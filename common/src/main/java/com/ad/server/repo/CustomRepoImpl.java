package com.ad.server.repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class CustomRepoImpl implements CustomRepo {

	@PersistenceContext
	private EntityManager entityManager;

	private static final String ALL_ELIGIBLE_CREATIVES = "\n" + 
			"SELECT ct.* \n" + 
			"FROM \n" + 
			"	account a, advertiser ad, campaign c, creative ct \n" + 
			"WHERE\n" + 
			"	a.account_id = ad.account_id \n" + 
			"	AND a.status_id = 1 \n" + 
			"	AND ad.advertiser_id = c.advertiser_id \n" + 
			"	AND ad.status = 1 \n" + 
			"	AND c.campaign_id = ct.campaign_id \n" + 
			"	AND c.status = 1 \n" + 
			"	AND ct.status = 1";
	 
	@Override
	public List<Object[]> finaAllEligibleCreatives() {
		Query nativeQuery = entityManager.createNativeQuery(ALL_ELIGIBLE_CREATIVES);
		return nativeQuery.getResultList();
	}

}
