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
import java.util.List;

/**
 * Utility class for handling file operations related to study groups.
 */
public class FileUtility {
    
    /** File path for storing and loading study groups. */
    public static final String GROUPS_FILE = "Studygroups.bin";

    /**
     * Writes the list of study groups to a binary file.
     *
     * @param groups The list of study groups to write to file
     */
    public static void writeGroupsToFile(List<StudyGroup> groups) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(GROUPS_FILE))) {
            outputStream.writeObject(groups);
            System.out.println("Study groups have been saved to " + GROUPS_FILE);
        } catch (Exception Ignored) {
        }
    }

    /**
     * Reads the list of study groups from a binary file.
     *
     * @return The list of study groups read from file, or null if an error occurs
     */
    public static List<StudyGroup> readGroupsFromFile() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(GROUPS_FILE))) {
            @SuppressWarnings("unchecked")
			List<StudyGroup> groups = (List<StudyGroup>) inputStream.readObject();
            System.out.println("Study groups have been loaded from " + GROUPS_FILE);
            return groups;
        } catch (Exception Ignored) {
            return null;
        }
    }
}
