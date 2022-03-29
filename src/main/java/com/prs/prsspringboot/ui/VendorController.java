package com.prs.prsspringboot.ui;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;
import com.prs.prsspringboot.business.Vendor;
import com.prs.prsspringboot.data.VendorRepository;

@RestController
@RequestMapping("/vendors")
public class VendorController {
	
	@Autowired
	private VendorRepository vendorRepo;

	@GetMapping("/")
	public List<Vendor> getAllVendors() {
		List<Vendor> vendors = new ArrayList<Vendor>();
		
		try {
			Iterable<Vendor> result = vendorRepo.findAll();
			result.forEach(v -> vendors.add(v));
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return vendors;
	}
	
	@GetMapping("/{id}")
	public List<Vendor> getVendorById(@PathVariable int id) {
		List<Vendor> vendors = new ArrayList<Vendor>();
		
		try {
			Optional<Vendor> result = vendorRepo.findById(id);
			result.ifPresent(v -> vendors.add(v));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return vendors;
	}
	
	@CrossOrigin
	@PostMapping
	public List<Vendor> createVendor(@RequestBody Vendor vendor) {
		List<Vendor> vendors = new ArrayList<Vendor>();
		
		try {
			vendors.add(vendorRepo.save(vendor));
		} catch (DataIntegrityViolationException dive) {
			System.out.println(dive.getMessage());
		}
		return vendors;
	}
	
	@CrossOrigin
	@PutMapping("/{id}")
	public List<Vendor> updateVendor(@PathVariable("id") int id, @RequestBody Vendor vendor) {
		List<Vendor> vendors = new ArrayList<Vendor>();
		
		if (vendorRepo.existsById(id)) {
			try {
				vendors.add(vendorRepo.save(vendor));
			} catch (DataIntegrityViolationException dive) {
				System.out.println(dive.getMessage());
			}
		}
		return vendors;
	}
	
	@CrossOrigin
	@DeleteMapping("/{id}")
	public boolean deleteVendor(@PathVariable("id") int id) {
		
		if (vendorRepo.existsById(id)) {
			try {
				vendorRepo.deleteById(id);
				return true;
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return false;
	}
}
