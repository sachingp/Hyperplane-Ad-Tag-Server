package com.ad.server.repo;

import com.ad.server.Cache;
import com.ad.server.pojo.AssetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings({"rawtypes"})
@Repository
public interface AssetTypeRepo extends JpaRepository<AssetType, Integer>, Cache {

  default Class getType() {
    return AssetType.class;
  }

}