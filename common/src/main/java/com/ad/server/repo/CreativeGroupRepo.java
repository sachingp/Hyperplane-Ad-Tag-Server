package com.ad.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ad.server.Cache;
import com.ad.server.pojo.CreativeGroup;
@SuppressWarnings({ "rawtypes" })

@Repository
public interface CreativeGroupRepo extends JpaRepository<CreativeGroup, Integer>, Cache {

  default Class getType() {
    return CreativeGroup.class;

  }

}