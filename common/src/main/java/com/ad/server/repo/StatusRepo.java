package com.ad.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ad.server.Cache;
import com.ad.server.pojo.Status;

@SuppressWarnings({ "rawtypes" })
@Repository
public interface StatusRepo extends JpaRepository<Status, Integer>, Cache {

  default Class getType() {
    return Status.class;
  }

}