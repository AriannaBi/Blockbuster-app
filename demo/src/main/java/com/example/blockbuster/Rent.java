package com.example.blockbuster;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.IOException;
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
//    private final float pricePerDay;
    // if deposit is > 0 is a debit, if deposit is < 0 is a credit
    private float deposit; //deposit to return
    private  Movie movie;


    public Rent() {}
    public Rent(Movie movie, User user, LocalDate start, LocalDate end, LocalDate actualEnd) {
        this.start = start;
        this.end = end;
        this.movie = movie;
        this.price = getPriceRentBasedOnMovieAndTime(movie);
//        System.out.println(price + " Price");
//        System.out.println(movie.getTitle());
        if (hasNotToPayInitialDeposit(user, movie.isStandard(), user.getlostBeforeFidelity(), user.getNumberOfRentals())) {
            this.deposit = 0;
        } else {
            this.deposit = computeDeposit(movie);
        }
//        System.out.println(deposit + " deposit");
        this.actualEnd = actualEnd;
    }

    public String getId() {
        return id;
    }
    public LocalDate getActualEnd() {
        return actualEnd;
    }
    public String getMovieTitle() {
        return movie.getTitle();
    }
    public float getPrice() {
        return price;
    }
    public Movie getMovie() {
        return this.movie;
    }
    public LocalDate getStart() {
        return start;
    }
    public LocalDate getEnd() {
        return end;
    }
    public float getDeposit() {
        return deposit;
    }
    public void setActualEnd(LocalDate actualEnd) {
        this.actualEnd = actualEnd;
    }

    /**
     * Decide if you have to give the deposit or not based on:
     * from the third rent you must not pay the deposit if you have never lost a movie and
     * your rent is on a standard movie.
//     * @param movie a movie with a title and a type(standard, for children, latest released)
     * @return true if you have to pay the deposit, otherwise false
     */
    public boolean hasNotToPayInitialDeposit(User user, boolean isStandard, boolean haslostBeforeFidelity, int numberOfRentals) {
//        System.out.println("---");
//        System.out.println(haslostBeforeFidelity);
//        System.out.println(numberOfRentals);

        if (haslostBeforeFidelity) return false;
        if (numberOfRentals == 2) if (isStandard) return true;
        if (numberOfRentals > 2) {
            if (isStandard) return true;
            List<Rent> rentsMoreThan2 = user.getRentals().subList(2, user.getRentals().size());
            for (Rent rent: rentsMoreThan2) {
//                System.out.println(rent.getMovieTitle());
                if (rent.getMovie().isStandard()) {
                    return true; //not pay the deposit because there is a standard movie
                }
            }
        }
        return false;

//        return !haslostBeforeFidelity && numberOfRentals >= 2;
    }


    private float getPriceRentBasedOnMovieAndTime(Movie movie) {
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
     * count the number of weeks:
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


    public float computeDeposit(Movie movie) {
        if (movie.isStandard()) {
            return 3;
        } else if (movie.isForChildren()) {
            return 1;
        } else { //(movie.isNewReleased())
            return 4;
        }
    }

    public long daysInBetween(LocalDate start, LocalDate end) {
        return ChronoUnit.DAYS.between(start, end);
    }

    /**
     * If the return is late, compute the number of days late and add them to the price.
//     * @param actualEnd the late date
     * @return price to pay (not total price)
     */
    public float computeAdditionalLatePrice() {
        long lateDays = daysInBetween(end, actualEnd);
//        System.out.println("COMPUTE ADDITIONAL PRICE");
//        System.out.println("DAYS IN BETWEEN " + end.getDayOfMonth() + " " + actualEnd.getDayOfMonth() + " ");
//        System.out.println(lateDays);

        float lateRentPrice = 2 * lateDays;
        this.price += lateRentPrice;

        if (lateDays > 0) this.deposit = 0;//lose the deposit if the rent is late
//        System.out.println("RENT" + lateRentPrice);
//        System.out.println("deposit" + this.deposit);
        return lateRentPrice;
    }




}
