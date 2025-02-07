package com.example.Service;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.DAO.*;
import com.example.Model.*;
@Service
public class CartService {
	
    @Autowired
    private CartDAO cartDAO;
   

    public void addToCart(Cart cartItem) {
        cartDAO.addToCart(cartItem);
    }

    public ArrayList<Cart> getCartItems(String username) {
        return cartDAO.getCartItems(username);
    }

    public void removeFromCart(String productName) {
        cartDAO.removeFromCart(productName);
    }

    

	public void removeProductFromCart(String currentuser, String checkoutProductName) {
		cartDAO.removeProductFromCart(currentuser, checkoutProductName);
	}
}


