package com.ad.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.ad.server.Cache;
import com.ad.server.pojo.CreativeTag;

@SuppressWarnings({ "rawtypes" })
@RepositoryRestResource(collectionResourceRel = "creativeTags", path = "creativeTags")
public interface CreativeTagRepo extends JpaRepository<CreativeTag, Integer>, Cache {

  default Class getType() {
    return CreativeTag.class;
  }

}