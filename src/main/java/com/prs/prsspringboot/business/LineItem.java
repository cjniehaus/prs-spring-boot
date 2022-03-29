package com.prs.prsspringboot.business;

import javax.persistence.*;

@Entity
@Table(name = "LineItems")
public class LineItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name = "RequestId")
	private Request request;
	@ManyToOne
	@JoinColumn(name = "ProductId")
	private Product product;
	private int quantity;
	
	public LineItem() {
	}
	
	public LineItem(int id, Request request, Product product, int quantity) {
		super();
		this.id = id;
		this.request = request;
		this.product = product;
		this.quantity = quantity;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Request getRequest() {
		return request;
	}
	public void setRequest(Request request) {
		this.request = request;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	@Override
	public String toString() {
		return "LineItem [id=" + id + ", request=" + request.toString() + ", product=" + product.toString() + ", quantity=" + quantity + "]";
	}
}
