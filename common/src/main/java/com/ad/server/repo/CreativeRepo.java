package com.ad.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ad.server.Cache;
import com.ad.server.pojo.Creative;

@SuppressWarnings({ "rawtypes" })
@Repository
public interface CreativeRepo extends JpaRepository<Creative, Integer>, Cache {

  default Class getType() {
    return Creative.class;
  }

}