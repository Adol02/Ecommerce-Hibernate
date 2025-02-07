package com.example.DAOImpl;

import java.util.ArrayList;
import java.util.List;

import com.example.DAO.ProductDAO;
import com.example.Model.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ProductDAOImpl implements ProductDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void addProduct(Product product) {
        getCurrentSession().save(product);
    }

    @Override
    public ArrayList<Product> getAllProducts() {
        List<Product> products = getCurrentSession().createQuery("FROM Product", Product.class).list();
        return new ArrayList<>(products);
    }

    @Override
    public void updateProduct(Product product) {
    	Session session = getCurrentSession();
        Product existingProduct = session.get(Product.class, product.getId());
        
        if (existingProduct != null) {
            // Update the fields of the existing product
            existingProduct.setPrice(product.getPrice());
            existingProduct.setQuantity(product.getQuantity());
            
            session.update(existingProduct);
    }
    }

    @Override
    public void deleteProduct(String productname) {
        String hql = "DELETE FROM Product WHERE productname = :productname";
        getCurrentSession().createQuery(hql)
                .setParameter("productname", productname)
                .executeUpdate();
    }

    @Override
    public ArrayList<Product> getByCategory(String category) {
        String hql = "FROM Product WHERE category = :category";
        List<Product> products = getCurrentSession().createQuery(hql, Product.class)
                .setParameter("category", category)
                .list();
        return new ArrayList<>(products);
    }

    @Override
    public Product getProductByName(String productName) {
        String hql = "FROM Product WHERE name = :name";
        return getCurrentSession().createQuery(hql, Product.class)
                .setParameter("name", productName)
                .uniqueResult();
    }

    @Override
    public void reduceProductQuantity(String productname, int quantity) {
       
            Session session = sessionFactory.getCurrentSession();
            Product product = session.createQuery("FROM Product WHERE productname = :productname", Product.class)
                                     .setParameter("productname", productname)
                                     .uniqueResult();
            if (product != null) {
                product.setQuantity(product.getQuantity() - quantity);
                session.update(product);
            }
        
    }
    
}


//String hql = "UPDATE Product SET quantity = :quantity WHERE productname = :productname";
//getCurrentSession().createQuery(hql, Product.class)
//      .setParameter("quantity", quantity)
//      .setParameter("productname", productname)
//      .executeUpdate();
