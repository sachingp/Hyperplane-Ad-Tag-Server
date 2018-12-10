package com.ad.server.repo;

import com.ad.server.Cache;
import com.ad.server.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings({"rawtypes"})
@Repository
public interface UserRepo extends JpaRepository<User, Integer>, Cache {

  default Class getType() {
    return User.class;
  }

}