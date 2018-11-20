package com.ad.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ad.server.Cache;
import com.ad.server.pojo.CreativeAssets;
@SuppressWarnings({ "rawtypes" })

@Repository
public interface CreativeAssetsRepo extends JpaRepository<CreativeAssets, Integer>, Cache {

  default Class getType() {
    return CreativeAssets.class;

  }

}