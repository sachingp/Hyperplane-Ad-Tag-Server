package com.ad.server.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.ad.server.Cache;
import com.ad.server.pojo.Creative;

@SuppressWarnings({ "rawtypes" })
@RepositoryRestResource(collectionResourceRel = "creatives", path = "creatives")
public interface CreativeRepo extends JpaRepository<Creative, Integer>, Cache {

  default Class getType() {
    return Creative.class;
  }
 @Query( "SELECT \n" + 
			"	 ct \n" + 
			"FROM 	\n" + 
			"	Creative ct \n" + 
			"	INNER JOIN ct.campaign c \n" + 
			"	INNER JOIN c.advertiser a \n" + 
			"	INNER JOIN a.account ac \n" + 
			"WHERE \n" + 
			"	  ct.status = 1 \n" + 
			"  AND c.status = 1 \n" + 
			"  AND a.status = 1 \n" + 
			"  AND ac.status = 1")
 public List<Creative> findAllMatchingCreativesList();
 
 @Query( "SELECT cr FROM Creative cr INNER JOIN cr.campaign c WHERE cr.status = 1 AND c.id = ?1")
 public List<Creative> findActiveCreativesForCampaigns(Integer camapignId);
  
}