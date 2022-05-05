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
import javax.servlet.http.HttpServletResponse;
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
    public ResponseEntity<List<Movie>> findAll(HttpServletResponse res){
        long startTime = System.currentTimeMillis();
        logger.info("find All movies " + this.getClass().getName());

        List<Movie> result=movieService.findAll();

        long endTime = System.currentTimeMillis();

        calculateExecutionTime(startTime,endTime,res);

        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> findById(@PathVariable(value = "id") String id,
                                          HttpServletResponse res){
        long startTime = System.currentTimeMillis();
        logger.info("find movie by id {}" ,id);
        Movie result=movieService.findById(id);
        long endTime = System.currentTimeMillis();

        calculateExecutionTime(startTime,endTime,res);

        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    /*
    Assignment Question number 1, Find based on Director and Movie year
     */
    @GetMapping("/director/{director}")
    public ResponseEntity<List<Movie>>findByDirectorAndYear(@PathVariable(value = "director") String director,
                                                            @RequestHeader("lower") int lower,
                                                            @RequestHeader("upper") int upper,
                                                            HttpServletResponse res){

        long startTime = System.currentTimeMillis();
        logger.info("find movie by director{} and movie year {} to {}",director,lower,upper);
        List<Movie> result=movieService.findByDirectorAndYear(director,lower,upper);
        long endTime = System.currentTimeMillis();

        calculateExecutionTime(startTime,endTime,res);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/english/{review}")
    public ResponseEntity<List<String>> findEnglishMovieByReview(@PathVariable(value = "review") int review,
                                                                 HttpServletResponse res){

        long startTime = System.currentTimeMillis();

        logger.info("find English movie by User Review");

        List<String> result= movieService.findByReview(review);

        long endTime = System.currentTimeMillis();

        calculateExecutionTime(startTime,endTime,res);

        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @GetMapping("/{year}/{country}")
    public ResponseEntity<String> findEnglishMovieByReview(@PathVariable(value = "year") int year,
                                                           @PathVariable(value = "country") String country,
                                                           HttpServletResponse res){

        long startTime = System.currentTimeMillis();
        logger.info("find Max Budget movie by year and country");

        String result=movieService.findByYearAndCountry(year,country);

        long endTime = System.currentTimeMillis();

        calculateExecutionTime(startTime,endTime,res);

        return new ResponseEntity<>(result,HttpStatus.OK);
    }


    @GetMapping("/save-from-csv")
    public ResponseEntity<List<Movie>> saveAll(HttpServletResponse res){

        long startTime = System.currentTimeMillis();
        logger.info("save movies from csv to dynamoDb");
        List<Movie> result=movieService.loadDataInDynamoDb();
        long endTime = System.currentTimeMillis();

        calculateExecutionTime(startTime,endTime,res);

        return new ResponseEntity<>(result,HttpStatus.CREATED);
    }

    private  void calculateExecutionTime(Long startTime, Long endTime, HttpServletResponse response){

        Long time=endTime-startTime;
        logger.debug("Execution Time {} ms ",time);
        response.addHeader("X-TIME-TO-EXECUTE",time.toString()+" ms");
    }

}
