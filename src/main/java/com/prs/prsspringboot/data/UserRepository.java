package com.prs.prsspringboot.data;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.prs.prsspringboot.business.User;

public interface UserRepository extends CrudRepository<User, Integer> {
	
	Optional<User> findByUsernameAndPassword(String username, String password);
}
