package com.example.Service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.DAO.*;
import com.example.Model.Product;
 @Service
public class ProductService {
	 
	 @Autowired
	private ProductDAO productDAO;

    public void addProduct(Product product) {
        productDAO.addProduct(product);
    }
 
    public ArrayList<Product> getAllProducts() {
        return productDAO.getAllProducts();
    }
 
    public void updateProduct(Product product) {
        productDAO.updateProduct(product);
    }
 
    public void deleteProduct(String name) {
        productDAO.deleteProduct(name);
    }
    public Product getProductByName(String productName) {
        return productDAO.getProductByName(productName);
    }
	public ArrayList<Product> getByCategory(String category) {
		return productDAO.getByCategory(category);
	       
	}

	public void reduceProductQuantity(String name, int quantity) {
		productDAO.reduceProductQuantity(name, quantity);
		
	}
}
