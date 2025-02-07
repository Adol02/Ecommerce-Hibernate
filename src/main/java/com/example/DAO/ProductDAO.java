package com.example.DAO;

import java.util.*;

import com.example.Model.Product;
 
public interface ProductDAO {
    void addProduct(Product product);
    ArrayList<Product> getAllProducts();
    void updateProduct(Product product);
    void deleteProduct(String name);
	ArrayList<Product> getByCategory(String category);
	Product getProductByName(String productName);
	void reduceProductQuantity(String productName, int quantity);
}
