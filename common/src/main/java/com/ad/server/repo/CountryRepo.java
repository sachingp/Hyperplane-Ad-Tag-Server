package com.ad.server.repo;

import com.ad.server.Cache;
import com.ad.server.pojo.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings({"rawtypes"})
@Repository
public interface CountryRepo extends JpaRepository<Country, Integer>, Cache {

  default Class getType() {
    return Country.class;
  }

}