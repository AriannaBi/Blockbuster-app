package com.example.blockbuster.Service;

import com.example.blockbuster.Movie;
import com.example.blockbuster.Repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    /**
     * Constructor for MovieService
     * @param movieRepository MovieRepository
     */
    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    /**
     * Given a movie, create or save it in the movie repository
     * @param movie Movie
     * @return the Movie created/saved
     */
    public Movie create(Movie movie) {
        return movieRepository.save(movie);
    }

    /**
     * Given a movie id, return the movie from the repo
     * @param id movie id
     * @return Optional of movie
     */
    public Optional<Movie> findById(String id) {
        return movieRepository.findById(id);
    }

    /**
     * Return a list of all the movie in the repo
     * @return list movie in repo
     */
    public List<Movie> findAll() {
        return movieRepository.findAll();
    }



}
