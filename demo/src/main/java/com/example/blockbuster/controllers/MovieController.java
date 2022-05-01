package com.example.blockbuster.controllers;

import com.example.blockbuster.Movie;
import com.example.blockbuster.Service.MovieDTO;
import com.example.blockbuster.Service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MovieController {

    private final MovieService movieService;

    /**
     * Constructor for MovieService
     * @param movieService
     */
    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    /**
     * Given a MovieDTO add a movie in the Movie repository
     * @param movieDTO a MovieDTO
     * @return the movie added
     */
    @PostMapping("/movie")
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movieDTO) {
        Movie movie = new Movie(movieDTO.getTitle(), movieDTO.isStandard(), movieDTO.isForChildren(), movieDTO.isNewReleased());
        if (movieDTO.isNewReleased() || movieDTO.isForChildren() || movieDTO.isStandard()) {
            List<Movie> listMovie = movieService.findAll();
            for (Movie elem : listMovie) {
                if (elem.getTitle().equals(movie.getTitle())) return ResponseEntity.badRequest().build();
            }
            movieService.create(movie);
            return ResponseEntity.ok(movie);
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * Given a movie id, return that movie found in the repo
     * @param id movie id
     * @return a movie
     */
    @GetMapping("/movie/{id}")
    public ResponseEntity<Movie> findMovieById(@PathVariable("id") String id) {
        var optionalMovie = movieService.findById(id);
        return optionalMovie.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Find all the movie in the repository and return them
     * @return a list of MovieDTO
     */
    @GetMapping("/movie")
    public ResponseEntity<List<MovieDTO>> findMovie() {
        var listMovie = movieService.findAll();
        List<MovieDTO> listMovieDTO = new ArrayList<>();
        movieService.findAll().forEach(elem -> listMovieDTO.add(new MovieDTO(elem.getId(), elem.getTitle(), elem.isStandard(), elem.isForChildren(), elem.isNewReleased())));
        return ResponseEntity.ok(listMovieDTO);
    }

}
