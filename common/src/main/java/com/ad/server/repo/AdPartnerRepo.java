package com.ad.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.ad.server.Cache;
import com.ad.server.pojo.AdPartner;

@SuppressWarnings({ "rawtypes" })
@RepositoryRestResource(collectionResourceRel = "adPartners", path = "adPartners")
public interface AdPartnerRepo extends JpaRepository<AdPartner, Integer>, Cache {

  default Class getType() {
    return AdPartner.class;
  }

}