package com.ad.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ad.server.Cache;
import com.ad.server.pojo.CreativeTag;
@SuppressWarnings({ "rawtypes" })

@Repository
public interface CreativeTagRepo extends JpaRepository<CreativeTag, Integer>, Cache {

  default Class getType() {
    return CreativeTag.class;

  }

}