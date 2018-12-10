package com.ad.server.repo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.ad.server.Cache;
import com.ad.server.Cacheable;
import com.ad.server.pojo.Advertiser;

@SuppressWarnings({ "rawtypes" })
@RepositoryRestResource(collectionResourceRel = "advertisers", path = "advertisers")
@Repository("advertiserRepo")
public interface AdvertiserRepo extends JpaRepository<Advertiser, Integer>, Cache {

  String ACCOUNT_ADVERTISER = "account-advertiser";

  default Class getType() {
    return AdvertiserRepo.class;
  }

  @Query( "SELECT ad FROM Advertiser ad INNER JOIN ad.account a WHERE ad.status = 1 AND a.id = ?1")
  public List<Advertiser> findActiveAdvertiserForAccount(Integer accountId);

  @Cacheable(name = ACCOUNT_ADVERTISER, whole = true, key={"accountId"}, keyType = Integer.class, custom = "prepareByAccount")
  @Query( "SELECT ad FROM Advertiser ad WHERE ad.status = 1")
  public List<Advertiser> findActiveAdvertisers();

  default Map<Integer, List<Advertiser>> prepareByAccount(final List<Advertiser> advertisers) {
    if (advertisers == null || advertisers.isEmpty()) {
      return null;
    }
    final Map<Integer, List<Advertiser>> map = new HashMap<>();
    advertisers.forEach(advertiser -> {
      final List<Advertiser> byAccount;
      final Integer accountId = advertiser.getAccount().getAccountId();
      if (map.containsKey(accountId)) {
        byAccount = map.get(accountId);
      } else {
        byAccount = new ArrayList<>();
        map.put(accountId, byAccount);
      }
      byAccount.add(advertiser);
    });
    return map;
  }

}