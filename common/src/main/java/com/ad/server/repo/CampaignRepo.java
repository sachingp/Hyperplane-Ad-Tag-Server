package com.ad.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ad.server.Cache;
import com.ad.server.pojo.Campaign;

@SuppressWarnings({ "rawtypes" })
@Repository
public interface CampaignRepo extends JpaRepository<Campaign, Integer>, Cache {

  default Class getType() {
    return Campaign.class;
  }

}