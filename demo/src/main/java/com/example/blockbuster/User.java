package com.example.blockbuster;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("user")
public class User {

    @Id
    private String id;
    private String name;
    //lostBeforeFidelity true before the third standard rent, and between 1-2 rentals
    private boolean lostBeforeFidelity;
    private ArrayList<Rent> rentals;


    /**
     * Constructor for User
     * @param name String name of user
     */
    public User(String name) {
        this.name = name;
        lostBeforeFidelity = false;
        rentals = new ArrayList<>();
    }

    /**
     * Empty constructor
     */
    public User() {}

    /**
     * Return id user
     * @return String id
     */
    public String getId() {
        return id;
    }

    /**
     * Return name user
     * @return String name user
     */
    public String getName() {
        return name;
    }

    /**
     * Return number of rentals of the user
     * @return int number rentals
     */
    public int getNumberOfRentals() {
        return rentals.size();
    }

    /**
     * Return if the user has lost the fidelity
     * @return boolean lostBeforeFidelity
     */
    public boolean getlostBeforeFidelity() {
        return lostBeforeFidelity;
    }

    /**
     *  A user rent a movie
     * @param rent Rent object
     */
    public void rentMovie(Rent rent) {
        rentals.add(rent);
    }


    /**
     * Return all the rentals of a user
     * @return array of rentals
     */
    public ArrayList<Rent> getRentals() {
        return rentals;
    }

    /**
     * Return the movie.
     * Find the movie among all the rentals, compute the additional fee to pay if the movie was turned late,
     * and decide to give back the deposit or not.
     * @param movie a movie
     * @param end end date of rent
     * @return price to pay as additional fees after rent (for late return)
     */
    public float returnMovie(Movie movie, LocalDate end) {
        for (Rent rent: rentals) {
            if (Objects.equals(rent.getMovieTitle(), movie.getTitle())) {
                rent.setActualEnd(end);
                return rent.computeAdditionalLatePrice();
            }
        }
        return 0;
    }

    /**
     * if the user has lost a movie, the rent is end, but he still needs to pay the rent's and deposit's
     * additional fees if it was returned late.
     * Example: if you call the function in first 2 rents, it has lost the fidelity.
     * If you call the function after the 2 rents, and it's not standard, it has lost the fidelity.
     * If you call the function on the third rent standard, has not lost fidelity
     */
    public void setlostBeforeFidelity() {
        if (rentals.size() <= 2) {
            this.lostBeforeFidelity = true;
        } else {
            List<Rent> rentsMoreThan2 = rentals.subList(2, rentals.size()); //
            for (Rent rent : rentsMoreThan2) {
                if (!rent.getMovie().isStandard()) {
                    this.lostBeforeFidelity = true;
                } else {
                    break;
                }

            }
        }
    }


}
