package com.hashedin.hu.repositories;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.hashedin.hu.model.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class MovieRepository {

    private static Logger logger = LoggerFactory.getLogger(MovieRepository.class);

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public Movie save(Movie movie){
        dynamoDBMapper.save(movie);
        logger.debug("movie detail save to DynamoDb");
        return movie;
    }

    public List<Movie> saveAll(List<Movie> movieList){
        dynamoDBMapper.batchSave(movieList);
        logger.debug("List of movies saved to DynamoDb");
        return movieList;
    }

    public List<Movie> findMoviesByDirectorAndYear(String director, int lower, int upper){
        HashMap<String, AttributeValue> condition=new HashMap<>();

        condition.put(":v1",new AttributeValue().withS(director));
        condition.put(":v2",new AttributeValue().withN(String.valueOf(lower)));
        condition.put(":v3",new AttributeValue().withN(String.valueOf(upper)));

        DynamoDBScanExpression expression=new DynamoDBScanExpression()
                .withFilterExpression("director = :v1 and yearIn >= :v2 and yearIn <= :v3")
                .withExpressionAttributeValues(condition);

        List<Movie> dbResult= dynamoDBMapper.scan(Movie.class,expression);

        logger.debug("List of movies received DynamoDb {}",dbResult.size());
        return dbResult;
    }

    public List<Movie> findByReview(int review){

        String lang="English";
        HashMap<String, AttributeValue> condition=new HashMap<>();

        condition.put(":v1",new AttributeValue().withS(lang));
        condition.put(":v2",new AttributeValue().withS(String.valueOf(review)));

        DynamoDBScanExpression expression=new DynamoDBScanExpression()
                .withFilterExpression("languageMovie = :v1 and userReview > :v2")
                .withExpressionAttributeValues(condition);


        List<Movie> dbResult= dynamoDBMapper.scan(Movie.class,expression);

        logger.debug("List of movies received DynamoDb {}",dbResult.size());

        return dbResult;
    }

    public List<Movie> findByYearAndCountry(int year,String country){

        HashMap<String, AttributeValue> condition=new HashMap<>();

        condition.put(":v1",new AttributeValue().withS(country));
        condition.put(":v2",new AttributeValue().withN(String.valueOf(year)));

        DynamoDBScanExpression expression=new DynamoDBScanExpression()
                .withFilterExpression("country = :v1 and yearIn = :v2")
                .withExpressionAttributeValues(condition);

        List<Movie> dbResult= dynamoDBMapper.scan(Movie.class,expression);

        logger.debug("List of movies received DynamoDb {}",dbResult.size());

        return dbResult;

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

}