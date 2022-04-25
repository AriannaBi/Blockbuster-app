package com.example.blockbuster;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
//@EnableMongoRepositories
public class BlockbusterApplication  {
//
//	@Autowired
//	private UserRepository userRepository;


	public static void main(String[] args) {
		SpringApplication.run(BlockbusterApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		User user = new User("arianna");
//		userRepository.save(user);
//		userRepository.save(new User("CIAO"));
//	}
}
