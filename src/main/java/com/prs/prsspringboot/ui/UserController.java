package com.prs.prsspringboot.ui;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;
import com.prs.prsspringboot.business.User;
import com.prs.prsspringboot.data.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserRepository userRepo;
	
	@CrossOrigin
	@PostMapping("/authenticate")
	public List<User> authenticate(@RequestBody User user) {
		List<User> users = new ArrayList<User>();
		
		try {
			Optional<User> result = userRepo.findByUsernameAndPassword(user.getUsername(), user.getPassword());
			result.ifPresent(u -> users.add(u));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return users;
	}
	
	@GetMapping("/")
	public List<User> getAllUsers() {
		List<User> users = new ArrayList<User>();
		
		try {
			Iterable<User> result = userRepo.findAll();
			result.forEach(u -> users.add(u));
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return users;
	}
	
	@GetMapping("/{id}")
	public List<User> getUserById(@PathVariable int id) {
		List<User> users = new ArrayList<User>();
		
		try {
			Optional<User> result = userRepo.findById(id);
			result.ifPresent(u -> users.add(u));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return users;
	}
	
	@CrossOrigin
	@PostMapping
	public List<User> createUser(@RequestBody User user) {
		List<User> users = new ArrayList<User>();
		
		try {
			users.add(userRepo.save(user));
		} catch (DataIntegrityViolationException dive) {
			System.out.println(dive.getMessage());
		}
		
		return users;
	}
	
	@CrossOrigin
	@PutMapping("/{id}")
	public List<User> updateUser(@PathVariable("id") int id, @RequestBody User user) {
		List<User> users = new ArrayList<User>();
		
		if (userRepo.existsById(id)) {
			try {
				users.add(userRepo.save(user));
			} catch (DataIntegrityViolationException dive) {
				System.out.println(dive.getMessage());
			}
		}
		return users;
	}
	
	@CrossOrigin
	@DeleteMapping("/{id}")
	public boolean deleteUser(@PathVariable("id") int id) {
		
		if (userRepo.existsById(id)) {
			try {
				userRepo.deleteById(id);
				return true;
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return false;
	}
}
