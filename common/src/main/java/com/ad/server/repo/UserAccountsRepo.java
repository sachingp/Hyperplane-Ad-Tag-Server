package com.ad.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ad.server.Cache;
import com.ad.server.pojo.UserAccounts;
@SuppressWarnings({ "rawtypes" })

@Repository
public interface UserAccountsRepo extends JpaRepository<UserAccounts, Integer>, Cache {

  default Class getType() {
    return UserAccounts.class;

  }

}