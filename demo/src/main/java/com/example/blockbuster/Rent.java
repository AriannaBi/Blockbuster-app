package com.example.blockbuster;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Rent {

    private final LocalDate start;
    private float price; //initial rent and final rent
    private float deposit; //deposit to return
    private Movie movie;


    public Rent(Movie movie, LocalDate start, boolean hasDeposit) {
        this.movie = movie;
        this.start = start;
        this.price = getPriceRentBasedOnMovie(movie);
        if (hasDeposit) {
            deposit = 0;
        } else {
            computeDeposit(movie);
        }
    }

    public float getPriceRentBasedOnMovie(Movie movie) {
        if (movie.isStandard()) {
            this.deposit = 5;
        } else if (movie.isForChildren()) {
            this.deposit = 10;
        } else if (movie.isNewReleased()) {
            this.deposit = 7;
        }
        return this.deposit;
    }

    public String getTitleMovie() {
        return movie.getTitle();
    }

    public void computeDeposit(Movie movie) {
        if (movie.isStandard()) {
            this.deposit = 3;
        } else if (movie.isForChildren()) {
            this.deposit = 1;
        } else if (movie.isNewReleased()) {
            this.deposit = 4;
        }
    }

    public long lateDays(LocalDate end) {
        return ChronoUnit.DAYS.between(start, end);
    }

    public float computeAdditionalLatePriceRent(LocalDate end) {
        long lateDays = lateDays(end);
        float lateRentPrice = 2 * lateDays;
        this.price += lateRentPrice;
        deposit = 0; //lose the deposit if the rent is late
        return lateRentPrice;
    }

    public float computeAdditionalLateDeposit(LocalDate end) {
        long lateDays = lateDays(end);
        if (lateDays > 0) this.deposit = 0;
        return deposit;
    }
}
