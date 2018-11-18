package com.ad.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ad.server.Cache;
import com.ad.server.pojo.CustomAttributesKeys;@SuppressWarnings({ "rawtypes" })

@Repository
public interface CustomAttributesKeysRepo extends JpaRepository<CustomAttributesKeys, Integer>, Cache {

  default Class getType() {
    return CustomAttributesKeys.class;

  }

}