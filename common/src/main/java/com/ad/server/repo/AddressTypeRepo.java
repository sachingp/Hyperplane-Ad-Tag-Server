package com.ad.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ad.server.Cache;
import com.ad.server.pojo.AddressType;@SuppressWarnings({ "rawtypes" })

@Repository
public interface AddressTypeRepo extends JpaRepository<AddressType, Integer>, Cache {

  default Class getType() {
    return AddressType.class;

  }

}