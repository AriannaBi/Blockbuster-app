package com.example.blockbuster;

import java.util.ArrayList;

public class UserDTO {

    private String id;
    private String name;
    private boolean lostBeforeFidelity;



    private ArrayList<Rent> rentals;

    public UserDTO() {}

    /**
     * Constructor to modify user with an already existing id
     * @param id id user
     * @param name string name user
     */
    public UserDTO(String id, String name, boolean lostBeforeFidelity, ArrayList<Rent> rentals) {
        this.id = id;
        this.name = name;
        this.lostBeforeFidelity = lostBeforeFidelity;
        this.rentals = rentals;
    }

//    for put lost movie

    /**
     * Constructor for PUT user, update the field lostBeforeFidelity to true
     * @param lostBeforeFidelity if he has lost a movie
     */
    public UserDTO(Boolean lostBeforeFidelity) {
        this.lostBeforeFidelity = lostBeforeFidelity;
    }

    /**
     * Constructor for creation of user and id is automatically assigned
     * @param name string name user
     */
    public UserDTO(String name) {
        this.name = name;
    }

    public boolean getlostBeforeFidelity() {
        return lostBeforeFidelity;
    }

    public void setlostBeforeFidelity(boolean lostBeforeFidelity) {
        this.lostBeforeFidelity = lostBeforeFidelity;
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
