package com.ad.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ad.server.Cache;
import com.ad.server.pojo.BillingCycle;
@SuppressWarnings({ "rawtypes" })

@Repository
public interface BillingCycleRepo extends JpaRepository<BillingCycle, Integer>, Cache {

  default Class getType() {
    return BillingCycle.class;

  }

}