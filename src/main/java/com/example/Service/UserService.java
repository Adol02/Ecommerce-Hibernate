package com.example.Service;

import com.example.Model.User;


import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.DAO.*;
import com.example.DAOImpl.*;

@Service
public class UserService  {
	@Autowired
    private UserDAO userDao;

    public void registerUser(User user) {
        userDao.addUser(user);
    }
    public ArrayList<User> getAllUsers() {
        return userDao.getAllUsers();
    }
 
    public boolean authenticateUser(String username, String password) {
        User user = userDao.getUser(username);
        return user != null && user.getPassword().equals(password);
    }
    public User getUser(String user) {
        return userDao.getUser(user);
    }
    
}
