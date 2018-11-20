package com.ad.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ad.server.Cache;
import com.ad.server.pojo.BillingType;
@SuppressWarnings({ "rawtypes" })

@Repository
public interface BillingTypeRepo extends JpaRepository<BillingType, Integer>, Cache {

  default Class getType() {
    return BillingType.class;

  }

}