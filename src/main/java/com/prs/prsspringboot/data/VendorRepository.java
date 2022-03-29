package com.prs.prsspringboot.data;

import org.springframework.data.repository.CrudRepository;

import com.prs.prsspringboot.business.Vendor;

public interface VendorRepository extends CrudRepository<Vendor, Integer> {

}
