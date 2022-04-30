package com.example.blockbuster;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
public class BlockbusterApplication  {
	public static void main(String[] args) {
		SpringApplication.run(BlockbusterApplication.class, args);
	}
}
