package com.hashedin.hu.service.impl;

import com.hashedin.hu.model.Movie;
import com.hashedin.hu.repositories.MovieRepository;
import com.hashedin.hu.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    private static Logger logger = LoggerFactory.getLogger(MovieService.class);

    @Autowired
    MovieRepository movieRepository;


    @Override
    public List<Movie> loadDatainDynamoDb() {

        logger.info("Save All Movies " + this.getClass().getName());
        boolean firstline=true;
        String line="";
        List<Movie> records = new ArrayList<>();
        try {
            Movie movie;
            BufferedReader bufferedReader=new BufferedReader(new FileReader("src/main/resources/movies.csv"));
            logger.info("File Read Successfull form csv");
            while((line=bufferedReader.readLine())!=null) {

                if(firstline){
                    firstline=false;
                    continue;
                }
                String[] values = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);


                movie=new Movie(values[0],values[1],values[4],Integer.parseInt(values[3]),values[5],values[6],values[7],values[8],
                        values[9],values[10],values[11],values[12],values[13],values[14],values[15],values[16],
                        values[17],values[18],values[19],values[20],values[21]);
                records.add(movie);

            }


            logger.info("Data Mapped successfull from csv to Model");

           return movieRepository.saveAll(records);


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Movie> findAll(){
        logger.info("findAll movies " + this.getClass().getName());
        return movieRepository.findAll();
    }

    @Override
    public Movie findById(String id){
        logger.info("find movie by id" + this.getClass().getName());
        return movieRepository.findById(id);
    }


    @Override
    public List<Movie> findByDirectorAndYear(String director,int lower,int upper) {

        return movieRepository.findMoviesByDirectorandYear(director,lower,upper);
    }

    /**
     * @param review User Review
     * @return List of Movie
     */

    @Override
    public List<String> findByReview(int review) {
        List<Movie> dbResult=movieRepository.findByReview(review);

        List<String> titles=dbResult.stream()
                .sorted(Comparator.comparing(Movie::getUserReview).reversed())
                .map(i->i.getTitle())
                .collect(Collectors.toList());

        return titles;
    }

    /**
     * @param year
     * @param country
     * @return
     */
    @Override
    public String findByYearAndCountry(int year, String country) {

        List<Movie> dbResult= movieRepository.findByYearAndCountry(year,country);

        Movie res= dbResult.stream()
                       .max(Comparator.comparing(Movie::getBudget)).get();

        return res.getTitle();

    }


}
