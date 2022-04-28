package com.example.blockbuster;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class RentController {

    //la classe é cosí semplice che in realta non mi serve un DTO ma posso usare direttamente la classe user

    private final RentService rentService;

    @Autowired
    public RentController(RentService rentService) {
        this.rentService = rentService;
    }

//    @PostMapping("/rent/{id}")
//    public ResponseEntity<Movie> addRent(@PathVariable("id") String id, @RequestBody Rent rentDTO) {
//        Optional<User> optionalUser = userService.findById(id);
//        if (optionalUser.isPresent()) {
//            Rent rent = new Rent(rentDTO.getMovie(), optionalUser.get(), rentDTO.getStart(), rentDTO.getEnd());

//
//
//        }
//        return ResponseEntity.notFound().build();
//        List<Movie> listMovie = movieService.findAll();
//        for (Movie elem : listMovie) {
//            if (elem.equals(movie)) return ResponseEntity.badRequest().build();
//        }
//        movieService.create(movie);
//        return ResponseEntity.ok(movie);
//    }

    @GetMapping("/rent/{id}")
    public ResponseEntity<Rent> findRentById(@PathVariable("id") String id) {
        var optionalRent = rentService.findById(id);
        return optionalRent.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/rent")
    public ResponseEntity<List<Rent>> findAll() {
        var listRent = rentService.findAll();
        return ResponseEntity.ok(listRent);
    }

}
