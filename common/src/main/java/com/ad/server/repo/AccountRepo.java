package com.ad.server.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ad.server.Cache;
import com.ad.server.pojo.Account;
import com.ad.server.pojo.Status;

@SuppressWarnings({ "rawtypes" })
@Repository
public interface AccountRepo extends JpaRepository<Account, Integer>, Cache {

  default Class getType() {
    return Account.class;
  }
  
  @Query( "SELECT a FROM Account a WHERE a.status = ?1 ")
  public List<Account> findActiveAccounts(Status status);
  
}