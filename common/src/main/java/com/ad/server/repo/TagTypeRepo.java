package com.ad.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ad.server.Cache;
import com.ad.server.pojo.TagType;

@SuppressWarnings({ "rawtypes" })
@Repository
public interface TagTypeRepo extends JpaRepository<TagType, Integer>, Cache {

  default Class getType() {
    return TagType.class;
  }

}