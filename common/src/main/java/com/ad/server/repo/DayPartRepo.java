package com.ad.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ad.server.Cache;
import com.ad.server.pojo.DayPart;@SuppressWarnings({ "rawtypes" })

@Repository
public interface DayPartRepo extends JpaRepository<DayPart, Integer>, Cache {

  default Class getType() {
    return DayPart.class;

  }

}