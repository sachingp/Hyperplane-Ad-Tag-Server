package com.ad.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.ad.server.Cache;
import com.ad.server.pojo.CreativeSize;

@SuppressWarnings({ "rawtypes" })
@RepositoryRestResource(collectionResourceRel = "creativeSizes", path = "creativeSizes")
public interface CreativeSizeRepo extends JpaRepository<CreativeSize, Integer>, Cache {

  default Class getType() {
    return CreativeSize.class;
  }

}