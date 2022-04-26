package com.example.blockbuster;

import java.util.ArrayList;

public class UserDTO {

    private String id;
    private String name;
    private boolean lostMovie;



    private ArrayList<Rent> rentals;

    public UserDTO() {}

    /**
     * Constructor to modify user with an already existing id
     * @param id id user
     * @param name string name user
     */
    public UserDTO(String id, String name, boolean lostMovie, ArrayList<Rent> rentals) {
        this.id = id;
        this.name = name;
        this.lostMovie = lostMovie;
        this.rentals = rentals;
    }

    /**
     * Constructor for creation of user and id is automatically assigned
     * @param name string name user
     */
    public UserDTO(String name) {
        this.name = name;
    }

    public boolean isLostMovie() {
        return lostMovie;
    }

    public ArrayList<Rent> getRentals() {
        return rentals;
    }


    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
