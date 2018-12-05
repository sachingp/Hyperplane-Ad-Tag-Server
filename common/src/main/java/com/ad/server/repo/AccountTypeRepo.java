package com.ad.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.ad.server.Cache;
import com.ad.server.pojo.AccountType;

@SuppressWarnings({ "rawtypes" })
@RepositoryRestResource(collectionResourceRel = "accountType", path = "accountType")
public interface AccountTypeRepo extends JpaRepository<AccountType, Integer>, Cache {

  default Class getType() {
    return AccountType.class;
  }

}