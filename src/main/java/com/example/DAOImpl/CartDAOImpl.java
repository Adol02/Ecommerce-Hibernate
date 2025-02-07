package com.example.DAOImpl;

import java.util.*;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.DAO.*;
import com.example.Model.*;
import com.example.Service.ProductService;
@Repository
@Transactional
public class CartDAOImpl implements CartDAO {
	 @Autowired
	    private SessionFactory sessionFactory;
	   
	    public Session getCurrentSession() {
	        return sessionFactory.getCurrentSession();
	    }
	    @Autowired
	    ProductService productService;
	    
	    @Override
	    public void addToCart(Cart cartItem) {
	        Session session = getCurrentSession();
	        User user = cartItem.getUser(); // Assuming the user is already set in the cartItem

	        // Fetch the existing user from the session
	        User existingUser = session.get(User.class, user.getUserId());
	        if (existingUser != null) {
	            user = existingUser;
	        } else {
	            session.save(user);
	        }

	        // Fetch or create the cart associated with the user
	        Cart cart = user.getCart();
	        if (cart == null) {
	            cart = new Cart();
	            cart.setUser(user);
	            user.setCart(cart);
	            session.save(cart);
	        }

	        // Ensure the product list in the cart is initialized
	        List<Product> list = new ArrayList<>();
	        if (cart.getProduct() == null) {
	            cart.setProduct(list);
	        }

	        // Add the product to the cart
	        Product product = cartItem.getProduct().get(0); // Assuming only one product is being added at a time
	        int quantity = cartItem.getQuantity();

	        // Check if the product is already in the cart
	        String hql = "SELECT c FROM Cart c JOIN c.product p WHERE c.cartID = :cartID AND p.productname = :productName";
	        Cart existingCartProduct = session.createQuery(hql, Cart.class)
	                .setParameter("cartID", cart.getCartID())
	                .setParameter("productName", product.getproductname())
	                .uniqueResult();

	        if (existingCartProduct != null) {
	            // If the product is already in the cart, update the quantity
	            existingCartProduct.setQuantity(existingCartProduct.getQuantity() + quantity);
	            session.merge(existingCartProduct);
	        } else {
	            // If the product is not in the cart, add the product to the cart
	            cart.getProduct().add(product);
	            session.save(cart);
	        }

	        // Save or update the user and cart
	        session.merge(user); // Use merge to handle existing instances
	        session.saveOrUpdate(cart);
	    }



	    @Override
	    public ArrayList<Cart> getCartItems(String userName) {
	    
	            Session session = sessionFactory.getCurrentSession();
	            String hql = "FROM Cart c WHERE c.user.username = :username"; // Use 'userName' instead of 'username'
	            List<Cart> cartItems = session.createQuery(hql, Cart.class)
	                    .setParameter("username", userName)
	                    .getResultList();
	            return new ArrayList<>(cartItems);
	        }
	   

	    @Override
	    public void removeProductFromCart(String username, String productName) {
	    	Session session = sessionFactory.getCurrentSession();
	        
	    	// Step 1: Retrieve Cart ID for the given username
	        String cartIdQuery = "SELECT c.cartID FROM Cart c WHERE c.user.username = :username";
	        List<Integer> cartIds = session.createQuery(cartIdQuery, Integer.class)
	                                       .setParameter("username", username)
	                                       .getResultList();

	        // Step 2: Retrieve Product ID for the given product name
	        String productIdQuery = "SELECT p.id FROM Product p WHERE p.productname = :productname";
	        List<Integer> productIds = session.createQuery(productIdQuery, Integer.class)
	                                          .setParameter("productname", productName)
	                                          .getResultList();

	        // Step 3: Execute Delete Statements for each Cart and Product combination
	        for (Integer cartId : cartIds) {
	            for (Integer productId : productIds) {
	                String deleteQuery = "DELETE FROM cart_product WHERE cart_id = :cartId AND product_id = :productId";
	                session.createNativeQuery(deleteQuery)
	                       .setParameter("cartId", cartId)
	                       .setParameter("productId", productId)
	                       .executeUpdate();
	            }
	        }
	    }

	    @Override
	    public void removeFromCart(String productName) {
	        Session session = getCurrentSession();
	        String hql = "DELETE FROM Cart c WHERE c.product.productname = :productname";
	        session.createQuery(hql, Cart.class)
	                .setParameter("productname", productName)
	                .executeUpdate();
	    }
	}
