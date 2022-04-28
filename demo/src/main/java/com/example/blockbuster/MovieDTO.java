package com.example.blockbuster;

public class MovieDTO {

    private final String id;
    private final String title;
    private final boolean standard;
    private final boolean forChildren;
    private final boolean newReleased;

    public MovieDTO(String id, String title, boolean standard, boolean forChildren, boolean newReleased) {
        this.id = id;
        this.title = title;
        this.standard = standard;
        this.forChildren = forChildren;
        this.newReleased = newReleased;
    }

//    public MovieDTO() {}

    public String getId() { return id; }

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
