package com.example.Model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;



@Entity
public class Orders {
	public Orders() {}
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="orderID")
	private int orderID;
	
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name= "username")
	private User user;
	
	@Column(name ="orderquantity")
    private int orderquantity;
	
	
	@Column(name ="status")
    private String status;
	
	@ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name = "Product_Orders",
               joinColumns = @JoinColumn(name = "order_id"),
               inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> product;
	
	
	
	
	
	public Orders(User user, int orderquantity, String status, List<Product> product) {
		super();
		this.user = user;
		this.orderquantity = orderquantity;
		this.status = status;
		this.product = product;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	

	public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getOrderquantity() {
		return orderquantity;
	}

	public void setOrderquantity(int orderquantity) {
		this.orderquantity = orderquantity;
	}

	public List<Product> getProduct() {
		return product;
	}

	public void setProduct(List<Product> product) {
		this.product = product;
	}
    
	
		
}
