package com.example.blockbuster;

import java.time.LocalDate;

public class RentDTO {

    private String id;
    private LocalDate start;
    private LocalDate end;
    private float price; //initial rent and final rent
    //    private final float pricePerDay;
    // if deposit is > 0 is a debit, if deposit is < 0 is a credit
    private float deposit; //deposit to return
    private Movie movie;


    public RentDTO(String id, LocalDate start,LocalDate end, float price, float deposit, Movie movie) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.price = price;
        this.deposit = deposit;
        this.movie = movie;
    }


    public String getId() {
        return id;
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public float getPrice() {
        return price;
    }

    public float getDeposit() {
        return deposit;
    }

    public Movie getMovie() {
        return movie;
    }
}
