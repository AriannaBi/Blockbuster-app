package com.example.blockbuster;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {


    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    public User create(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findById(String id) {
        System.out.println(id);
        return userRepository.findById(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

}
