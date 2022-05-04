package com.hashedin.hu.service;

import com.hashedin.hu.model.Movie;
import java.util.List;

public interface MovieService {

    List<Movie> loadDataInDynamoDb();

    List<Movie> findAll();

    Movie findById(String id);

    List<Movie> findByDirectorAndYear(String director,int lower,int upper);

    List<String> findByReview(int review);

    String findByYearAndCountry(int year,String country);


}
