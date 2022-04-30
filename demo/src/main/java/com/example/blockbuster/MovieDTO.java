package com.example.blockbuster;

public class MovieDTO {

    private final String id;
    private final String title;
    private final boolean standard;
    private final boolean forChildren;
    private final boolean newReleased;

    /**
     * Constructor of Movie DTO
     * @param id String id movie
     * @param title String title movie
     * @param standard boolean standard
     * @param forChildren boolean for children
     * @param newReleased boolean new released
     */
    public MovieDTO(String id, String title, boolean standard, boolean forChildren, boolean newReleased) {
        this.id = id;
        this.title = title;
        this.standard = standard;
        this.forChildren = forChildren;
        this.newReleased = newReleased;
    }


    /**
     * Return id movie
     * @return String id movie
     */
    public String getId() { return id; }

    /**
     * Return if movie is standard
     * @return boolean is standard
     */
    public boolean isStandard() {
        return standard;
    }

    /**
     * Return if movie is for children
     * @return boolean is for children
     */
    public boolean isForChildren() {
        return forChildren;
    }

    /**
     * Return if movie is new released
     * @return boolean is new released
     */
    public boolean isNewReleased() {
        return newReleased;
    }

    /**
     * Return movie title
     * @return String title
     */
    public String getTitle() {
        return this.title;
    }
}
