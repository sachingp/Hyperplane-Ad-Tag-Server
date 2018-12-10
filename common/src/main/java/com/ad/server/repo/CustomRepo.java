package com.ad.server.repo;

import com.ad.server.pojo.Creative;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface CustomRepo {

  public List<Object[]> findAllEligibleCreatives();

  public List<Creative> findAllEligibleCreativeEntities();

  public List<Integer> findActiveCreativeTagsByAdv(Integer advId);
}
