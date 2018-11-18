package com.ad.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ad.server.Cache;
import com.ad.server.pojo.Roles;@SuppressWarnings({ "rawtypes" })

@Repository
public interface RolesRepo extends JpaRepository<Roles, Integer>, Cache {

  default Class getType() {
    return Roles.class;

  }

}