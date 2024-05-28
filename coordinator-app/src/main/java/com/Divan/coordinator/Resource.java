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
 * The Resource class represents a resource item that can be shared within a study group.
 */
public class Resource implements Serializable {
	/**
	 * Serializable identifier for object persistence.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The title of the coordinator.
	 */
	private String title;

	/**
	 * A brief description of the coordinator.
	 */
	private String description;

	/**
	 * The link associated with the coordinator.
	 */
	private String link;

	/**
	 * The study group associated with the coordinator.
	 */
	private StudyGroup group;

	/**
	 * A list of additional resources related to the coordinator.
	 */
	private List<String> resources;

    /**
     * Constructs a Resource object with the specified details.
     *
     * @param title       The title of the resource
     * @param description The description of the resource
     * @param link        The link associated with the resource
     * @param group       The study group associated with the resource
     */
    public Resource(String title, String description, String link, StudyGroup group) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.group = group;
        this.resources = new ArrayList<>();
    }

    /**
     * Gets the title of the resource.
     *
     * @return The title of the resource
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the description of the resource.
     *
     * @return The description of the resource
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the link associated with the resource.
     *
     * @return The link associated with the resource
     */
    public String getLink() {
        return link;
    }

    /**
     * Gets the study group associated with the resource.
     *
     * @return The study group associated with the resource
     */
    public StudyGroup getGroup() {
        return group;
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
     * @brief Sets the link of the object.
     * 
     * @param link The new link to be set.
     */
    public void setLink(String link) {
        this.link = link;
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
