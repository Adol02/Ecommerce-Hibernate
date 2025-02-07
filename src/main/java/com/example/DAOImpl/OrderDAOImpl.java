package com.example.DAOImpl;



import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.query.Query;
import com.example.DAO.OrderDAO;
import com.example.Model.*;
@Repository
@Transactional
public class OrderDAOImpl implements OrderDAO{
	
	@Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
	
  
	@Override
	public void addCheckout(Orders order) {
		 getCurrentSession().save(order);
	}

	@Override
	public List<Orders> getAllOrders() {
		 return getCurrentSession().createQuery("from Orders", Orders.class).list();
	}

	

    @Override
    @Transactional
    public void updateOrderStatus(int orderID, String status) {
    	   Session session = getCurrentSession();
           Orders order = session.get(Orders.class, orderID);
           if (order != null) {
               order.setStatus(status);
               session.update(order);
           }
    }

    @Override
    public ArrayList<Orders> getOrdersByUserName(String username) {
    	String hql = "FROM Orders WHERE user.username = :username";
        List<Orders> orders = getCurrentSession().createQuery(hql, Orders.class)
                .setParameter("username", username)
                .list();
        
//        for (Orders order : orders) {
//            Hibernate.initialize(order.getProduct());
//        }
        return new ArrayList<>(orders);
        


}
}


