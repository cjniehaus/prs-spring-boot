package com.prs.prsspringboot.data;

import org.springframework.data.repository.CrudRepository;

import com.prs.prsspringboot.business.Product;

public interface ProductRepository extends CrudRepository<Product, Integer> {

}
