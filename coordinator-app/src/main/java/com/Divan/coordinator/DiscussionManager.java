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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * The DiscussionManager class manages discussions, including adding discussions, displaying discussions for a group,
 * and reading/writing discussions to/from a file.
 */
public class DiscussionManager {
    /** The file path for storing discussions. */
    public static final String DISCUSSIONS_FILE = "Discussions.bin";
    
    /** The list of discussions managed by this manager. */
    private List<Discussion> discussions;

    /**
     * Constructs a new DiscussionManager object with an empty list of discussions.
     */
    public DiscussionManager() {
        this.discussions = new ArrayList<>();
    }

    /**
     * Adds a discussion to the list of discussions.
     *
     * @param discussion The discussion to be added
     */
    public void addDiscussion(Discussion discussion) {
        discussions.add(discussion);
    }

    /**
     * Retrieves the list of discussions.
     *
     * @return The list of discussions
     */
    public List<Discussion> getDiscussions() {
        return discussions;
    }

    /**
     * Displays discussions belonging to a specific study group.
     *
     * @param group The study group for which discussions are to be displayed
     */
    public void displayDiscussionsForGroup(StudyGroup group) {
        System.out.println("Discussions for group " + group.getName() + ":");
        boolean foundDiscussion = false;
        for (Discussion discussion : discussions) {
            if (discussion.getGroup().getName().equals(group.getName())) {
                System.out.println("Topic: " + discussion.getTopic());
                System.out.println("Comments:");
                for (String comment : discussion.getComments()) {
                    System.out.println("- " + CoordinatorApp.currentUser.getUsername() + " : " + comment);
                }
                foundDiscussion = true;
            }
            
            else {
            	System.out.println("Your group doesn't have any discussions");
            	}
        }
        
        
       
    
    }

    /**
     * Writes the list of discussions to a file.
     *
     * @param discussions The list of discussions to be written to file
     */
    public static void writeDiscussionsToFile(List<Discussion> discussions) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(DISCUSSIONS_FILE))) {
            outputStream.writeObject(discussions);
            System.out.println("Discussions have been saved to " + DISCUSSIONS_FILE);
        } catch (Exception Ignored) {
        }
    }

    /**
     * Reads the list of discussions from a file.
     *
     * @return The list of discussions read from the file
     */
    public static List<Discussion> readDiscussionsFromFile() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(DISCUSSIONS_FILE))) {
            @SuppressWarnings("unchecked")
            List<Discussion> discussions = (List<Discussion>) inputStream.readObject();
            System.out.println("Discussions have been loaded from " + DISCUSSIONS_FILE);
            return discussions;
        } catch (Exception Ignored) {
            return new ArrayList<>();
        }
    }
}
