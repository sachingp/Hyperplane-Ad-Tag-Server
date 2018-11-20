package com.ad.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ad.server.Cache;
import com.ad.server.pojo.BillingPaymentDetails;

@SuppressWarnings({ "rawtypes" })
@Repository
public interface BillingPaymentDetailsRepo extends JpaRepository<BillingPaymentDetails, Integer>, Cache {

  default Class getType() {
    return BillingPaymentDetails.class;
  }

}