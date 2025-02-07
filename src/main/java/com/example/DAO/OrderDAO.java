package com.example.DAO;

import java.util.ArrayList;


import com.example.Model.*;
import java.util.*;
public interface OrderDAO {
	void addCheckout(Orders order);
    List<Orders> getAllOrders();
    void updateOrderStatus(int checkoutID, String status);
	List<Orders> getOrdersByUserName(String userName);
	
}
