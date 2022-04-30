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
    //lost before the third standard rent, and between 1-2 rentals
    // name: lostBeforeFidelity
    private boolean lostBeforeFidelity;
    private ArrayList<Rent> rentals;


    public User(String name) {
        this.name = name;
        lostBeforeFidelity = false;
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

    public boolean getlostBeforeFidelity() {
        return lostBeforeFidelity;
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
     * I can set the lost movie only in the first 2 elements
     */
    //when it get lost
    //if you call it in the first 2 rents, it has lostfidelity
    //if you call it after the 2 rents and it's not standard, it has lost fidelity.
    //if you call it on the third rental standard, it should be false
    public void setlostBeforeFidelity() {
        //for the first 2 movies, you can always set them to lost.
        // from the third, if it's standard you can't set it as lost because you don't care
        // from the third, if it's not standard you can set it lost
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

//        this.lostBeforeFidelity = lostBeforeFidelity;
//        return returnMovie(movie, end);
    }


}
