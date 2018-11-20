package com.ad.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ad.server.Cache;
import com.ad.server.pojo.AdvertiserEventTrackers;

@SuppressWarnings({ "rawtypes" })
@Repository
public interface AdvertiserEventTrackersRepo extends JpaRepository<AdvertiserEventTrackers, Integer>, Cache {

  default Class getType() {
    return AdvertiserEventTrackers.class;
  }

}