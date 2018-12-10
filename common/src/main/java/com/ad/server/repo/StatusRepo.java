package com.ad.server.repo;

import com.ad.server.Cache;
import com.ad.server.pojo.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@SuppressWarnings({"rawtypes"})
@RepositoryRestResource(collectionResourceRel = "status", path = "status")
public interface StatusRepo extends JpaRepository<Status, Integer>, Cache {

  default Class getType() {
    return Status.class;
  }

}