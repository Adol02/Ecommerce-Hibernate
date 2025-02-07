package com.example.DAO;

import java.util.ArrayList;

import com.example.Model.Rating;

public interface RatingDAO {
	  void addRating(Rating rating);
	  ArrayList<Rating> getRating(String p);
	ArrayList<Rating> getAllRating();
}
