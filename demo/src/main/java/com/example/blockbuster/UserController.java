package com.example.blockbuster;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
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
        if (userDTO.getName() == null || Objects.equals(userDTO.getName(), "")) return ResponseEntity.badRequest().build();
        User user = new User(userDTO.getName());
        userService.create(user);
        return ResponseEntity.ok(user);
    }

    // userDTO has lostBeforeFidelity as field (and movie object)
    @PutMapping("/user/{id}")
    public ResponseEntity<User> modifylostBeforeFidelityUser(@PathVariable("id") String id, @RequestBody UserDTO userDTO) {
        var optionalUser = userService.findById(id);
        if (optionalUser.isPresent()){
            optionalUser.get().setlostBeforeFidelity();
            userService.create(optionalUser.get());
            return ResponseEntity.ok(optionalUser.get());
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> findUserById(@PathVariable("id") String id) {
        var optionalUser = userService.findById(id);
        return optionalUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user")
    public ResponseEntity<List<UserDTO>> findUser() {
        List<UserDTO> listUserDTO = new ArrayList<>();
        userService.findAll().forEach(elem -> listUserDTO.add(new UserDTO(elem.getId(), elem.getName(), elem.getlostBeforeFidelity(), elem.getRentals())));
        return ResponseEntity.ok(listUserDTO);
    }

    /**
     * Create a rent, add it to the rent repository and add it to the rent list of the user
     * Input validation: start, end date should be in order and at least one day of rental
     * @param id id user
     * @param userRentDTO body of the post request
     * @return the rent added
     */
    @PostMapping("/user/{id}/rent")
    public ResponseEntity<Rent> rentMovie(@PathVariable("id") String id, @RequestBody UserRentDTO userRentDTO) {
        if (!validDate(userRentDTO)) {
            return ResponseEntity.badRequest().build();
        }
        var optionalUser = userService.findById(id);
        var optionalMovie = movieService.findById(userRentDTO.getMovieId());
        var listMovieOfUser = rentService.findAllRentalsByUserId(id);


        if (optionalUser.isPresent() && optionalMovie.isPresent()){
            for (Rent elem:listMovieOfUser) {
                //if the movie we want to add is already in a rent, not accept
                if (Objects.equals(elem.getMovieTitle(), optionalMovie.get().getTitle())){
                    return ResponseEntity.badRequest().build();
                }
            }

            Rent newRent = new Rent(optionalMovie.get(), optionalUser.get(), userRentDTO.getStart(), userRentDTO.getEnd(), userRentDTO.getActualEnd());
            var a = rentService.create(newRent);
            optionalUser.get().rentMovie(newRent);
            userService.create(optionalUser.get());
            return ResponseEntity.ok(newRent);
        }
        return ResponseEntity.notFound().build();

    }

    /**
     * Check if the start date is before the ending date, and that the two dates cannot be equal (min rental 1 day)
     * @param userRentDTO userRent obj
     * @return true if date is valid, otherwise false
     */
    public boolean validDate(UserRentDTO userRentDTO) {
        return userRentDTO.getStart() != null && userRentDTO.getEnd() != null &&
                !userRentDTO.getStart().isAfter(userRentDTO.getEnd()) &&
                !userRentDTO.getStart().equals(userRentDTO.getEnd());
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

    /**
     * Return a movie at a certain date
     * Also check if the return date is later the end date don't accept it.
     * @param idUser user id
     * @param idRent rent id
     * @param userReturnDTO user rent obj
     * @return return rent object
     */
    @PutMapping("/user/{idUser}/rent/{idRent}")
    public ResponseEntity<Rent> returnMovie(@PathVariable("idUser") String idUser, @PathVariable("idRent") String idRent, @RequestBody UserReturnDTO userReturnDTO) {
        var optionalUser = userService.findById(idUser);
        var optionalRent = rentService.findById(idRent);
        if (optionalUser.isPresent() && optionalRent.isPresent()){
//            System.out.println(userReturnDTO.getActualEnd());
            if (userReturnDTO.getActualEnd() != null && userReturnDTO.getActualEnd().isBefore(optionalRent.get().getEnd())){
                return ResponseEntity.badRequest().build();
            }
            var movie = optionalRent.get().getMovie();
            optionalUser.get().returnMovie(movie, userReturnDTO.getActualEnd());
            System.out.println(optionalRent.get().getDeposit());
            userService.create(optionalUser.get());
//            rentService.create(optionalRent.get());
            return ResponseEntity.ok(optionalRent.get());
        }
        return ResponseEntity.notFound().build();
    }



}
