package com.ad.server.repo;

import com.ad.server.Cache;
import com.ad.server.pojo.Os;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings({"rawtypes"})
@Repository
public interface OsRepo extends JpaRepository<Os, Integer>, Cache {

  default Class getType() {
    return Os.class;
  }

}