package com.example.Model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;


@Entity
public class User {
	public User() {}
   
   	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private int userId;
   	
	    @Column(name = "username")
	    private String username;
	    @Column(name = "email")  
        private String email;
	    @Column(name = "address")
        private String address;
	    @Column(name = "password")
        private String password;
     
	    
    public User(String userName, String password, String email, String address) {
        this.username = userName;
        this.password = password;
        this.email = email;
        this.address = address;  
    }
    
    
    public User(String userName, String password) {
    	this.username = userName;
        this.password = password;
	}

	@OneToOne(cascade = CascadeType.ALL)
    private WishList wishList;
    
    @OneToOne(cascade = CascadeType.ALL)
    private Cart cart;

    
    @OneToMany(mappedBy = "user" ,cascade = CascadeType.ALL)
    private List<Rating> rating;
    
    
    @OneToMany(mappedBy= "user", fetch = FetchType.LAZY)
    private List<Orders> orders;


	public int getUserId() {
		return userId;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}


	public String getUserName() {
		return username;
	}


	public void setUserName(String userName) {
		this.username = userName;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public WishList getWishList() {
		return wishList;
	}


	public void setWishList(WishList wishList) {
		this.wishList = wishList;
	}


	public Cart getCart() {
		return cart;
	}


	public void setCart(Cart cart) {
		this.cart = cart;
	}


	public List<Rating> getRating() {
		return rating;
	}


	public void setRating(List<Rating> rating) {
		this.rating = rating;
	}


	public List<Orders> getOrders() {
		return orders;
	}


	public void setOrders(List<Orders> order) {
		this.orders = order;
	}
    
 
}
