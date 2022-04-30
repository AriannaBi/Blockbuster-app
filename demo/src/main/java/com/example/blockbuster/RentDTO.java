package com.example.blockbuster;

import java.time.LocalDate;

public class RentDTO {

    private String id;
    private LocalDate start;
    private LocalDate end;
    private float price; //initial rent and final rent
    private float deposit; //deposit to return
    private Movie movie;


    /**
     * Constructor RentDTO
     * @param id rent id
     * @param start starting date of rent
     * @param end ending date of rent
     * @param price price paid for rent
     * @param deposit deposit for rent
     * @param movie movie rented
     */
    public RentDTO(String id, LocalDate start,LocalDate end, float price, float deposit, Movie movie) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.price = price;
        this.deposit = deposit;
        this.movie = movie;
    }


    /**
     * Return id rent
     * @return String id rent
     */
    public String getId() {
        return id;
    }

    /**
     * Return starting date of rent
     * @return LocalDate start of rent
     */
    public LocalDate getStart() {
        return start;
    }
    /**
     * Return ending date of rent
     * @return LocalDate end of rent
     */
    public LocalDate getEnd() {
        return end;
    }

    /**
     * Return the price paid for the rent
     * @return float price
     */
    public float getPrice() {
        return price;
    }

    /**
     * Return the deposit paid for the rent
     * @return float deposit
     */
    public float getDeposit() {
        return deposit;
    }

    /**
     * Return the movie rented
     * @return Movie rented
     */
    public Movie getMovie() {
        return movie;
    }
}
