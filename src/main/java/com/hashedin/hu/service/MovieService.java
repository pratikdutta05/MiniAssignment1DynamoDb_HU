package com.hashedin.hu.service;

import com.hashedin.hu.model.Movie;
import java.util.List;

public interface MovieService {

    List<Movie> loadDatainDynamoDb();

    List<Movie> findAll();

    Movie findById(String id);

    List<Movie> findByDirector(String director,int lower,int upper);

    List<String> findByReview(int review);
}
