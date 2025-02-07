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
import jakarta.persistence.OneToOne;



@Entity
public class Cart {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="cartID")
	private int cartID;
	
	
	@Column(name ="cartquantity")
	private int quantity;
	

    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "username")
    private User user;
    
    public Cart() {}
    
    public Cart(int quantity, User user, List<Product> product) {
		super();
		this.quantity = quantity;
		this.user = user;
		this.product = product;
	}

	@ManyToMany(cascade=CascadeType.ALL,  fetch=FetchType.EAGER)
	@JoinTable(
	        name = "cart_product",
	        joinColumns = @JoinColumn(name = "cart_id"),
	        inverseJoinColumns = @JoinColumn(name = "product_id")
	    )
    private List<Product> product;

   

	public int getCartID() {
		return cartID;
	}

	public void setCartID(int cartID) {
		this.cartID = cartID;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Product> getProduct() {
		return product;
	}

	public void setProduct(List<Product> product) {
		this.product = product;
	}
    
    

	

  
}
