package com.hashedin.hu.repositories;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.hashedin.hu.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MovieRepository {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public Movie save(Movie movie){
        dynamoDBMapper.save(movie);
        return movie;
    }

    public List<Movie> saveAll(List<Movie> movieList){
        dynamoDBMapper.batchSave(movieList);
        return movieList;
    }

    public Movie findById(String id){
       return dynamoDBMapper.load(Movie.class, id);
    }

    public List<Movie> findAll(){
        return dynamoDBMapper.scan(Movie.class, new DynamoDBScanExpression());
    }

    public String update(String id, Movie movie){
        dynamoDBMapper.save(movie,
                new DynamoDBSaveExpression()
        .withExpectedEntry("id",
                new ExpectedAttributeValue(
                        new AttributeValue().withS(id)
                )));
        return id;
    }

    public String delete(String id){
       Movie movie = dynamoDBMapper.load(Movie.class, id);
        dynamoDBMapper.delete(movie);
        return "Movie deleted successfully:: "+id;
    }

    public List<Movie> findByDirector(String director){

        return (List<Movie>) dynamoDBMapper.load(Movie.class, director);
    }


}