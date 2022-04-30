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
        movie4 = new Movie("Avatar", true, false, false);

        rent1 = new Rent(movie1, user1, today, todayPlus3, null); //3
        rent2 = new Rent(movie2, user1, today, todayPlus4, null); //4
        rent3 = new Rent(movie3, user1, today, todayPlus2, null); //2
        rent4 = new Rent(movie4, user1, today, todayPlus4, null); //4


    }

    @DisplayName("test name of user and movie")
    @Test
    void testNameUserMovie() {
        assertEquals(user1.getName(), "Arianna Bianchi", "Error in user name wrong");
        assertEquals(movie1.getTitle(), "Men in Black", "Error: movie title wrong");

    }

    @DisplayName("test standard and new released movie, return in time & late, no/yes lost the movie, no deposit after lost fidelity")
    @Test
    void testAddRentalToUser() {
        user1.rentMovie(rent1); //3 //standard
        user1.rentMovie(rent2); //4 //new released
        assertEquals(2, user1.getNumberOfRentals(), "Error: adding rent to a user is wrong");
        float additionalPrice = user1.returnMovie(movie1, todayPlus3);
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
//
//
        user1.rentMovie(rent3); //2
        // return movie in time
        additionalPrice = user1.returnMovie(movie3, todayPlus2);
        assertEquals(0, additionalPrice, "Error in compute late price rent and deposit");
//
//
        // return movie late of 2 days (2*2)
        additionalPrice = user1.returnMovie(movie2, todayPlus6);
        assertEquals(4.0, additionalPrice, "Error in compute late price rent");
        assertEquals(32, rent2.getPrice(), "Error: amount of price rent not correct");
        assertEquals(0, rent2.getDeposit(), "Error: amount of deposit not correct");

        //il terzo noleggio non ho il deposito e d'ora in avanti non devo piu pagarlo.
        //il quarto noleggio l'ho perso
        //il quinto non devo pagare la tassa di deposito anche se l'ho perso, perché era dal terzo noleggio
        //nel frontend controllo, se il rent é >= 3 e

        user1.rentMovie(rent4); //4 //standard
        // 4 days * 5 standard
        user1.setlostBeforeFidelity();
        assertFalse(user1.getlostBeforeFidelity(), "Error: user has actually lost the fidelity");
        assertEquals(20, rent4.getPrice(), "Error: amount of price rent not correct");
        //even if he lost a movie, and so he lost the fidelity, the deposit is still there
        assertEquals(3, rent4.getDeposit(), "Error: amount of deposit not correct");

        // 3 days late * 2 ch
        // price 4 days * standard 5
        additionalPrice = user1.returnMovie(movie4, todayPlus7);
        assertEquals(6.0, additionalPrice, "Error in compute late price rent");
        // 6 for late and 20 for rent
        assertEquals(26, rent4.getPrice(), "Error: amount of total rent not correct");
        //he lost a movie before his fidelity so he lost the deposit
        assertEquals(0, rent4.getDeposit(), "Error: amount of deposit not correct");

    }

    @DisplayName("test standard movie, return late, NO lost the movie")
    @Test
    void testAddRentalToUserd() {
        user1.rentMovie(rent1); //3 //standard
        user1.rentMovie(rent2); //4 //new released
        assertEquals(2, user1.getNumberOfRentals(), "Error: adding rent to a user is wrong");
        float additionalPrice = user1.returnMovie(movie1, todayPlus3);
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
//
//
        user1.rentMovie(rent3); //2
        // return movie in time
        additionalPrice = user1.returnMovie(movie3, todayPlus2);
        assertEquals(0, additionalPrice, "Error in compute late price rent and deposit");
//
//
        // return movie late of 2 days (2*2)
        additionalPrice = user1.returnMovie(movie2, todayPlus6);
        assertEquals(4.0, additionalPrice, "Error in compute late price rent");
        assertEquals(32, rent2.getPrice(), "Error: amount of price rent not correct");
        assertEquals(0, rent2.getDeposit(), "Error: amount of deposit not correct");

        //il terzo noleggio non ho il deposito e d'ora in avanti non devo piu pagarlo.
        //il quarto noleggio l'ho perso
        //il quinto non devo pagare la tassa di deposito anche se l'ho perso, perché era dal terzo noleggio
        //nel frontend controllo, se il rent é >= 3 e

        user1.rentMovie(rent4); //4 //standard
        // 4 days * 5 standard
        user1.setlostBeforeFidelity();
        assertFalse(user1.getlostBeforeFidelity(), "Error in lostBeforeFidelity");
        assertEquals(20, rent4.getPrice(), "Error: amount of price rent not correct");
        //even if he lost a movie, and so he lost the fidelity, the deposit is still there
        assertEquals(3, rent4.getDeposit(), "Error: amount of deposit not correct");

        // 3 days late * 2 ch
        // price 4 days * standard 5
        additionalPrice = user1.returnMovie(movie4, todayPlus7);
        assertEquals(6.0, additionalPrice, "Error in compute late price rent");
        // 6 for late and 20 for rent
        assertEquals(26, rent4.getPrice(), "Error: amount of total rent not correct");
        //he lost a movie before his fidelity so he lost the deposit
        assertEquals(0, rent4.getDeposit(), "Error: amount of deposit not correct");


    }

    @DisplayName("test standard movie, return in time, no lost the movie, third rent")
    @Test
    void testAddRentalToUserr() {
//        user1.rentMovie(rent1);
//        user1.rentMovie(rent2);







    }

}
