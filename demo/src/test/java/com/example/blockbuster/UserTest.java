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
    private User user1;
    private Movie movie1;
    private Movie movie2;
    private Movie movie3;
    private Movie movie4;

    private LocalDate today;
    private LocalDate todayPlus1;
    private LocalDate todayPlus2;
    private LocalDate todayPlus3;
    private LocalDate todayPlus4;
    private LocalDate todayPlus6;
    private LocalDate todayPlus7;

    private Rent rent1;
    private Rent rent2;
    private Rent rent3;
    private Rent rent4;




    @BeforeEach
    public void DataSetup() {
        today =  LocalDate.now();
        todayPlus1 =  today.plusDays(1);
        todayPlus2 =  today.plusDays(2);
        todayPlus3 =  today.plusDays(3);
        todayPlus4 =  today.plusDays(4);
        todayPlus6 =  today.plusDays(6);
        todayPlus7 = today.plusDays(7);

//        Create user1, 2 rents and 2 movies
        user1 = new User("Arianna Bianchi");
        movie1 = new Movie("Men in Black", true, false, false);
        movie2 = new Movie("The Batman", false, false, true);
        movie3 = new Movie("Toy Story", true, false, false);
        movie4 = new Movie("Avatar", false, false, true);

        //non posso creare i rent qui, ma devo crearli uno dopo l'altro perché l'user ha bisogno di una lista di rentals
    }

    @DisplayName("test name of user and movie")
    @Test
    void testNameUserMovie() {
        assertEquals(user1.getName(), "Arianna Bianchi", "Error in user name wrong");
        assertEquals(movie1.getTitle(), "Men in Black", "Error: movie title wrong");

    }

    @DisplayName("test standard and new released movie, return in time & late, no/yes lost the movie, no deposit after lose fidelity")
    @Test
    void testAddRentalToUser() {
        rent1 = new Rent(movie1, user1, today, todayPlus3, null); //3
        user1.rentMovie(rent1); //3 //standard
        rent2 = new Rent(movie2, user1, today, todayPlus4, null); //4
        user1.rentMovie(rent2); //4 //new released

        assertEquals(2, user1.getNumberOfRentals(), "Error: adding rent to a user is wrong");
        float additionalPrice = user1.returnMovie(movie1, todayPlus3); //in time
        assertEquals(0, additionalPrice, "Error in compute late price rent and deposit");
        assertEquals("Men in Black", rent1.getMovieTitle(), "Error Title rent  wrong");
        assertEquals("The Batman", rent2.getMovieTitle(), "Error Title rent wrong");


//      rent1: 3 days * 5 standard + 3 deposit
        assertEquals(15, rent1.getPrice(), "Error: price of total rent not correct");
        assertEquals(3, rent1.getDeposit(), "Error: price of total rent not correct");
//      rent2: 4 days * 7 new + 4 deposit
        assertEquals(28, rent2.getPrice(), "Error: price of total rent not correct");
        assertEquals(4, rent2.getDeposit(), "Error: price of total rent not correct");
        assertFalse(user1.getlostBeforeFidelity(), "Error: actually a user has never lost a movie");


        rent3 = new Rent(movie3, user1, today, todayPlus2, null); //2
        user1.rentMovie(rent3); //2
//        //2 days * standard 5
        assertEquals(10, rent3.getPrice(), "Error: price of total rent not correct");
//        // deposit is zero because the third movie was standard: GET THE FIDELITY
        assertEquals(0, rent3.getDeposit(), "Error: price of deposit not correct");
        additionalPrice = user1.returnMovie(movie3, todayPlus3); //return in time
        assertEquals(2, additionalPrice, "Error in compute late price rent and deposit");
        assertEquals(0, rent3.getDeposit(), "Error: price of total rent not correct");

        // return movie late of 2 days (2*2)
        additionalPrice = user1.returnMovie(movie2, todayPlus6);
        assertEquals(4.0, additionalPrice, "Error in compute late price rent");
        assertEquals(32, rent2.getPrice(), "Error: amount of price rent not correct");
        assertEquals(0, rent2.getDeposit(), "Error: amount of deposit not correct");

        rent4 = new Rent(movie4, user1, today, todayPlus4, null); //4
        user1.rentMovie(rent4); //4 standard
        assertEquals(28, rent4.getPrice(), "Error: price of total rent not correct");// 4 days * 7 new released
        // deposit is zero because the third movie was standard: get the fidelity
        assertEquals(0, rent4.getDeposit(), "Error: price of total rent not correct");
        user1.setlostBeforeFidelity(); // is false because now even if he lost movies, he still doesn't pay the deposit
        assertFalse(user1.getlostBeforeFidelity(), "Error: user has actually lost the fidelity");


        // 3 days late * 2 ch
        additionalPrice = user1.returnMovie(movie4, todayPlus7);
        assertEquals(6.0, additionalPrice, "Error in compute late price rent");
        // 6 for late and 28 for rent
        assertEquals(34, rent4.getPrice(), "Error: amount of total rent not correct");
        assertEquals(0, rent4.getDeposit(), "Error: amount of deposit not correct");

    }

    @DisplayName("test deposit is zero when movie standard after third rent")
    @Test
    void testNotGetTheFidelity() {
        rent1 = new Rent(movie1, user1, today, todayPlus3, null); //3
        user1.rentMovie(rent1); //3 //standard

        rent2 = new Rent(movie2, user1, today, todayPlus4, null); //4
        user1.rentMovie(rent2); //4 //new released

        movie3 = new Movie("Toy Story", false, false, true);
        rent3 = new Rent(movie3, user1, today, todayPlus2, null); //2
        user1.rentMovie(rent3); //2 //NOT GET THE FIDELITY BECAUSE THE THIRD MOVIE IS NEW RELEASED
        assertEquals(14, rent3.getPrice(), "Error: price of total rent not correct");// 2 days * 7 ch
        assertEquals(4, rent3.getDeposit(), "Error: price of total rent not correct");

        float additionalPrice = user1.returnMovie(movie3, todayPlus2); //return in time
        assertEquals(0, additionalPrice, "Error in compute late price rent and deposit");
        additionalPrice = user1.returnMovie(movie2, todayPlus6); //late 2 days * 2 ch
        assertEquals(4, additionalPrice, "Error in compute late price rent and deposit");

        //GET FIDELITY ON THE FOURTH RENT, IT'S STANDARD so don't pay the deposit
        movie4 = new Movie("Avatar", true, false, false);
        rent4 = new Rent(movie4, user1, today, todayPlus4, null); //4
        user1.rentMovie(rent4); //4 //standard
        // 4 days * 5 new released
        assertEquals(20, rent4.getPrice(), "Error: price of total rent not correct");
        // deposit is zero because the fourth movie was standard: get the fidelity
        assertEquals(0, rent4.getDeposit(), "Error: price of total rent not correct");

    }

    @DisplayName("test the user that lose the fidelity")
    @Test
    void testLoseFidelity() {
        rent1 = new Rent(movie1, user1, today, todayPlus3, null); //3
        user1.rentMovie(rent1); //3 //standard

        user1.setlostBeforeFidelity(); //the user has lost the first movie, and so the fidelity
        rent2 = new Rent(movie2, user1, today, todayPlus4, null); //4
        user1.rentMovie(rent2); //4  days * 7new released
        assertEquals(28, rent2.getPrice(), "Error: price of total rent not correct");// 2 days * 7 ch
        assertEquals(4, rent2.getDeposit(), "Error: price of total rent not correct");


        movie3 = new Movie("Toy Story", true, false, false);
        rent3 = new Rent(movie3, user1, today, todayPlus2, null); //2
        user1.rentMovie(rent3); //2 days * 5 ch
        assertEquals(10, rent3.getPrice(), "Error: price of total rent not correct");// 2 days * 7 ch
        assertEquals(3, rent3.getDeposit(), "Error: price of total rent not correct");
    }

    @DisplayName("test children movie price and deposit")
    @Test
    void testAddRentalChildrenMovie() {
        movie1 = new Movie("Men in Black", false, true, false);
        movie2 = new Movie("The Batman", false, true, false);


        rent1 = new Rent(movie1, user1, today, todayPlus1, null); //3 day2 = 1 week
        user1.rentMovie(rent1); //1 week * 1 ch
        assertEquals(10, rent1.getPrice(), "Error: price of total rent not correct");
        assertEquals(1, rent1.getDeposit(), "Error: price of total rent not correct");

        LocalDate todayPlus15 = today.plusDays(15);
        rent2 = new Rent(movie2, user1, today, todayPlus15, null); //15 days = 3 weeks
        user1.rentMovie(rent2); //3 weeks * 1 ch
        assertEquals(30, rent2.getPrice(), "Error: price of total rent not correct");
        assertEquals(1, rent2.getDeposit(), "Error: price of total rent not correct");

        LocalDate todayPlus21 = today.plusDays(21);
        rent2 = new Rent(movie2, user1, today, todayPlus15, null); //15 days = 3 weeks
        user1.rentMovie(rent2); //3 weeks * 1 ch
        assertEquals(30, rent2.getPrice(), "Error: price of total rent not correct");
        assertEquals(1, rent2.getDeposit(), "Error: price of total rent not correct");
    }


}
