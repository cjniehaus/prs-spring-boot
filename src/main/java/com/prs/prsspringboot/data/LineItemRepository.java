package com.prs.prsspringboot.data;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.prs.prsspringboot.business.LineItem;
import com.prs.prsspringboot.business.Request;

public interface LineItemRepository extends CrudRepository<LineItem, Integer> {

	Iterable<LineItem> findByRequestId(int requestId);
	
	List<LineItem> findByRequest(Request request);
}
