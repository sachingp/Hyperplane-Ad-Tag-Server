package com.ad.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ad.server.Cache;
import com.ad.server.pojo.Billing;
@SuppressWarnings({ "rawtypes" })

@Repository
public interface BillingRepo extends JpaRepository<Billing, Integer>, Cache {

  default Class getType() {
    return Billing.class;

  }

}