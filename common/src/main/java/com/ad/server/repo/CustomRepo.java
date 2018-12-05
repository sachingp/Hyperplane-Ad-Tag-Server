package com.ad.server.repo;

import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CustomRepo {
	public List<Object[]> finaAllEligibleCreatives();
}
