package com.prs.prsspringboot.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import com.prs.prsspringboot.business.LineItem;
import com.prs.prsspringboot.business.Request;
import com.prs.prsspringboot.data.LineItemRepository;
import com.prs.prsspringboot.data.RequestRepository;

@RestController
@RequestMapping("/line-items")
public class LineItemController {

	@Autowired
	private LineItemRepository itemRepo;
	@Autowired
	private RequestRepository requestRepo;
	
	@GetMapping("/")
	public List<LineItem> getAllLineItems() {
		List<LineItem> items = new ArrayList<LineItem>();
		
		try {
			Iterable<LineItem> result = itemRepo.findAll();
			result.forEach(i -> items.add(i));
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return items;
	}
	
	@CrossOrigin
	@PutMapping
	public List<LineItem> getAllByRequest(@RequestBody Request request) {
		List<LineItem> items = new ArrayList<LineItem>();
		
		try {
			items = itemRepo.findByRequest(request);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return items;
	}
	
	@GetMapping("/{id}")
	public List<LineItem> getLineItemById(@PathVariable int id) {
		List<LineItem> items = new ArrayList<LineItem>();
		
		try {
			Optional<LineItem> result = itemRepo.findById(id);
			result.ifPresent(i -> items.add(i));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return items;
	}
	
	@CrossOrigin
	@PostMapping
	public List<LineItem> createLineItem(@RequestBody LineItem lineItem) {
		List<LineItem> items = new ArrayList<LineItem>();
		
		try {
			items.add(itemRepo.save(lineItem));
			recalculateTotal(lineItem.getRequest());
		} catch (DataIntegrityViolationException dive) {
			System.out.println(dive.getMessage());
		}
		
		return items;
	}
	
	@CrossOrigin
	@PutMapping("/{id}")
	public List<LineItem> updateLineItem(@PathVariable("id") int id, @RequestBody LineItem lineItem) {
		List<LineItem> items = new ArrayList<LineItem>();
		
		if (itemRepo.existsById(id)) {
			try {
				items.add(itemRepo.save(lineItem));
				recalculateTotal(lineItem.getRequest());
			} catch (DataIntegrityViolationException dive) {
				System.out.println(dive.getMessage());
			}
		}
		return items;
	}
	
	@CrossOrigin
	@DeleteMapping("/{id}")
	public boolean deleteLineItem(@PathVariable("id") int id, @RequestBody LineItem lineItem) {
		
		if (itemRepo.existsById(id)) {
			try {
				itemRepo.deleteById(id);
				recalculateTotal(lineItem.getRequest());
				return true;
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return false;
	}
	
	private void recalculateTotal(Request request) {
		double newTotal = 0.0;
		List<LineItem> items = new ArrayList<LineItem>();
		
		try {
			Iterable<LineItem> result = itemRepo.findByRequestId(request.getId());
			result.forEach(i -> items.add(i));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		for (LineItem item : items) {
			newTotal += (item.getProduct().getPrice() * item.getQuantity());
		}
		
		request.setTotal(newTotal);
		
			if (requestRepo.existsById(request.getId())) {
				try {
					requestRepo.save(request);
				} catch (DataIntegrityViolationException dive) {
					System.out.println(dive.getMessage());
				}
			}
	}
}
