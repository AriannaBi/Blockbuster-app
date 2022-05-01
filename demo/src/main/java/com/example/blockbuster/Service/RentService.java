package com.example.blockbuster.Service;

import com.example.blockbuster.*;
import com.example.blockbuster.Repositories.MovieRepository;
import com.example.blockbuster.Repositories.RentRepository;
import com.example.blockbuster.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RentService {

    private final RentRepository rentRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    /**
     * Constructor a RentService
     * @param rentRepository rent repository
     * @param movieRepository movie repository
     * @param userRepository user repository
     */
    @Autowired
    public RentService(RentRepository rentRepository, MovieRepository movieRepository, UserRepository userRepository) {
        this.rentRepository = rentRepository;
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
    }

    /**
     * Given a rent, dave it in the rent repo and return it
     * @param rent Rent
     * @return the rent created/saved in the repo
     */
    public Rent create(Rent rent) {
        return rentRepository.save(rent);
    }

    /**
     * Given an id of a rent, find it in the repo and return the rent object
     * @param id String id rent
     * @return rent object from repo
     */
    public Optional<Rent> findById(String id) {
        return rentRepository.findById(id);
    }

    /**
     * Find in the repository all the rents and return that list
     * @return a list of rents from repo
     */
    public List<Rent> findAll() {
        return rentRepository.findAll();
    }

    /**
     * Given a user id, return a list of all the rents of that user
     * @param id String user id
     * @return a list of rents of user
     */
    public List<Rent> findAllRentalsByUserId(String id) {
        return userRepository.findById(id).map(User::getRentals).orElseThrow();
    }
}
