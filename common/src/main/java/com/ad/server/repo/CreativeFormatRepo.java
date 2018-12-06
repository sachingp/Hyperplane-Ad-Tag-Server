package com.ad.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.ad.server.Cache;
import com.ad.server.pojo.CreativeFormat;

@SuppressWarnings({ "rawtypes" })
@RepositoryRestResource(collectionResourceRel = "creativeFormats", path = "creativeFormats")
public interface CreativeFormatRepo extends JpaRepository<CreativeFormat, Integer>, Cache {

  default Class getType() {
    return CreativeFormat.class;
  }

}