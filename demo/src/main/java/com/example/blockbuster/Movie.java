package com.example.blockbuster;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Objects;

@Document("movie")
public class Movie {
    @Id
    private String id;
    private String title;
    private boolean standard;
    private boolean forChildren;
    private boolean newReleased;

    /**
     * Empty constructor
     */
    public Movie() {}

    /**
     * Constructor for movie
     * @param title string title movie
     * @param standard boolean standard
     * @param forChildren boolean for children
     * @param newReleased boolean new released
     */
    public Movie(String title, boolean standard, boolean forChildren, boolean newReleased) {
        this.title = title;
        this.standard = standard;
        this.forChildren = forChildren;
        this.newReleased = newReleased;
    }

    /**
     * Return id movie
     * @return string id movie
     */
    public String getId() { return id;}

    /**
     * Return if the movie is standard
     * @return boolean standard
     */
    public boolean isStandard() {
        return standard;
    }
    /**
     * Return if the movie is for children
     * @return boolean for children
     */
    public boolean isForChildren() {
        return forChildren;
    }
    /**
     * Return title movie
     * @return String title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Return if the movie is a new released
     * @return boolean new released
     */
    public boolean isNewReleased() {
        return newReleased;
    }

    /**
     * Overriding the equals function
     * @param o Object used to compare
     * @return False if the two Objects are not equal, True otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var movie = (Movie) o;
        return Objects.equals(title, movie.title) && Objects.equals(standard, movie.standard) && Objects.equals(forChildren, movie.forChildren) && Objects.equals(newReleased, movie.newReleased);
    }

}
