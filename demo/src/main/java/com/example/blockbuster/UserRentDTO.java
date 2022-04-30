package com.example.blockbuster;

import java.io.IOException;
import java.time.LocalDate;

public class UserRentDTO {

    private String movieId;
    private String userId;
    private LocalDate start;
    private LocalDate end;
    private LocalDate actualEnd;

    /**
     * Constructor UserRentDTO
     * @param movieId string movie id
     * @param userId string user id
     * @param start LocalDate starting date of rent
     * @param end LocalDate ending date of rent
     * @param actualEnd LocalDate of return of the movie
     */
    public UserRentDTO(String movieId, String userId, LocalDate start, LocalDate end, LocalDate actualEnd) {
        this.start = start;
        this.end = end;
        this.movieId = movieId;
        this.userId = userId;
        this.actualEnd = actualEnd;
    }


    /**
     * Empty constructor
     */
    public UserRentDTO() {}

    /**
     * Return movie id
     * @return string movie id
     */
    public String getMovieId() {
        return movieId;
    }

    /**
     * Return user id
     * @return string user id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Return start date of rent
     * @return LocalDate start day of rent
     */
    public LocalDate getStart() {
        return start;
    }

    /**
     * Return end date of rent
     * @return LocalDate end day of rent
     */
    public LocalDate getEnd() {
        return end;
    }

    /**
     * Return date of return movie
     * @return LocalDate return day of movie
     */
    public LocalDate getActualEnd() {
        return actualEnd;
    }


}
