package com.ad.server.repo;

import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;

import com.ad.server.pojo.Creative;

@NoRepositoryBean
public interface CustomRepo {
	public List<Object[]> findAllEligibleCreatives();
	
	public List<Creative> findAllEligibleCreativeEntities();
}
