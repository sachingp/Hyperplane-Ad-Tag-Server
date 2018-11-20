package com.ad.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ad.server.Cache;
import com.ad.server.pojo.Segment;

@SuppressWarnings({ "rawtypes" })
@Repository
public interface SegmentRepo extends JpaRepository<Segment, Integer>, Cache {

  default Class getType() {
    return Segment.class;
  }

}