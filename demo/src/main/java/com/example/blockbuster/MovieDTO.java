package com.example.blockbuster;

public class MovieDTO {

    private final String title;
    private final boolean standard;
    private final boolean forChildren;
    private final boolean newReleased;

    public MovieDTO(String title, boolean standard, boolean forChildren, boolean newReleased) {
        this.title = title;
        this.standard = standard;
        this.forChildren = forChildren;
        this.newReleased = newReleased;
    }

//    public MovieDTO() {}

    public boolean isStandard() {
        return standard;
    }

    public boolean isForChildren() {
        return forChildren;
    }

    public boolean isNewReleased() {
        return newReleased;
    }

    public String getTitle() {
        return this.title;
    }
}
