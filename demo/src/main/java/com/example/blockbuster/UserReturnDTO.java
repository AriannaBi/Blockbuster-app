package com.example.blockbuster;

import java.time.LocalDate;

public class UserReturnDTO {

    private LocalDate actualEnd;

    public UserReturnDTO(LocalDate actualEnd) {
        this.actualEnd = actualEnd;
    }

    public UserReturnDTO() {}

    public LocalDate getActualEnd() {
        return actualEnd;
    }


}
