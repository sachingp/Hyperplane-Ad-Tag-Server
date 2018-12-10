package com.ad.server.repo;

import com.ad.server.Cache;
import com.ad.server.pojo.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings({"rawtypes"})
@Repository
public interface StateRepo extends JpaRepository<State, Integer>, Cache {

  default Class getType() {
    return State.class;
  }

}