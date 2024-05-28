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
import java.util.ArrayList;
import java.util.List;

/**
 * The Session class represents a session in the system.
 */
public class Session implements Serializable {
    /** The serial version UID for serialization. */
    private static final long serialVersionUID = 1L;

    /** The title of the session. */
    private String title;

    /** The date of the session. */
    private String date;

    /** The study group associated with the session. */
    private StudyGroup group;

    /** A description of the session. */
    private String description;

    /** A list of sessions related to this session. */
    private List<String> sessions;

    /**
     * Constructs a new Session object.
     *
     * @param title       The title of the session
     * @param date        The date of the session
     * @param group       The study group associated with the session
     * @param description The description of the session
     */
    public Session(String title, String date, StudyGroup group, String description) {
        this.title = title;
        this.date = date;
        this.group = group;
        this.description = description;
        this.sessions = new ArrayList<>();
    }

    /**
     * Retrieves the title of the session.
     *
     * @return The title of the session
     */
    public String getTitle() {
        return title;
    }

    /**
     * Retrieves the date of the session.
     *
     * @return The date of the session
     */
    public String getDate() {
        return date;
    }

    /**
     * Retrieves the study group associated with the session.
     *
     * @return The study group associated with the session
     */
    public StudyGroup getGroup() {
        return group;
    }

    /**
     * Retrieves the description of the session.
     *
     * @return The description of the session
     */
    public String getDescription() {
        return description;
    }

    /**
     * @brief Sets the title of the object.
     * 
     * @param title The new title to be set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @brief Sets the date of the object.
     * 
     * @param date The new date to be set.
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @brief Sets the description of the object.
     * 
     * @param description The new description to be set.
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
