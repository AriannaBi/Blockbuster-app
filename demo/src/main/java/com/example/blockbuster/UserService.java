package com.example.blockbuster;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {


    private final UserRepository userRepository;

    /**
     * Constructor of UserService
     * @param userRepository user repository
     */
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    /**
     * Given a user, create/save it in the user repository
     * @param user a user
     * @return return the user object from the repository
     */
    public User create(User user) {
        return userRepository.save(user);
    }

    /**
     * Given a user id, find the user in the repo and return it
     * @param id string user id
     * @return optional of the user object
     */
    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    /**
     * Find all the user in the user repo and return a list of them
     * @return a list of all the users from the repo
     */
    public List<User> findAll() {
        return userRepository.findAll();
    }

}
