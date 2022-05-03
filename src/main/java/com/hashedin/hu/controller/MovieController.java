package com.hashedin.hu.controller;


import com.hashedin.hu.model.Movie;
import com.hashedin.hu.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.QueryAnnotation;
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

    @Autowired
    MovieService movieService;

    @GetMapping("/save-from-csv")
    public List<Movie> saveAll(){
        logger.info("findAll movies " + this.getClass().getName());
         return movieService.loadDatainDynamoDb();
    }

    @GetMapping
    public List<Movie> findAll(){
       logger.info("findAll movies " + this.getClass().getName());
        return movieService.findAll();
    }

    @GetMapping("/{id}")
    public Movie findById(@PathVariable(value = "id") String id){
        logger.info("find movie by id" + this.getClass().getName());
        return movieService.findById(id);
    }

    /*
    Assignment Question number 1, Find based on Director and Movie year
     */
    @GetMapping("/director/{director}")
    public List<Movie> findByDirectorAndYear(@PathVariable(value = "director") String director,
                                      @RequestHeader("lower") int lower, @RequestHeader("upper") int upper){
        logger.info("find movie by director and movie year" + this.getClass().getName());
        return movieService.findByDirector(director,lower,upper);
    }

    @GetMapping("/english/{review}")
    public List<String> findEnglishMovieByReview(@PathVariable(value = "review") int review){

        logger.info("find English movie by User Review" + this.getClass().getName());

        return movieService.findByReview(review);
    }

}
