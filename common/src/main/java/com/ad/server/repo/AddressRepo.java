package com.ad.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.ad.server.Cache;
import com.ad.server.pojo.Address;

@SuppressWarnings({ "rawtypes" })
@RepositoryRestResource(collectionResourceRel = "address", path = "address")
public interface AddressRepo extends JpaRepository<Address, Integer>, Cache {

  default Class getType() {
    return Address.class;
  }

}