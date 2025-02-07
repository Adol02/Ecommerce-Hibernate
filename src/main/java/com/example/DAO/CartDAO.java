package com.example.DAO;

import java.util.*;

import com.example.Model.Cart;

public interface CartDAO {

    void addToCart(Cart cartItem);
    ArrayList<Cart> getCartItems(String username);
    void removeFromCart(String productName);
    void removeProductFromCart(String username, String productName);
}
