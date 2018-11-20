package com.ad.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ad.server.Cache;
import com.ad.server.pojo.User;
@SuppressWarnings({ "rawtypes" })

@Repository
public interface UserRepo extends JpaRepository<User, Integer>, Cache {

  default Class getType() {
    return User.class;

  }

}