package com.example.Service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.DAO.*;
import com.example.Model.*;
@Service
public class WishListService {
	@Autowired
	 private WishListDAO wishListDAO;
	   

	    public void addToWishList(WishList wishList) {
	    	wishListDAO.addToWishList(wishList);
	    }
	    public ArrayList<WishList> getWishList(String username) {
	        return wishListDAO.getWishList(username);
	    }
	    public void deleteFromWishlist(String currentuser, String checkoutProductName) {
	    	wishListDAO.deleteFromWishlist(currentuser, checkoutProductName);
		}
	    public ArrayList<WishList> getAllWishList() {
	        return wishListDAO.getAllWishlist();
	    }
	    
}
