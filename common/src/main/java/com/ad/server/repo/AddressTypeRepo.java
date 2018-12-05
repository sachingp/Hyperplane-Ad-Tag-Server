package com.ad.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.ad.server.Cache;
import com.ad.server.pojo.AddressType;

@SuppressWarnings({ "rawtypes" })
@RepositoryRestResource(collectionResourceRel = "addressType", path = "addressType")
public interface AddressTypeRepo extends JpaRepository<AddressType, Integer>, Cache {

  default Class getType() {
    return AddressType.class;
  }

}