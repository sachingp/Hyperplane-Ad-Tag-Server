package com.ad.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ad.server.Cache;
import com.ad.server.pojo.AssetSize;@SuppressWarnings({ "rawtypes" })

@Repository
public interface AssetSizeRepo extends JpaRepository<AssetSize, Integer>, Cache {

  default Class getType() {
    return AssetSize.class;

  }

}