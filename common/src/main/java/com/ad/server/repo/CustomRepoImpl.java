package com.ad.server.repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.ad.server.pojo.Creative;

@Repository
public class CustomRepoImpl implements CustomRepo {

	@PersistenceContext
	private EntityManager entityManager;

	private static final String ALL_ELIGIBLE_CREATIVES_NATIVE = "\n" + 
			"SELECT ct.* \n" + 
			"FROM \n" + 
			"	account a, advertiser ad, campaign c, creative ct \n" + 
			"WHERE\n" + 
			"	a.account_id = ad.account_id \n" + 
			"	AND a.status_id = 1 \n" + 
			"	AND ad.advertiser_id = c.advertiser_id \n" + 
			"	AND ad.status_id = 1 \n" + 
			"	AND c.campaign_id = ct.campaign_id \n" + 
			"	AND c.status_id = 1 \n" + 
			"	AND ct.status_id = 1";
	
	private static final String  ALL_ELIGIBLE_CREATIVES_JPA =  
			"SELECT \n" + 
			"	 ct \n" + 
			"FROM 	\n" + 
			"	creative ct \n" + 
			"	INNER JOIN ct.campaign c \n" + 
			"	INNER JOIN c.advertiser a \n" + 
			"	INNER JOIN a.account ac \n" + 
			"WHERE \n" + 
			"	  ct.status = 1 \n" + 
			"  AND c.sttaus = 1 \n" + 
			"  AND a.status = 1 \n" + 
			"  AND ac.status = 1";
	 
	@Override
	public List<Creative> findAllEligibleCreativeEntities() {
		Query query = entityManager.createQuery(ALL_ELIGIBLE_CREATIVES_JPA);
		return query.getResultList();
	}
	
	@Override
	public List<Object[]> findAllEligibleCreatives() {
		Query nativeQuery = entityManager.createNativeQuery(ALL_ELIGIBLE_CREATIVES_NATIVE);
		return nativeQuery.getResultList();
	}

}
