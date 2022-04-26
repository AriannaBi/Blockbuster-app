package com.example.blockbuster;

import java.time.LocalDate;

public class UserReturnDTO {

    private LocalDate end;

    public UserReturnDTO(LocalDate start, LocalDate end) {
        this.end = end;
    }

    public LocalDate getEnd() {
        return end;
    }


}
