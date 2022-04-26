package com.example.blockbuster;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;


@Document("movie")
public class Movie {
    @Id
    private String id;
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

//    public Movie() {}

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

//    /**
//     * @return a hashcode for the task
//     */
//    @Override
//    public int hashCode() {
//        return Objects.hash(firstName, lastName, thesisTitle);
//    }


}
