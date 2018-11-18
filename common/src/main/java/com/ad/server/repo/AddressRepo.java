package com.ad.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ad.server.Cache;
import com.ad.server.pojo.Address;@SuppressWarnings({ "rawtypes" })

@Repository
public interface AddressRepo extends JpaRepository<Address, Integer>, Cache {

  default Class getType() {
    return Address.class;

  }

}