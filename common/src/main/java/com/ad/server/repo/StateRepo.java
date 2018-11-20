package com.ad.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ad.server.Cache;
import com.ad.server.pojo.State;
@SuppressWarnings({ "rawtypes" })

@Repository
public interface StateRepo extends JpaRepository<State, Integer>, Cache {

  default Class getType() {
    return State.class;

  }

}