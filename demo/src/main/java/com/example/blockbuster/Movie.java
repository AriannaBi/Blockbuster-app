package com.example.blockbuster;

public class Movie {

    private final String title;
    private final boolean standard;
    private final boolean forChildren;
    private final boolean newReleased;

    public Movie(String title, boolean standard, boolean forChildren, boolean newReleased) {
        this.title = title;
        this.standard = standard;
        this.forChildren = forChildren;
        this.newReleased = newReleased;
    }

    public float getPricePerDay() {
        if (standard) {
            return 5;
        } else if (forChildren){
            return 10;
        } else {
            return 7;
        }
    }


    public boolean isStandard() {
        return standard;
    }

    public boolean isForChildren() {
        return forChildren;
    }

    public String getTitle() {
        return title;
    }


    public boolean isNewReleased() {
        return newReleased;
    }

}
