package com.example.DAOImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.DAO.WishListDAO;
import com.example.Model.Product;
import com.example.Model.User;
import com.example.Model.WishList;

@Repository
@Transactional
public class WishListDAOImpl implements WishListDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void addToWishList(WishList wishList) {
    	  // Fetch the existing user from the database to avoid duplicates
        User existingUser = getCurrentSession().get(User.class, wishList.getUser().getUserId());
        if (existingUser != null) {
            wishList.setUser(existingUser);
        }

        // Fetch the existing products from the database to avoid duplicates
        List<Product> managedProducts = new ArrayList<>();
        for (Product product : wishList.getProduct()) {
            Product existingProduct = getCurrentSession().get(Product.class, product.getId());
            if (existingProduct != null) {
                managedProducts.add(existingProduct);
            } else {
                managedProducts.add(product);
            }
        }
        wishList.setProduct(managedProducts);

        // Save or update the wishlist
        getCurrentSession().merge(wishList);
    }
    @Override
    public ArrayList<WishList> getWishList(String username) {
        String hql = "FROM WishList w JOIN FETCH w.product p WHERE w.user.username = :username";
        List<WishList> wishList = getCurrentSession().createQuery(hql, WishList.class)
                .setParameter("username", username)
                .getResultList();
        
        System.out.println("Fetched Wishlist size: " + wishList.size());
        for (WishList w : wishList) {
            Hibernate.initialize(w.getProduct());
            System.out.println("Products in wishlist: " + w.getProduct().size());
            for (Product p : w.getProduct()) {
                System.out.println(p);
            }
        }
        
        return new ArrayList<>(wishList);
    }


    @Override
    public void deleteFromWishlist(String username, String productName) {
        String hql = "FROM WishList w WHERE w.user.username = :username";
        List<WishList> wishLists = getCurrentSession().createQuery(hql, WishList.class)
                .setParameter("username", username)
                .getResultList();

        for (WishList wishList : wishLists) {
            Product productToRemove = null;
            for (Product product : wishList.getProduct()) {
                if (product.getproductname().equals(productName)) {
                    productToRemove = product;
                    break;
                }
            }

            if (productToRemove != null) {
                wishList.getProduct().remove(productToRemove);
                getCurrentSession().update(wishList);
            }
        }
    }


    @Override
    public ArrayList<WishList> getAllWishlist() {
        List<WishList> list = getCurrentSession().createQuery("FROM WishList", WishList.class).list();
        
        // Initialize the products
        for (WishList w : list) {
            Hibernate.initialize(w.getProduct());
        }
        
        return new ArrayList<>(list);
    }
}
