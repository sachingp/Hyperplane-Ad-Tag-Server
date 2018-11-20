package com.ad.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ad.server.Cache;
import com.ad.server.pojo.CustomAttributesValues;

@SuppressWarnings({ "rawtypes" })
@Repository
public interface CustomAttributesValuesRepo extends JpaRepository<CustomAttributesValues, Integer>, Cache {

  default Class getType() {
    return CustomAttributesValues.class;
  }

}