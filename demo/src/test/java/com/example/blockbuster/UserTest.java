package com.example.blockbuster;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

//expected, actual
import java.time.LocalDate;

@SpringBootTest
@DisplayName("test User, Rent and Movie")
public class UserTest {
//    @Test
//    void contextLoads() {
//    }
    private User user1;
    private Movie movie1;
    private Movie movie2;
    private Movie movie3;
    private LocalDate today;
    private LocalDate todayPlus1;
    private LocalDate todayPlus2;
    private LocalDate todayPlus3;
    private LocalDate todayPlus4;
    private LocalDate todayPlus7;




    @BeforeEach
    public void DataSetup() {
        today =  LocalDate.now();
        todayPlus1 =  today.plusDays(1);
        todayPlus2 =  today.plusDays(2);
        todayPlus3 =  today.plusDays(3);
        todayPlus4 =  today.plusDays(4);
        todayPlus7 = today.plusDays(7);

//        Create user1, 2 rents and 2 movies
        user1 = new User("Arianna Bianchi");
        movie1 = new Movie("Men in Black", true, false, false);
        movie2 = new Movie("The Batman", false, false, true);
        movie3 = new Movie("Toy Story", true, false, false);
//        rent1 = new Rent(movie1, user1, today, todayPlus3); //3
//        rent2 = new Rent(movie2, user1, today, todayPlus4); //4
//        rent3 = new Rent(movie3, user1, today, todayPlus2); //2


    }

    @DisplayName("test name of user and movie")
    @Test
    void testNameUserMovie() {
        assertEquals(user1.getName(), "Arianna Bianchi", "Error in user name wrong");
        assertEquals(movie1.getTitle(), "Men in Black", "Error: movie title wrong");

    }

    @DisplayName("test standard movie, return in time, no lost the movie")
    @Test
    void testAddRentalToUser() {
        user1.rentMovie(movie1, today, todayPlus3); //3
        user1.rentMovie(movie2, today, todayPlus4); //4
        assertEquals(2, user1.getNumberOfRentals(), "Error: adding rent to a user wrong");
        float actualAdditionalPrice = user1.returnMovie(movie1, todayPlus3);
        assertEquals(0, actualAdditionalPrice, "Error in compute late price rent and deposit");


        Rent rent1 = user1.getRental(movie1); //3
        Rent rent2 = user1.getRental(movie2); //4
        assertEquals("Men in Black", rent1.getMovieTitle(), "Error Title rent  wrong");
        assertEquals("The Batman", rent2.getMovieTitle(), "Error Title rent wrong");
        assertEquals(12, rent1.getPrice(), "Error: price of total rent not correct");
        assertFalse(user1.getLostMovie(), "Error: actually a user has never lost a movie");


        user1.rentMovie(movie3, today, todayPlus2); //2
        float additionalPrice = user1.returnMovie(movie3, todayPlus2);
        assertEquals(0, additionalPrice, "Error in compute late price rent and deposit");
        Rent rent3 = user1.getRental(movie3); //2
        assertEquals(10, rent3.getPrice(), "Error: price of total rent not correct");



    }

    @DisplayName("test standard movie, return late, no lost the movie")
    @Test
    void testAddRentalToUserd() {
        user1.rentMovie(movie1, today, todayPlus3); //3
        user1.rentMovie(movie2, today, todayPlus4); //4
        float actualAdditionalPrice = user1.returnMovie(movie2, todayPlus7);
        Rent rent1 = user1.getRental(movie1); //3
        Rent rent2 = user1.getRental(movie2); //4

        float expectedAdditionalPrice = 3 * 2; // deposit to zero + 3 late days * 2
        assertEquals(expectedAdditionalPrice, actualAdditionalPrice, "Error in compute additional price for late return");
        assertEquals(0, rent2.getDeposit(), "Error: deposit is not zero even if rent return is late");
        assertEquals(34, rent2.getPrice(), "Error: price of total rent not correct");

    }

    @DisplayName("test standard movie, return in time, no lost the movie, third rent")
    @Test
    void testAddRentalToUserr() {
//        user1.rentMovie(rent1);
//        user1.rentMovie(rent2);







    }

}
