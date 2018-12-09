package com.ad.server.repo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.ad.server.Cache;
import com.ad.server.Cacheable;
import com.ad.server.pojo.Campaign;

@SuppressWarnings({ "rawtypes" })
@RepositoryRestResource(collectionResourceRel = "campaigns", path = "campaigns")
@Repository("campaignRepo")
public interface CampaignRepo extends JpaRepository<Campaign, Integer>, Cache {

  String ADVERTISER_CAMPAIGN = "advertiser-campaign";

  default Class getType() {
    return CampaignRepo.class;
  }

  @Query( "SELECT c FROM Campaign c INNER JOIN c.advertiser ad WHERE c.status = 1 AND ad.id = ?1")
  public List<Campaign> findActiveCampaignsForAdvertiser(Integer advertiserId);

  @Cacheable(name = ADVERTISER_CAMPAIGN, whole = true, key={"advertiser_id"}, keyType = Integer.class, custom = "prepareByAdvertiser")
  @Query( "SELECT c FROM Campaign c INNER JOIN c.advertiser ad WHERE c.status = 1")
  public List<Campaign> findActiveCampaigns();

  default Map<Integer, List<Campaign>> prepareByAdvertiser(final List<Campaign> campaigns) {
    if (campaigns == null || campaigns.isEmpty()) {
      return null;
    }
    final Map<Integer, List<Campaign>> map = new HashMap<>();
    campaigns.forEach(campaign -> {
      final List<Campaign> byAdvertiser;
      final Integer advertiserId = campaign.getAdvertiser().getAdvertiserId();
      if (map.containsKey(advertiserId)) {
        byAdvertiser = map.get(advertiserId);
      } else {
        byAdvertiser = new ArrayList<>();
        map.put(advertiserId, byAdvertiser);
      }
      byAdvertiser.add(campaign);
    });
    return map;
  }

}