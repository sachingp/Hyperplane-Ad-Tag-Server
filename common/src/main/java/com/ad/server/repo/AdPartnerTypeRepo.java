package com.ad.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.ad.server.Cache;
import com.ad.server.pojo.AdPartnerType;

@SuppressWarnings({ "rawtypes" })
@RepositoryRestResource(collectionResourceRel = "adPartnerTypes", path = "adPartnerTypes")
public interface AdPartnerTypeRepo extends JpaRepository<AdPartnerType, Integer>, Cache {

  default Class getType() {
    return AdPartnerType.class;
  }

}