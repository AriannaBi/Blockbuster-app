package com.example.blockbuster;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
//@RequestMapping()
public class UserController {

    private final UserService userService;
    private final RentService rentService;
    private final MovieService movieService;

    @Autowired
    public UserController(UserService userService, RentService rentService, MovieService movieService) {
        this.userService = userService;
        this.rentService = rentService;
        this.movieService = movieService;
    }

    @PostMapping("/user")
    public ResponseEntity<User> addUser(@RequestBody UserDTO userDTO) {
        User user = new User(userDTO.getName());
        userService.create(user);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> findUserById(@PathVariable("id") String id) {
        var optionalUser = userService.findById(id);
        return optionalUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user")
    public ResponseEntity<List<UserDTO>> findUser() {
        List<UserDTO> listUserDTO = new ArrayList<>();
        userService.findAll().forEach(elem -> listUserDTO.add(new UserDTO(elem.getId(), elem.getName(), elem.getLostMovie(), elem.getRentals())));
        return ResponseEntity.ok(listUserDTO);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<User> modifyUser(@PathVariable("id") String id, @RequestBody UserDTO userDTO) {
        var optionalUser = userService.findById(id);
        if (optionalUser.isPresent()){
            optionalUser.get().setName(userDTO.getName());
            userService.create(optionalUser.get());
            return ResponseEntity.ok(optionalUser.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

//    create a rent and add it to list of rent user
    @PostMapping("/user/{id}/rent")
    public ResponseEntity<Rent> rentMovie(@PathVariable("id") String id, @RequestBody UserRentDTO userRentDTO) {
        var optionalUser = userService.findById(id);
        var optionalMovie = movieService.findById(userRentDTO.getMovieId());
        if (optionalUser.isPresent() && optionalMovie.isPresent()){
            Rent newRent = new Rent(optionalMovie.get(), optionalUser.get(), userRentDTO.getStart(), userRentDTO.getEnd(), null);
            var a = rentService.create(newRent);
//            faccio o pptionalUser.get().rentMovie(a); oppure optionalUser.get().rentMovie(newRent);
            optionalUser.get().rentMovie(newRent);
            userService.create(optionalUser.get());
            return ResponseEntity.ok(newRent);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/user/{id}/rent")
    public ResponseEntity<List<Rent>> getRentMovie(@PathVariable("id") String id) {
        var optionalUser = userService.findById(id);
        if (optionalUser.isPresent()){
            var listRent = rentService.findAllRentalsByUserId(id);
            return ResponseEntity.ok(listRent);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/user/{idUser}/rent/{idRent}/return")
    public ResponseEntity<Rent> returnMovie(@PathVariable("idUser") String idUser, @PathVariable("idRent") String idRent, @RequestBody UserReturnDTO userReturnDTO) {
        var optionalUser = userService.findById(idUser);
        var optionalRent = rentService.findById(idRent);
        if (optionalUser.isPresent() && optionalRent.isPresent()){
            var movie = optionalRent.get().getMovie();
            optionalUser.get().returnMovie(movie, userReturnDTO.getEnd());
            userService.create(optionalUser.get());
//            now that i created a user, get list of rentals of user, get list of rentals in total and match which are equal and assign id.
            return ResponseEntity.ok(optionalRent.get());
        }
        return ResponseEntity.notFound().build();
    }



}
