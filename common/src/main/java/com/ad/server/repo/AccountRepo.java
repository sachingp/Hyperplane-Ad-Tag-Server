package com.ad.server.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ad.server.Cache;
import com.ad.server.Cacheable;
import com.ad.server.pojo.Account;
import com.ad.server.pojo.Status;

@SuppressWarnings({ "rawtypes" })
@Repository("accountRepo")
public interface AccountRepo extends JpaRepository<Account, Integer>, Cache {

  String ACTIVE_ACCOUNTS = "active-account";

  default Class getType() {
    return AccountRepo.class;
  }

  @Cacheable(name = ACTIVE_ACCOUNTS, whole = true, key={"account_id"}, keyType = Integer.class, valueType = Account.class)
  @Query( "SELECT a FROM Account a WHERE a.status = 1 ")
  public List<Account> findActiveAccounts();


  @Query( "SELECT a FROM Account a WHERE a.status = ?1 ")
  public List<Account> findAccountsByStatus(Status status);

}