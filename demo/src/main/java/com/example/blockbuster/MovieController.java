package com.example.blockbuster;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MovieController {

    //la classe é cosí semplice che in realta non mi serve un DTO ma posso usare direttamente la classe user

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

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

    @GetMapping("/movie/{id}")
    public ResponseEntity<Movie> findMovieById(@PathVariable("id") String id) {
        var optionalMovie = movieService.findById(id);
        return optionalMovie.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/movie")
    public ResponseEntity<List<MovieDTO>> findMovie() {
        var listMovie = movieService.findAll();
        List<MovieDTO> listMovieDTO = new ArrayList<>();
        movieService.findAll().forEach(elem -> listMovieDTO.add(new MovieDTO(elem.getId(), elem.getTitle(), elem.isStandard(), elem.isForChildren(), elem.isNewReleased())));
        return ResponseEntity.ok(listMovieDTO);
    }

}
