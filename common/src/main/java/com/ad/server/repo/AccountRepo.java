package com.ad.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ad.server.Cache;
import com.ad.server.pojo.Account;@SuppressWarnings({ "rawtypes" })

@Repository
public interface AccountRepo extends JpaRepository<Account, Integer>, Cache {

  default Class getType() {
    return Account.class;

  }

}