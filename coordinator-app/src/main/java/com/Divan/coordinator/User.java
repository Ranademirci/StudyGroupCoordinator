/**
 * The com.Divan.coordinator package contains classes and interfaces related to coordination tasks
 * in the application developed by Rana Melih.
 * 
 * This package serves as a coordination module facilitating communication and orchestration
 * among different components of the application.
 * 
 * Classes within this package might include implementations of coordinator patterns,
 * such as the mediator pattern, or other coordination strategies.
 * 
 * @author Rana Melih
 * @version 1.0.3
 * @since 2024-03-28
 */
package com.Divan.coordinator;

import java.io.Serializable;

/**
 * The User class represents a user in the system.
 */
public class User implements Serializable {
    /** The serial version UID for serialization. */
    private static final long serialVersionUID = 1L;

    /** The user's first name. */
    private String name;

    /** The user's last name. */
    private String surname;

    /** The unique identifier for the user. */
    private int id;

    /** The username used for authentication. */
    private String username;

    /** The password used for authentication. */
    private String password;

    /** The study group associated with the user. */
    private StudyGroup group;

    /**
     * Constructs a User object with the specified attributes.
     *
     * @param name     The user's name.
     * @param surname  The user's surname.
     * @param id       The user's ID.
     * @param username The user's username.
     * @param password The user's password.
     */
    public User(String name, String surname, int id, String username, String password) {
        this.name = name;
        this.surname = surname;
        this.id = id;
        this.username = username;
        this.password = password;
    }

    /**
     * Gets the user's name.
     *
     * @return The user's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the user's surname.
     *
     * @return The user's surname.
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Gets the user's ID.
     *
     * @return The user's ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the user's username.
     *
     * @return The user's username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the user's password.
     *
     * @return The user's password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets the study group the user belongs to.
     *
     * @return The study group the user belongs to.
     */
    public StudyGroup getGroup() {
        return group;
    }

    /**
     * Sets the study group for the user.
     *
     * @param group The study group to set for the user.
     */
    public void setGroup(StudyGroup group) {
        this.group = group;
    }
}
