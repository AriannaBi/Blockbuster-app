package com.example.blockbuster;

import java.time.LocalDate;

public class UserRentDTO {

    private String movieId;
    private LocalDate start;
    private LocalDate end;


    public UserRentDTO(String movieId, LocalDate start, LocalDate end) {
        this.movieId = movieId;
        this.start = start;
        this.end = end;
    }

    public UserRentDTO() {}

    public String getMovieId() {
        return movieId;
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }


}
