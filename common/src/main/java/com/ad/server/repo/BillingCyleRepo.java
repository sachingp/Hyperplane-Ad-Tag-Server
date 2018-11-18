package com.ad.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ad.server.Cache;
import com.ad.server.pojo.BillingCyle;@SuppressWarnings({ "rawtypes" })

@Repository
public interface BillingCyleRepo extends JpaRepository<BillingCyle, Integer>, Cache {

  default Class getType() {
    return BillingCyle.class;

  }

}