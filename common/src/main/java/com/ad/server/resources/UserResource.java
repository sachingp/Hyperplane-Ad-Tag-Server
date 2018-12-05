package com.ad.server.resources;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ad.server.beans.ResourceNotFoundException;
import com.ad.server.pojo.User;
import com.ad.server.repo.UserRepo;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class UserResource {

	@Autowired
	private UserRepo userRepo;
	
	@PostMapping("/users")
	public User create(@Valid @RequestBody User user) {
		log.debug("Trying to save the user object :: {}", user);
	    return userRepo.save(user);
	}
	
	@GetMapping("/users")
	public List<User> getAll() {
	    return userRepo.findAll();
	}
	
	@GetMapping("/users/{id}")
	public User getById(@PathVariable(value = "id") Integer userId) {
	    return userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Note", "id", userId));
	}
}
