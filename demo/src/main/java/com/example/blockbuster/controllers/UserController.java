package com.example.blockbuster.controllers;

import com.example.blockbuster.*;
import com.example.blockbuster.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
public class UserController {

    private final UserService userService;
    private final RentService rentService;
    private final MovieService movieService;


    /**
     * Constructor for UserController
     * @param userService a user service
     * @param rentService a rent service
     * @param movieService ma movie service
     */
    @Autowired
    public UserController(UserService userService, RentService rentService, MovieService movieService) {
        this.userService = userService;
        this.rentService = rentService;
        this.movieService = movieService;
    }

    /**
     * Given a UserDTO, add the user in the user repository and return it
     * @param userDTO a UserDTO
     * @return the user object stored in the repo
     */
    @PostMapping("/user")
    public ResponseEntity<User> addUser(@RequestBody UserDTO userDTO) {
        if (userDTO.getName() == null || Objects.equals(userDTO.getName(), "")) return ResponseEntity.badRequest().build();
        User user = new User(userDTO.getName());
        userService.create(user);
        return ResponseEntity.ok(user);
    }

    /**
     * Given a user id and a userDTO, update the user by modifying the field lostBeforeFidelityUser to true
     * @param id user id
     * @param userDTO userDTO
     * @return the user updated
     */
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

    /**
     * Given a user id find it in the repo and return it
     * @param id user id
     * @return the user from the repo
     */
    @GetMapping("/user/{id}")
    public ResponseEntity<User> findUserById(@PathVariable("id") String id) {
        var optionalUser = userService.findById(id);
        return optionalUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Find all the users from the user repo and return that list
     * @return a list of UserDTO
     */
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

    /**
     * Given a user string, return a list of all his rents
     * @param id user id
     * @return a list of rent from user repo
     */
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
     * Given a user id, a rent id and a userRentDTO, update the rent because the user returned the movie. Hence, set the
     * field actualDate to the return date
     * Input validation:  check if the return date is later the end date don't accept it.
     * @param idUser user id
     * @param idRent rent id
     * @param userReturnDTO user rent object
     * @return return rent object
     */
    @PutMapping("/user/{idUser}/rent/{idRent}")
    public ResponseEntity<Rent> returnMovie(@PathVariable("idUser") String idUser, @PathVariable("idRent") String idRent, @RequestBody UserReturnDTO userReturnDTO) {
        var optionalUser = userService.findById(idUser);
        var optionalRent = rentService.findById(idRent);
        if (optionalUser.isPresent() && optionalRent.isPresent()){
            if (userReturnDTO.getActualEnd() != null && userReturnDTO.getActualEnd().isBefore(optionalRent.get().getEnd())){
                return ResponseEntity.badRequest().build();
            }
            var movie = optionalRent.get().getMovie();
            optionalUser.get().returnMovie(movie, userReturnDTO.getActualEnd());
            userService.create(optionalUser.get());
            return ResponseEntity.ok(optionalRent.get());
        }
        return ResponseEntity.notFound().build();
    }



}
