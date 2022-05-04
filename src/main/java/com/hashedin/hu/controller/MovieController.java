package com.hashedin.hu.controller;


import com.hashedin.hu.model.Movie;
import com.hashedin.hu.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/movie")
public class MovieController {

    private static Logger logger = LoggerFactory.getLogger(MovieController.class);

    MovieService movieService;
    @Autowired
    public MovieController(MovieService movieService){
        this.movieService=movieService;
    }

    @GetMapping
    public ResponseEntity<List<Movie>> findAll(){
        logger.info("find All movies " + this.getClass().getName());
        return new ResponseEntity<>(movieService.findAll(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> findById(@PathVariable(value = "id") String id){
        logger.info("find movie by id {}" ,id);
        return new ResponseEntity<>(movieService.findById(id),HttpStatus.OK);
    }

    /*
    Assignment Question number 1, Find based on Director and Movie year
     */
    @GetMapping("/director/{director}")
    public ResponseEntity<List<Movie>>findByDirectorAndYear(@PathVariable(value = "director") String director,
                                                            @RequestHeader("lower") int lower,
                                                            @RequestHeader("upper") int upper){

        logger.info("find movie by director{} and movie year {} to {}",director,lower,upper);
        return new ResponseEntity<>(movieService.findByDirectorAndYear(director,lower,upper), HttpStatus.OK);
    }

    @GetMapping("/english/{review}")
    public ResponseEntity<List<String>> findEnglishMovieByReview(@PathVariable(value = "review") int review){

        logger.info("find English movie by User Review");

        return new ResponseEntity<>(movieService.findByReview(review),HttpStatus.OK);
    }

    @GetMapping("/{year}/{country}")
    public ResponseEntity<String> findEnglishMovieByReview(@PathVariable(value = "year") int year,
                                                           @PathVariable(value = "country") String country){

        logger.info("find Max Budget movie by year and country");

        return new ResponseEntity<>(movieService.findByYearAndCountry(year,country),HttpStatus.OK);
    }


    @GetMapping("/save-from-csv")
    public ResponseEntity<List<Movie>> saveAll(){
        logger.info("save movies from csv to dynamoDb");
        return new ResponseEntity<>(movieService.loadDataInDynamoDb(),HttpStatus.CREATED);
    }

}
