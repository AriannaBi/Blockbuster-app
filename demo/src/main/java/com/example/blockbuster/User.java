package com.example.blockbuster;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("user")
public class User {

    @Id
    private String id;
    private String name;
    private boolean lostMovie;
    private ArrayList<Rent> rentals;


    public User(String name) {
        this.name = name;
        lostMovie = false;
        rentals = new ArrayList<>();
    }

    public User() {}

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
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

    /**
     *  A user can rent a movie
//     * @param rent the rent with start date, end date and movie
     */
    public Rent rentMovie(Rent rent) {
        rentals.add(rent);
        return rent;
    }

    /**
     * Return the single rental
     * @param movie a movie
     * @return rent
     */
    public Rent getRental(Movie movie) {
        for (Rent rent: rentals) {
            if (Objects.equals(rent.getMovieTitle(), movie.getTitle())) {
                return rent;
            }
        }
        return null;
    }

    /**
     * Return all the rentals
     */
    public ArrayList<Rent> getRentals() {
//        return Collections.unmodifiableList(rentals);
        return rentals;
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
            if (Objects.equals(rent.getMovieTitle(), movie.getTitle())) {

                rent.setActualEnd(end);
                System.out.println("aaaa");
                additionalPrice += rent.computeAdditionalLatePrice();

            }
        }
        return additionalPrice;
    }

    /**
     * if the user has lost a movie, the rent is end, but he still needs to pay the rent's and deposit's
     * additional fees if it was returned late.
     * @param lostMovie if user has lost a movie or not
     */
    public void setLostMovie(boolean lostMovie) {
        this.lostMovie = lostMovie;
//        return returnMovie(movie, end);
    }


}
