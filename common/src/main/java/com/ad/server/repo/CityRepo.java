package com.ad.server.repo;

import com.ad.server.Cache;
import com.ad.server.pojo.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings({"rawtypes"})
@Repository
public interface CityRepo extends JpaRepository<City, Integer>, Cache {

  default Class getType() {
    return City.class;
  }

}