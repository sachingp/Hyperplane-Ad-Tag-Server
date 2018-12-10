package com.ad.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ad.server.Cache;
import com.ad.server.pojo.PostalCode;

@SuppressWarnings({"rawtypes"})
@Repository
public interface PostalCodeRepo extends JpaRepository<PostalCode, Integer>, Cache {

  default Class getType() {
    return PostalCode.class;
  }

}