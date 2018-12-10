package com.ad.server.repo;

import com.ad.server.Cache;
import com.ad.server.pojo.Events;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings({"rawtypes"})
@Repository
public interface EventsRepo extends JpaRepository<Events, Integer>, Cache {

  default Class getType() {
    return Events.class;
  }

}