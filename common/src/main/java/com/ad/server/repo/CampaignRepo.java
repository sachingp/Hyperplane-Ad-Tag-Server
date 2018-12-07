package com.ad.server.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.ad.server.Cache;
import com.ad.server.pojo.Campaign;

@SuppressWarnings({ "rawtypes" })
@RepositoryRestResource(collectionResourceRel = "campaigns", path = "campaigns")
public interface CampaignRepo extends JpaRepository<Campaign, Integer>, Cache {

  default Class getType() {
    return Campaign.class;
  }

  @Query( "SELECT c FROM Campaign c INNER JOIN c.advertiser ad WHERE c.status = 1 AND ad.id = ?1")
  public List<Campaign> findActiveCampaignsForAdvertiser(Integer advertiserId);
  
}