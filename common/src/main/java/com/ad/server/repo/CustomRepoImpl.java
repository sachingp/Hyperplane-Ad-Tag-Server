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

	private static final String ALL_ACTIVE_CREATIVE_TAGS_OF_ADV = "\n" + 
			"SELECT cta.creative_tag_id \n" + 
			"FROM \n" + 
			"	creative_tag cta,  advertiser ad, campaign c, creative ct \n" + 
			"WHERE \n" + 
			"	 cta.creative_id = ct.creative_id \n" + 
			"	AND ct.campaign_id = c.campaign_id \n" +
			"	AND c.advertiser_id = ad.advertiser_id \n" + 
			"	AND ct.status_id = 1 \n" + 
			"	AND c.status_id = 1 \n " +
			"	AND ad.status_id = 1 \n"+ 
			"   AND ad.advertiser_id = advId \n" +
			" ORDER BY cta.creative_tag_id";
	
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
		Query nativeQuery = entityManager.createNativeQuery(ALL_ACTIVE_CREATIVE_TAGS_OF_ADV);
		return nativeQuery.getResultList();
	}

	@Override
	public List<Integer> findActiveCreativeTagsByAdv(Integer advId) {
		if (advId == null || advId == 0) return null;
		
		Query nativeQuery = entityManager.createNativeQuery(ALL_ACTIVE_CREATIVE_TAGS_OF_ADV.replace("advId", String.valueOf(advId)));
		return nativeQuery.getResultList();
	}

}
