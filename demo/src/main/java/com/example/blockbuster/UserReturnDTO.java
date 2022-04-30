package com.example.blockbuster;

import java.time.LocalDate;

public class UserReturnDTO {

    private LocalDate actualEnd;

    /**
     * Constructor of UserReturnDTO
     * @param actualEnd
     */
    public UserReturnDTO(LocalDate actualEnd) {
        this.actualEnd = actualEnd;
    }

    /**
     * EMpty constructor
     */
    public UserReturnDTO() {}

    /**
     * Return date in which the movie was returned back
     * @return LocalDate of return of the movie
     */
    public LocalDate getActualEnd() {
        return actualEnd;
    }


}
