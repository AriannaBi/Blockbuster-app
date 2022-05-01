package com.example.blockbuster;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Document("rent")
public class Rent {

    @Id
    private String id;
    private  LocalDate start;
    private  LocalDate end;
    private  LocalDate actualEnd;
    private float price; //initial rent and final rent
    private float deposit; //deposit to return
    private Movie movie;


    /**
     * Empty constructor
     */
    public Rent() {}

    /**
     * Constructor for Rent
     * @param movie a movie
     * @param user a user
     * @param start starting date of rent
     * @param end ending date of rent
     * @param actualEnd actual date of return of the movie
     */
    public Rent(Movie movie, User user, LocalDate start, LocalDate end, LocalDate actualEnd) {
        this.start = start;
        this.end = end;
        this.movie = movie;
        this.price = computeInitialPriceRent(movie);
        if (hasNotToPayInitialDeposit(user, movie.isStandard())) {
            this.deposit = 0;
        } else {
            this.deposit = computeDeposit(movie);
        }
        this.actualEnd = actualEnd;
    }

    /**
     * Return the id of the rent
     * @return String id rent
     */
    public String getId() {
        return id;
    }

    /**
     * Return the date in which the movie returned
     * @return LocalDate of return
     */
    public LocalDate getActualEnd() {
        return actualEnd;
    }

    /**
     * Return the title of the movie
     * @return String title movie
     */
    public String getMovieTitle() {
        return movie.getTitle();
    }

    /**
     * Return the price paid of the rent
     * @return float price rent
     */
    public float getPrice() {
        return price;
    }

    /**
     * Return the movie to rent
     * @return Movie rented
     */
    public Movie getMovie() {
        return this.movie;
    }

    /**
     * Return the starting date of the rent
     * @return LocalDate starting date of rent
     */
    public LocalDate getStart() {
        return start;
    }

    /**
     * Return the planned ending date of the rent
     * @return LocalDate ending date of rent
     */
    public LocalDate getEnd() {
        return end;
    }

    /**
     * Return the deposit paid of the rent
     * @return float the deposit paid
     */
    public float getDeposit() {
        return deposit;
    }

    /**
     * Given a date, set the effective returning date
     * @param actualEnd LocalDate in which the rent was returned
     */
    public void setActualEnd(LocalDate actualEnd) {
        this.actualEnd = actualEnd;
    }

    /**
     * Decide if you have to give the deposit or not based on:
     * from the third rent you must not pay the deposit if you have never lost a movie and
     * your rent is on a standard movie.
     * If you rent 4 "children" movie, then you start not paying the rent from the first standard movie you rent
     * @return true if you have to pay the deposit, otherwise false
     */
    public boolean hasNotToPayInitialDeposit(User user, boolean isStandard) {
        var haslostBeforeFidelity = user.getlostBeforeFidelity();
        var numberOfRentals = user.getNumberOfRentals();
        if (haslostBeforeFidelity) return false;
        if (numberOfRentals == 2) if (isStandard) return true;
        if (numberOfRentals > 2) {
            if (isStandard) return true;
            List<Rent> rentsMoreThan2 = user.getRentals().subList(2, user.getRentals().size());
            for (Rent rent: rentsMoreThan2) {
                if (rent.getMovie().isStandard()) {
                    return true; //not pay the deposit because there is a standard movie
                }
            }
        }
        return false;
    }


    /**
     * Given a movie, return the price associated with the rent and the movie.
     * Is 5 for standard, 10 for children and 7 for new released.
     * Children movie are rented by weeks, so the price is for weeks
     * @param movie Movie rented
     * @return the rent price (without deposit)
     */
    private float computeInitialPriceRent(Movie movie) {
        long daysInBetween = daysInBetween(start, end);
        if (movie.isStandard()) {
            return 5 * daysInBetween;
        } else if (movie.isForChildren()) {
            return 10 * howManyWeeks(daysInBetween);
        } else {
            return 7 * daysInBetween;
        }
    }

    /**
     * Given a number of days, return how many weeks they are:
     * 4 gg -> 1 week
     * 9 gg -> 2 weeks
     * 16 gg -> 3 weeks
     * @param days gays
     * @return how many weeks for the days
     */
    public int howManyWeeks(long days) {
        int counter = 0;
        for (int i = 0; i < days; i += 7) {
            counter += 1;
            if (i <= days && days <= i + 7) {
                return counter;
            }
        }
        return -1; //error
    }


    /**
     * Given a movie, return the deposit that has to be paid.
     * Is 3 for standard, 1 for children and 4 for new released movie
     * @param movie Movie rented
     * @return float deposit
     */
    public float computeDeposit(Movie movie) {
        if (movie.isStandard()) {
            return 3;
        } else if (movie.isForChildren()) {
            return 1;
        } else {
            return 4;
        }
    }

    /**
     * Given a starting and ending date, return how many days in between
     * @param start LocalDate start
     * @param end LocalDate end
     * @return how mnay days in between
     */
    public long daysInBetween(LocalDate start, LocalDate end) {
        return ChronoUnit.DAYS.between(start, end);
    }

    /**
     * If the return is late, compute the number of days late multiplied by 2 and add them to the rent price.
     * Then the user lost the deposit, which is set to zero.
     * @return additional price to pay (not total price)
     */
    public float computeAdditionalLatePrice() {
        long lateDays = daysInBetween(end, actualEnd);
        float lateRentPrice = 2 * lateDays;
        this.price += lateRentPrice;
        if (lateDays > 0) this.deposit = 0;//lose the deposit if the rent is late
        return lateRentPrice;
    }
}
