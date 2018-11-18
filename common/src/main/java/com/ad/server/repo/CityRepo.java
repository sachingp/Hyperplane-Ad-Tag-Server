package com.ad.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ad.server.Cache;
import com.ad.server.pojo.City;@SuppressWarnings({ "rawtypes" })

@Repository
public interface CityRepo extends JpaRepository<City, Integer>, Cache {

  default Class getType() {
    return City.class;

  }

}