package com.ad.server.repo;

import com.ad.server.Cache;
import com.ad.server.pojo.TagType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@SuppressWarnings({"rawtypes"})
@RepositoryRestResource(collectionResourceRel = "tagTypes", path = "tagTypes")
public interface TagTypeRepo extends JpaRepository<TagType, Integer>, Cache {

  default Class getType() {
    return TagType.class;
  }

}