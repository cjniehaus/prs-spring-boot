package com.prs.prsspringboot.ui;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import com.prs.prsspringboot.business.Request;
import com.prs.prsspringboot.business.User;
import com.prs.prsspringboot.data.RequestRepository;;

@RestController
@RequestMapping("/requests")
public class RequestController {
	
	@Autowired
	private RequestRepository requestRepo;
	
	@GetMapping("/")
	public List<Request> getAllRequests() {
		List<Request> requests = new ArrayList<Request>();
		
		try {
			Iterable<Request> result = requestRepo.findAll();
			result.forEach(r -> requests.add(r));
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return requests;
	}
	
	@CrossOrigin
	@PutMapping
	public List<Request> getAllByUser(@RequestBody User user) {
		List<Request> requests = new ArrayList<Request>();
		
		try {
			requests = requestRepo.findByUser(user);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return requests;
	}
	
	@GetMapping("/{id}")
	public List<Request> getRequestById(@PathVariable int id) {
		List<Request> requests = new ArrayList<Request>();
		
		try {
			Optional<Request> result = requestRepo.findById(id);
			result.ifPresent(r -> requests.add(r));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return requests;
	}
	
	@CrossOrigin
	@PostMapping
	public List<Request> createRequest(@RequestBody Request request) {
		List<Request> requests = new ArrayList<Request>();
		
		request.setSubmittedDate(LocalDateTime.now());
		request.setStatus("New");
		
		try {
			requests.add(requestRepo.save(request));
		} catch (DataIntegrityViolationException dive) {
			System.out.println(dive.getMessage());
		}
		
		return requests;
	}
	
	@CrossOrigin
	@PutMapping("/{id}")
	public List<Request> updateRequest(@PathVariable("id") int id, @RequestBody Request request) {
		List<Request> requests = new ArrayList<Request>();
		
		if (requestRepo.existsById(id)) {
			try {
				requests.add(requestRepo.save(request));
			} catch (DataIntegrityViolationException dive) {
				System.out.println(dive.getMessage());
			}
		}
		return requests;
	}
	
	@CrossOrigin
	@DeleteMapping("/{id}")
	public boolean deleteRequest(@PathVariable("id") int id) {
		
		if (requestRepo.existsById(id)) {
			try {
				requestRepo.deleteById(id);
				return true;
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return false;
	}

}
