package com.ad.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ad.server.Cache;
import com.ad.server.pojo.AdPartner;@SuppressWarnings({ "rawtypes" })

@Repository
public interface AdPartnerRepo extends JpaRepository<AdPartner, Integer>, Cache {

  default Class getType() {
    return AdPartner.class;

  }

}