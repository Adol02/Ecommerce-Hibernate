
package com.example.Model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;



@Entity
public class Product {
	
	public Product(String name, double price, int quantity) {
		super();
		this.productname = name;
		this.price = price;
		this.quantity = quantity;
	}

	public Product(String category, String name, String description, double price, int quantity) {
		super();
		this.category = category;
		this.productname = name;
		this.description = description;
		this.price = price;
		this.quantity = quantity;
	}



	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="productId")
    private int id;
	@Column(name ="category")
    private String category;
	@Column(name ="productname")
    private String productname;
	@Column(name ="description")
    private String description;
	@Column(name ="price")
    private double price;
	@Column(name ="quantity")
    private int quantity;
	
 
    public Product() {}
 
    public Product(int id, String productName, double price, int quantity) {
        this.id = id;
        this.productname = productName;
        this.price = price;
        this.quantity = quantity;
    }


	public Product(int id, String productname) {
		super();
		this.id = id;
		this.productname = productname;
	}



	@ManyToMany(mappedBy = "product" , cascade = CascadeType.ALL,  fetch=FetchType.EAGER)
    private List<Cart> cart;
    
    @ManyToMany(mappedBy = "product",cascade = CascadeType.ALL)
    private List<WishList> wishList;
    
    @ManyToMany(mappedBy ="product",cascade = CascadeType.ALL)
    private List<Orders> orders;
    
    @OneToMany(mappedBy = "product")
    private List<Rating> rating;
    
    
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getproductname() {
		return productname;
	}

	public void setproductname(String name) {
		this.productname = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public List<Cart> getCart() {
		return cart;
	}

	public void setCart(List<Cart> cart) {
		this.cart = cart;
	}

	public List<WishList> getWishList() {
		return wishList;
	}

	public void setWishList(List<WishList> wishList) {
		this.wishList = wishList;
	}

	public List<Orders> getOrders() {
		return orders;
	}

	public void setOrder(List<Orders> order) {
		this.orders = order;
	}

	public List<Rating> getRating() {
		return rating;
	}

	public void setRating(List<Rating> rating) {
		this.rating = rating;
	}

	@Override
    public String toString() {
        return "Product [id=" + id + ", category=" + category +", name=" + productname + ", description=" + description + ", price=" + price + ", quantity=" + quantity + "]";
    }

	
}
