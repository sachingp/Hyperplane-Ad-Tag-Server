package com.ad.server.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ad.server.repo.CustomRepo;

import lombok.extern.slf4j.Slf4j;


@RestController
@Slf4j
public class CreativeResource {

	@Autowired
	private CustomRepo customRepo;
	
	@GetMapping("/eligibleCreatives")
	public List<Object[]> getAll() {
	    return customRepo.finaAllEligibleCreatives();
	}
}
