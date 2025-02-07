package com.example.Service;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.DAO.*;
import com.example.Model.*;
@Service
public class OrderService {
	@Autowired
	    private OrderDAO orderDAO;
	 
	    public void addCheckout(Orders checkout) {
	    	orderDAO.addCheckout(checkout);
	    }
	 
	    public ArrayList<Orders> getAllOrders() {
	        return new ArrayList<>(orderDAO.getAllOrders());
	    }
	 
	    public void updateOrderStatus(int checkoutID, String status) {
	    	orderDAO.updateOrderStatus(checkoutID, status);
	    }
	 
	    public ArrayList<Orders> getOrdersByUserName(String username) {
	        return new ArrayList<>(orderDAO.getOrdersByUserName(username));
	    }

//	    public Map<String, Double> generateTotalSalesReport() {
//			return orderDAO.generateTotalSalesReport();
//	    }
		
	}

