package com.ad.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ad.server.Cache;
import com.ad.server.pojo.UserRoles;

@SuppressWarnings({ "rawtypes" })
@Repository
public interface UserRolesRepo extends JpaRepository<UserRoles, Integer>, Cache {

  default Class getType() {
    return UserRoles.class;
  }

}