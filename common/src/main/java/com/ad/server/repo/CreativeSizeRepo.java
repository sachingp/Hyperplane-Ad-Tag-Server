package com.ad.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ad.server.Cache;
import com.ad.server.pojo.CreativeSize;@SuppressWarnings({ "rawtypes" })

@Repository
public interface CreativeSizeRepo extends JpaRepository<CreativeSize, Integer>, Cache {

  default Class getType() {
    return CreativeSize.class;

  }

}