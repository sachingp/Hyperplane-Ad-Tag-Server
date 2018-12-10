package com.ad.server.repo;

import com.ad.server.Cache;
import com.ad.server.pojo.Macros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings({"rawtypes"})
@Repository
public interface MacrosRepo extends JpaRepository<Macros, Integer>, Cache {

  default Class getType() {
    return Macros.class;
  }

}