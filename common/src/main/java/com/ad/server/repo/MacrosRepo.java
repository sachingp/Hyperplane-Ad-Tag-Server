package com.ad.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ad.server.Cache;
import com.ad.server.pojo.Macros;
@SuppressWarnings({ "rawtypes" })

@Repository
public interface MacrosRepo extends JpaRepository<Macros, Integer>, Cache {

  default Class getType() {
    return Macros.class;

  }

}