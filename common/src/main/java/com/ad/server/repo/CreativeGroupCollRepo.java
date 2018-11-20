package com.ad.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ad.server.Cache;
import com.ad.server.pojo.CreativeGroupColl;
@SuppressWarnings({ "rawtypes" })

@Repository
public interface CreativeGroupCollRepo extends JpaRepository<CreativeGroupColl, Integer>, Cache {

  default Class getType() {
    return CreativeGroupColl.class;

  }

}