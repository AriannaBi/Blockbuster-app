package com.example.blockbuster;

import java.time.LocalDate;

public class UserRentDTO {

    private String movieId;
    private String userId;
    private LocalDate start;
    private LocalDate end;
    private LocalDate actualEnd;

    private String idRent;
    public UserRentDTO(String movieId, String userId, LocalDate start, LocalDate end, LocalDate actualEnd) {
        this.movieId = movieId;
        this.userId = userId;
        this.start = start;
        this.end = end;
    }


    public UserRentDTO() {}

    public String getMovieId() {
        return movieId;
    }
    public String getUserId() {
        return userId;
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public LocalDate getActualEnd() {
        return actualEnd;
    }


}
