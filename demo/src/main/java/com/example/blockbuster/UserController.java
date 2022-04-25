package com.example.blockbuster;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping()
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public void addUser(@RequestBody final String name) {
        User user = new User(name);
        userService.create(user);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> findUserById(@PathVariable("id") String id) {
        var optionalUser = userService.findById(id);
//        if (optionalUser.isPresent()){UserDTO user = new UserDTO(optionalUser.get().getName());return ResponseEntity.ok(user);}
//        return ResponseEntity.notFound().build();
        return optionalUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user")
    public ResponseEntity<List<User>> findUser() {
        var listUser = userService.findAll();
        return ResponseEntity.ok(listUser);
    }

}
