package com.example.blockbuster;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class User {

    private final String name;
    private boolean lostMovie;
    private ArrayList<Rent> rentals;


    public User(String name) {
        this.name = name;
        lostMovie = false;
        rentals = new ArrayList<>();
    }

    public String getName() {
        return name;
    }


    public int getNumberOfRentals() {
        return rentals.size();
    }


    public boolean getLostMovie() {
        return lostMovie;
    }

//    /**
//     *  A user can rent a movie
//     * @param rent the rent with start date, end date and movie
//     */
//    public void rentMovie(Rent rent) {
//        rentals.add(rent);
//    }

    /**
     *  A user can rent a movie
//     * @param rent the rent with start date, end date and movie
     */
    public void rentMovie(Movie movie, LocalDate start, LocalDate end) {
        Rent rent = new Rent(movie, this, start, end);
        rentals.add(rent);
    }

    public Rent getRent(Movie movie) {
        for (Rent rent: rentals) {
            if (Objects.equals(rent.getTitleMovie(), movie.getTitle())) {
                return rent;
            }
        }
        return null;
    }

    /**
     * Return to the blockbuster the movie and end the rent.
     * Find the movie among all the rentals, compute the fee to pay if the movie was turned late,
     * and decide to give back the deposit or not.
     * @param movie a movie
     * @param end end date of rent
     * @return price to pay as additional fees after rent (for late return)
     */
    public float returnMovie(Movie movie, LocalDate end) {
        float additionalPrice = 0;
        for (Rent rent: rentals) {
            if (Objects.equals(rent.getTitleMovie(), movie.getTitle())) {
                additionalPrice += rent.computeAdditionalLatePrice(end);
            }
        }
        return additionalPrice;
    }

    /**
     * if the user has lost a movie, the rent is end, but he still needs to pay the rent's and deposit's
     * additional fees if it was returned late.
     * @param movie movie
     * @param end end date of rental
     * @return the price to pay
     */
    public float setLostMovie(Movie movie, LocalDate end) {
        lostMovie = true;
        return returnMovie(movie, end);
    }




}