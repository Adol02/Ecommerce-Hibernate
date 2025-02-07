package com.example.Service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.DAO.*;
import com.example.Model.*;
@Service
public class RatingService {
	
	@Autowired
	private RatingDAO ratingDAO;
	
	public void addRating(Rating rating) {
        ratingDAO.addRating(rating);
    }
	public ArrayList<Rating> getRating(String p) {
		return ratingDAO.getRating(p);
	}
	public ArrayList<Rating> getAllRating() {
		return ratingDAO.getAllRating();
	}
}

