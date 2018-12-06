package com.ad.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.ad.server.Cache;
import com.ad.server.pojo.ObjectiveType;

@SuppressWarnings({ "rawtypes" })
@RepositoryRestResource(collectionResourceRel = "objectiveTypes", path = "objectiveTypes")
public interface ObjectiveTypeRepo extends JpaRepository<ObjectiveType, Integer>, Cache {

  default Class getType() {
    return ObjectiveType.class;
  }

}