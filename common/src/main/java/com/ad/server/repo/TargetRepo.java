package com.ad.server.repo;

import com.ad.server.Cache;
import com.ad.server.pojo.Target;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@SuppressWarnings({"rawtypes"})
@RepositoryRestResource(collectionResourceRel = "targets", path = "targets")
public interface TargetRepo extends JpaRepository<Target, Integer>, Cache {

  default Class getType() {
    return Target.class;
  }

}