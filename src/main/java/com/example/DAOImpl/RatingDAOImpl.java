package com.example.DAOImpl;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.query.Query;
import com.example.DAO.*;
import com.example.Model.*;
@Repository
@Transactional
public class RatingDAOImpl implements RatingDAO{
	
	@Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
    
    @Override
    public void addRating(Rating rating) {
    	getCurrentSession().save(rating);
    }
	@Override
	public ArrayList<Rating> getRating(String productName) {
		String hql = "FROM Rating r WHERE r.product.productname = :productname";
        List<Rating> rating = getCurrentSession().createQuery(hql, Rating.class)
                .setParameter("productname", productName)
                .list();
		   System.out.println(rating.size());
	        return  new ArrayList<>(rating);
	}

	@Override
	public ArrayList<Rating> getAllRating() {
		return new ArrayList<>(getCurrentSession().createQuery("from Rating", Rating.class).list());
	}

}
