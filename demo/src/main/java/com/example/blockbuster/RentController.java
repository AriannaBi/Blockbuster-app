package com.example.blockbuster;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class RentController {

    private final RentService rentService;

    /**
     * Constructor a RentController
     * @param rentService RentService
     */
    @Autowired
    public RentController(RentService rentService) {
        this.rentService = rentService;
    }

    /**
     * Given a rent id, return the rent object from the repository
     * @param id rent id
     * @return return a rent object
     */
    @GetMapping("/rent/{id}")
    public ResponseEntity<Rent> findRentById(@PathVariable("id") String id) {
        var optionalRent = rentService.findById(id);
        return optionalRent.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Find all the rent in the repository and return that list
     * @return a list of rent
     */
    @GetMapping("/rent")
    public ResponseEntity<List<Rent>> findAll() {
        var listRent = rentService.findAll();
        return ResponseEntity.ok(listRent);
    }

}
