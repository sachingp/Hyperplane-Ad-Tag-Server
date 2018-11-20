package com.ad.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ad.server.Cache;
import com.ad.server.pojo.AccountEventTrackers;

@SuppressWarnings({ "rawtypes" })
@Repository
public interface AccountEventTrackersRepo extends JpaRepository<AccountEventTrackers, Integer>, Cache {

  default Class getType() {
    return AccountEventTrackers.class;
  }

}