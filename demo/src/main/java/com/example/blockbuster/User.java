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

    public int numberOfRentals() {
        return rentals.size();
    }

    /**
     * Return if the user has ever lost a movie.
     * @return true if user had lost a movie, false if user had never lost a movie
     */
    public boolean hasLostMovie() {
        return true;
    }

    public void setLostMovie() {
        lostMovie = true;
    }

    /**
     * A user can rent a movie
     * @param movie is the object movie
     * @return the object rent
     */
    public Rent rentMovie(Movie movie, LocalDate start) {
        boolean hasDeposit = depositYerOrNo(movie);
        Rent rent = new Rent(movie, LocalDate.now(), hasDeposit);
        rentals.add(rent);
        return rent;
    }

    /**
     * Return to the blockbuster the movie and end the rent.
     * Find the movie among all the rentals, compute the fee to pay if the movie was turned late,
     * and decide to give back the deposit or not
     * @param movie
     * @param end
     * @return
     */
    public float returnMovie(Movie movie, LocalDate end) {
//        find the movie 
//        compute the rimanent rent for being late and the deposit to return or not return
//        remove movie from list rent
        for (Rent rent: rentals) {
            if (Objects.equals(rent.getTitleMovie(), movie.getTitle())) {
                rent.computeAdditionalLatePriceRent(end);
                rent.computeAdditionalLateDeposit(end);
            }
        }

        return 0;

    }

    private boolean depositYerOrNo(Movie movie) {
        return movie.isStandard() && !lostMovie && numberOfRentals() >= 3;
    }


}
