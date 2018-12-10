package com.ad.server.repo;

import com.ad.server.Cache;
import com.ad.server.pojo.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@SuppressWarnings({"rawtypes"})
@RepositoryRestResource(collectionResourceRel = "addresses", path = "addresses")
public interface AddressRepo extends JpaRepository<Address, Integer>, Cache {

  default Class getType() {
    return Address.class;
  }

}