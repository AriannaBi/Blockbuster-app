package com.example.blockbuster.Service;

import com.example.blockbuster.Rent;

import java.util.ArrayList;

public class UserDTO {

    private String id;
    private String name;
    private boolean lostBeforeFidelity;



    private ArrayList<Rent> rentals;

    /**
     * Empty contructor
     */
    public UserDTO() {}

    /**
     * Constructor to create UserDTO
     * @param id id user
     * @param name string name user
     */
    public UserDTO(String id, String name, boolean lostBeforeFidelity, ArrayList<Rent> rentals) {
        this.id = id;
        this.name = name;
        this.lostBeforeFidelity = lostBeforeFidelity;
        this.rentals = rentals;
    }


    /**
     * Constructor to create UserDTO
     * @param lostBeforeFidelity if he has lost a movie
     */
    public UserDTO(Boolean lostBeforeFidelity) {
        this.lostBeforeFidelity = lostBeforeFidelity;
    }

    /**
     * Constructor to create UserDTO
     * @param name string name user
     */
    public UserDTO(String name) {
        this.name = name;
    }

    /**
     * Return if the user has lost the fidelity
     * @return boolean lost before the fidelity
     */
    public boolean getlostBeforeFidelity() {
        return lostBeforeFidelity;
    }

    /**
     * Given if the user has lost the fidelity, set the relative field to that value
     * @param lostBeforeFidelity boolean lost before the fidelity
     */
    public void setlostBeforeFidelity(boolean lostBeforeFidelity) {
        this.lostBeforeFidelity = lostBeforeFidelity;
    }

    /**
     * Get the list of rentals
     * @return arraylist of rental
     */
    public ArrayList<Rent> getRentals() {
        return rentals;
    }


    /**
     * Return the name of the user
     * @return string name
     */
    public String getName() {
        return name;
    }

    /**
     * Return the id of the user
     * @return string id
     */
    public String getId() {
        return id;
    }
}
