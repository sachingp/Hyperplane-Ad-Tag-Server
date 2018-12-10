package com.ad.server.repo;

import com.ad.server.Cache;
import com.ad.server.Cacheable;
import com.ad.server.pojo.Account;
import com.ad.server.pojo.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings({"rawtypes"})
@Repository("accountRepo")
public interface AccountRepo extends JpaRepository<Account, Integer>, Cache {

  String ACTIVE_ACCOUNTS = "active-account";

  default Class getType() {
    return AccountRepo.class;
  }

  @Cacheable(name = ACTIVE_ACCOUNTS, whole = true, key = {
      "accountGuid"}, keyType = String.class, valueType = Account.class)
  @Query("SELECT a FROM Account a WHERE a.status = 1 ")
  public List<Account> findActiveAccounts();


  @Query("SELECT a FROM Account a WHERE a.status = ?1 ")
  public List<Account> findAccountsByStatus(Status status);

}