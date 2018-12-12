package com.ad.server.repo;

import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.ad.server.Cache;
import com.ad.server.Cacheable;
import com.ad.server.pojo.AdPartner;

@SuppressWarnings({"rawtypes"})
@RepositoryRestResource(collectionResourceRel = "adPartners", path = "adPartners")
@Repository("adPartnerRepo")
public interface AdPartnerRepo extends JpaRepository<AdPartner, Integer>, Cache {

  String ACCOUNT_PARTNER = "tag-partner";

  default Class getType() {
    return AdPartnerRepo.class;
  }

  @Cacheable(name = ACCOUNT_PARTNER, whole = true, key = {
      "accountGuid"}, value={"adPartnerId"}, keyType = String.class, custom = "prepareByAccount")
  @Query("SELECT new AdPartner(ac.accountGuid, ap.adPartnerId) FROM Advertiser ad"
      + " INNER JOIN ad.address a INNER JOIN ad.account ac, AdPartner ap"
      + " WHERE ap.address = ad.address AND ap.status = 1 AND ad.status = 1 AND ac.status = 1")
  public List<AdPartner> findActiveAccountCreatives();

  default Map<String, BitSet> prepareByAccount(final List<AdPartner> partners) {
    if (partners == null || partners.isEmpty()) {
      return null;
    }
    final Map<String, BitSet> map = new HashMap<>();
    partners.forEach(partner -> {
      final BitSet byAccount;
      final String accountGuid = partner.getAccountGuid();
      if (map.containsKey(accountGuid)) {
        byAccount = map.get(accountGuid);
      } else {
        byAccount = new BitSet();
        map.put(accountGuid, byAccount);
      }
      byAccount.set(partner.getAdPartnerId());
    });
    return map;
  }

}