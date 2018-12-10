package com.ad.server.repo;

import com.ad.server.Cache;
import com.ad.server.pojo.AssetSize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@SuppressWarnings({"rawtypes"})
@RepositoryRestResource(collectionResourceRel = "assetSize", path = "assetSize")
public interface AssetSizeRepo extends JpaRepository<AssetSize, Integer>, Cache {

  default Class getType() {
    return AssetSize.class;
  }

}