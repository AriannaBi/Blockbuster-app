package com.example.blockbuster;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

// user has id string
//inject this with autowired

@Repository
public interface UserRepository extends MongoRepository<User, String> {


}
