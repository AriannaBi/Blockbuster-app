package com.example.blockbuster;

import java.io.IOException;
import java.time.LocalDate;

public class UserRentDTO {

    private String movieId;
    private String userId;
    private LocalDate start;
    private LocalDate end;
    private LocalDate actualEnd;

    public UserRentDTO(String movieId, String userId, LocalDate start, LocalDate end, LocalDate actualEnd) {
//        checkDate(start,end,actualEnd);
        this.start = start;
        this.end = end;
        this.movieId = movieId;
        this.userId = userId;
        this.actualEnd = actualEnd;
    }

//    public void checkDate(LocalDate start, LocalDate end, LocalDate actualEnd) {
//        if (start.isAfter(end) && actualEnd == null){
//            this.start = start;
//            this.end = end;
//        } else {
//            this.start = null;
//            this.end = null;
//        }
//    }


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
