package com.ad.server.repo;

import com.ad.server.Cache;
import com.ad.server.pojo.DayPart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings({"rawtypes"})
@Repository
public interface DayPartRepo extends JpaRepository<DayPart, Integer>, Cache {

  default Class getType() {
    return DayPart.class;
  }

}