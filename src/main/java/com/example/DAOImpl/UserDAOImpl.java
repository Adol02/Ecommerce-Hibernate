package com.example.DAOImpl;

import java.util.ArrayList;
import java.util.List;

import com.example.DAO.UserDAO;
import com.example.Model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class UserDAOImpl implements UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void addUser(User user) {
        getCurrentSession().save(user);
    }

    @Override
    public User getUser(String username) {
        String hql = "FROM User WHERE userName = :username";
        return getCurrentSession().createQuery(hql, User.class)
                .setParameter("username", username)
                .uniqueResult();
    }

    @Override
    public ArrayList<User> getAllUsers() {
        List<User> users = getCurrentSession().createQuery("FROM User", User.class).list();
        return new ArrayList<>(users);
    }
}
