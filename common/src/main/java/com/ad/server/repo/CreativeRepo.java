package com.ad.server.repo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.ad.server.Cache;
import com.ad.server.Cacheable;
import com.ad.server.pojo.Creative;import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings({"rawtypes"})
@RepositoryRestResource(collectionResourceRel = "creatives", path = "creatives")
@Repository("creativeRepo")
public interface CreativeRepo extends JpaRepository<Creative, Integer>, Cache {

  String EXCLUSION_CAMELCASE = "Exclusion";
  String INCLUSION_CAMELCASE = "Inclusion";
  String INCLUSION_LOWERCASE = "/inclusion";
  String EXCLUSION_LOWERCASE = "/exclusion";
  String CAMPAIGN_CREATIVE = "campaign-creative";
  String ACCOUNT_CREATIVE = "guid-creative";
  String ACTIVE_CREATIVE = "active-creative";
  String CREATIVE_COUNTRY = "creative-country";

  default Class getType() {
    return CreativeRepo.class;
  }

  @Query("SELECT \n" +
      "	 ct \n" +
      "FROM 	\n" +
      "	Creative ct \n" +
      "	INNER JOIN ct.campaign c \n" +
      "	INNER JOIN c.advertiser a \n" +
      "	INNER JOIN a.account ac \n" +
      "WHERE \n" +
      "	  ct.status = 1 \n" +
      "  AND c.status = 1 \n" +
      "  AND a.status = 1 \n" +
      "  AND ac.status = 1")
  public List<Creative> findAllMatchingCreativesList();

  @Query("SELECT cr FROM Creative cr INNER JOIN cr.campaign c WHERE cr.status = 1 AND c.id = ?1")
  public List<Creative> findActiveCreativesForCampaigns(Integer camapignId);

  @Cacheable(name = CAMPAIGN_CREATIVE, whole = true, key = {
      "campaignId"}, keyType = Integer.class, custom = "prepareByCampaign")
  @Query("SELECT cr FROM Creative cr INNER JOIN cr.campaign c INNER JOIN c.advertiser ad INNER JOIN ad.account ac"
      + " WHERE cr.status = 1 AND c.status = 1 AND ad.status = 1 AND ac.status = 1")
  public List<Creative> findActiveCreatives();

  @Cacheable(name = ACCOUNT_CREATIVE, whole = true, key = {
      "accountGuid"}, value={"creativeId"}, keyType = String.class, custom = "prepareByAccount")
  @Query("SELECT new Creative(ac.accountGuid, cr.creativeId) FROM Creative cr"
      + " INNER JOIN cr.campaign c INNER JOIN c.advertiser ad INNER JOIN ad.account ac"
      + " WHERE cr.status = 1 AND c.status = 1 AND ad.status = 1 AND ac.status = 1")
  public List<Creative> findActiveAccountCreatives();

  @Cacheable(name = ACTIVE_CREATIVE, whole = true, key = {
      "creativeId"}, keyType = Integer.class, valueType = Creative.class)
  @Query("SELECT new Creative(cr.creativeId, c.campaignId, ad.advertiserId, ac.accountId, ac.accountGuid, cr.name) FROM Creative cr"
      + " INNER JOIN cr.campaign c INNER JOIN c.advertiser ad INNER JOIN ad.account ac"
      + " WHERE cr.status = 1 AND c.status = 1 AND ad.status = 1 AND ac.status = 1")
  public List<Creative> findAllActiveCreatives();

  @Cacheable(name = CREATIVE_COUNTRY, whole = true, key = {
      "accountGuid"}, value={"creativeId"}, keyType = String.class, custom = "prepareByGeo")
  @Query("SELECT new Creative(cr.creativeId, t.byGeo) FROM Creative cr INNER JOIN cr.target t"
      + " INNER JOIN cr.campaign c INNER JOIN c.advertiser ad INNER JOIN ad.account ac"
      + " WHERE cr.status = 1 AND c.status = 1 AND ad.status = 1 AND ac.status = 1")
  public List<Creative> findActiveCreativesByGeo();

  default Map<Integer, List<Creative>> prepareByCampaign(final List<Creative> creatives) {
    if (creatives == null || creatives.isEmpty()) {
      return null;
    }
    final Map<Integer, List<Creative>> map = new HashMap<>();
    creatives.forEach(creative -> {
      final List<Creative> byCampaign;
      final Integer campaignId = creative.getCampaign().getCampaignId();
      if (map.containsKey(campaignId)) {
        byCampaign = map.get(campaignId);
      } else {
        byCampaign = new ArrayList<>();
        map.put(campaignId, byCampaign);
      }
      byCampaign.add(creative);
    });
    return map;
  }

  default Map<String, BitSet> prepareByAccount(final List<Creative> creatives) {
    if (creatives == null || creatives.isEmpty()) {
      return null;
    }
    final Map<String, BitSet> map = new HashMap<>();
    creatives.forEach(creative -> {
      final BitSet byAccount;
      final String accountGuid = creative.getAccountGuid();
      if (map.containsKey(accountGuid)) {
        byAccount = map.get(accountGuid);
      } else {
        byAccount = new BitSet();
        map.put(accountGuid, byAccount);
      }
      byAccount.set(creative.getCreativeId());
    });
    return map;
  }

  default Map<Integer, Map<String, List<String>>> prepareByGeo(final List<Creative> creatives) {
    if (creatives == null || creatives.isEmpty()) {
      return null;
    }
    final Map<Integer, Map<String, List<String>>> map = new HashMap<>();
    final ObjectMapper mapper = new ObjectMapper();
    creatives.forEach(creative -> {
      try {
        final Map<String, List<String>> byGeo;
        final Integer creativeId = creative.getCreativeId();
        if (map.containsKey(creativeId)) {
          byGeo = map.get(creativeId);
        } else {
          byGeo = new HashMap<>();
          map.put(creativeId, byGeo);
        }
        final String json = creative.getByGeo();
        if (json == null || json.trim().isEmpty()) {
          return;
        }
        final JsonNode geoJson = mapper.readTree(json);
        final JsonNode inclusions = geoJson.at(INCLUSION_LOWERCASE);
        if (!inclusions.isMissingNode()) {
          final List<String> list;
          if (byGeo.containsKey(INCLUSION_CAMELCASE)) {
            list = byGeo.get(INCLUSION_CAMELCASE);
          } else {
            list = new ArrayList<>();
            byGeo.put(INCLUSION_CAMELCASE, list);
          }
          final Iterator<JsonNode> iterator = inclusions.iterator();
          while (iterator.hasNext()) {
            list.add(iterator.next().asText());
          }
        }
        final JsonNode exclusions = geoJson.at(EXCLUSION_LOWERCASE);
        if (!exclusions.isMissingNode()) {
          final List<String> list;
          if (byGeo.containsKey(EXCLUSION_CAMELCASE)) {
            list = byGeo.get(EXCLUSION_CAMELCASE);
          } else {
            list = new ArrayList<>();
            byGeo.put(EXCLUSION_CAMELCASE, list);
          }
          final Iterator<JsonNode> iterator = exclusions.iterator();
          while (iterator.hasNext()) {
            list.add(iterator.next().asText());
          }
        }
      } catch (final IOException e) {
        e.printStackTrace();
      }
    });
    return map;
  }

}