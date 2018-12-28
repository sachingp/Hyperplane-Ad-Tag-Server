package com.ad.server.repo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.ad.server.Cache;
import com.ad.server.Cacheable;
import com.ad.server.beans.PropertyReferrer;
import com.ad.server.pojo.CreativeTag;
import com.ad.server.template.CreativeTemplateType;
import com.ad.server.template.TemplateService;

@SuppressWarnings({ "rawtypes" })
@Repository("creativeTagRepo")
@RepositoryRestResource(collectionResourceRel = "creativeTags", path = "creativeTags")
public interface CreativeTagRepo extends JpaRepository<CreativeTag, Integer>, Cache {

  String TAG_GUID_CREATIVE = "active-tag-guids";
  String TAG_GUID_URL = "active-tag-url";
  String TAG_GUID_MACROS = "active-tag-macros";

  default Class getType() {
    return CreativeTagRepo.class;
  }

  @Cacheable(name = TAG_GUID_CREATIVE, whole = true, key = {
      "tagGuid"}, keyType = String.class, valueType = CreativeTag.class)
  @Query("SELECT new CreativeTag(ct.tagGuid, cr.creativeId, ct.tagTypeId, ct.adPartnerId) FROM CreativeTag ct"
      + " INNER JOIN ct.creative cr INNER JOIN cr.campaign c INNER JOIN c.advertiser ad INNER JOIN ad.account ac"
      + " WHERE cr.status = 1 AND c.status = 1 AND ad.status = 1 AND ac.status = 1")
  public List<CreativeTag> findActiveCreativeGuids();

  @Cacheable(name = TAG_GUID_URL, whole = true, key = {
      "tagGuid"}, value = {"creativeMarkupUrl"}, keyType = String.class, valueType = String.class)
  @Query("SELECT new CreativeTag(ct.tagGuid, ct.creativeMarkupUrl) FROM CreativeTag ct"
      + " INNER JOIN ct.creative cr INNER JOIN cr.campaign c INNER JOIN c.advertiser ad INNER JOIN ad.account ac"
      + " WHERE cr.status = 1 AND c.status = 1 AND ad.status = 1 AND ac.status = 1")
  public List<CreativeTag> findActiveCreativeGuidUrls();

  @Cacheable(name = TAG_GUID_MACROS, key = {"tagGuid"}, keyType = String.class, custom = "prepareEvaluatedMacros")
  @Query("SELECT new CreativeTag(ct.tagGuid, cr.creativeId, ct.tagTypeId, ct.adPartnerId, m.macroName, m.macroValue) FROM CreativeTag ct"
      + " INNER JOIN ct.creative cr INNER JOIN cr.campaign c INNER JOIN c.advertiser ad INNER JOIN ad.account ac, Macros m"
      + " WHERE cr.status = 1 AND c.status = 1 AND ad.status = 1 AND ac.status = 1 AND m.partner = ct.adPartnerId AND m.status = 1")
  public List<CreativeTag> findActiveCreativeTagMacros();

  default Map<String, String> prepareEvaluatedMacros(
      final List<CreativeTag> tags,
      @PropertyReferrer("ads.domain") final String adsDomain,
      @PropertyReferrer("trk.domain") final String trackDomain,
      @PropertyReferrer("clk.domain") final String clickDomain) {
    if (tags == null || tags.isEmpty()) {
      return null;
    }
    final Map<String, String> map = new HashMap<>();
    final Map<Integer, Map<String, String>> macros = new HashMap<>();
    final Map<String, CreativeTag> creatives = new HashMap<>();
    tags.forEach(tag -> {
      final String guid = tag.getTagGuid();
      final Integer partnerId = tag.getAdPartnerId();
      creatives.put(guid, tag);
      final Map<String, String> partnerMacros;
      if (macros.containsKey(partnerId)) {
        partnerMacros = macros.get(partnerId);
      } else {
        partnerMacros = new HashMap<>();
        macros.put(partnerId, partnerMacros);
      }
      partnerMacros.put(tag.getMacroName(), tag.getMacroValue());
    });
    creatives.forEach((guid, tag) -> {
      final CreativeTemplateType template = CreativeTemplateType.from(tag.getTagTypeId());
      final Map<String, Object> context = new HashMap<>();
      context.put("ADS_DOMAIN", adsDomain);
      context.put("TRACK_DOMAIN", trackDomain);
      context.put("CLICK_DOMAIN", clickDomain);
      context.put("TAG_GUID", guid);
        final Map<String, String> partnerMacros = macros.get(tag.getAdPartnerId());
        final StringBuilder builder = new StringBuilder();
        partnerMacros.forEach((k, v) -> {
          builder.append("'").append(k).append("'='").append(v).append("',");
        });
        final String expanded;
        if (builder.length() > 1) {
          expanded = builder.substring(0, builder.length() - 1);
        } else {
          expanded = builder.toString();
        }
      context.put("PARTNER_MACROS", expanded);
      final String evaluated = new TemplateService().eval(context, template);
      map.put(guid, evaluated);
    });
    return map;
  }

}