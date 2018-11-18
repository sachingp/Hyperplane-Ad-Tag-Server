package com.ad.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ad.server.Cache;
import com.ad.server.pojo.Target;@SuppressWarnings({ "rawtypes" })

@Repository
public interface TargetRepo extends JpaRepository<Target, Integer>, Cache {

  default Class getType() {
    return Target.class;

  }

}