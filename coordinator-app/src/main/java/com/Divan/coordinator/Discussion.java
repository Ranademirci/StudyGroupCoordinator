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
 * Represents a discussion topic along with comments and the associated study group.
 */
public class Discussion implements Serializable {
	/**
	 * Serializable identifier for object persistence.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The topic of discussion or coordination handled by the coordinator.
	 */
	private String topic;

	/**
	 * A list of comments or remarks related to the coordinator.
	 */
	private List<String> comments;

	/**
	 * The study group associated with the coordinator.
	 */
	private StudyGroup group;

    /**
     * Constructs a Discussion object with the specified topic and associated study group.
     *
     * @param topic The topic of the discussion
     * @param group The study group associated with the discussion
     */
    public Discussion(String topic, StudyGroup group) {
        this.topic = topic;
        this.group = group;
        this.comments = new ArrayList<>();
    }

    /**
     * Gets the topic of the discussion.
     *
     * @return The topic of the discussion
     */
    public String getTopic() {
        return topic;
    }

    /**
     * Gets the list of comments in the discussion.
     *
     * @return The list of comments
     */
    public List<String> getComments() {
        return comments;
    }

    /**
     * Adds a comment to the discussion.
     *
     * @param comment The comment to add
     */
    public void addComment(String comment) {
        comments.add(comment);
    }

    /**
     * Gets the study group associated with the discussion.
     *
     * @return The associated study group
     */
    public StudyGroup getGroup() {
        return group;
    }

	 /**
     * @brief Sets the topic of the object.
     * 
     * @param title The new topic to be set.
     */
    public void setTopic(String topic) {
        this.topic = topic;
    }
}
