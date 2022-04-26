package com.example.blockbuster;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RentService {

    private final RentRepository rentRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    @Autowired
    public RentService(RentRepository rentRepository, MovieRepository movieRepository, UserRepository userRepository) {
        this.rentRepository = rentRepository;
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
    }

    public Rent create(Rent rent) {
        return rentRepository.save(rent);
    }

    public Optional<Rent> findById(String id) {
        return rentRepository.findById(id);
    }

    public List<Rent> findAll() {
        return rentRepository.findAll();
    }

//    public List<Rent> getAllRentalsByUserId(String id) {
//        return userRepository.findById(id);
//        .map(User::getNumberOfRentals).orElseThrow();
//    }



}
