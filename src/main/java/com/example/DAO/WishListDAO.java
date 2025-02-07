package com.example.DAO;

import java.util.*;
import com.example.Model.WishList;

public interface WishListDAO {
	void addToWishList(WishList wishList);
	ArrayList<WishList> getWishList(String username);
	void deleteFromWishlist(String currentuser, String Name);
	ArrayList<WishList> getAllWishlist();
}
