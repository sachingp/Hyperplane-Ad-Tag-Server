package com.ad.server.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.ad.server.Cache;
import com.ad.server.pojo.Advertiser;

@SuppressWarnings({ "rawtypes" })
@RepositoryRestResource(collectionResourceRel = "advertisers", path = "advertisers")
public interface AdvertiserRepo extends JpaRepository<Advertiser, Integer>, Cache {

  default Class getType() {
    return Advertiser.class;
  }

  @Query( "SELECT ad FROM Advertiser ad INNER JOIN ad.account a WHERE ad.status = 1 AND a.id = ?1")
  public List<Advertiser> findActiveAdvertiserForAccount(Integer accountId);
}