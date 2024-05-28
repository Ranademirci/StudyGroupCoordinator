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

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The ResourceManager class manages the resources in the system.
 */
public class ResourceManager {
    /** The file path for saving and loading resources. */
    public static final String RESOURCES_FILE = "Resources.bin";
    
    /** The list of resources managed by this manager. */
    private List<Resource> resources;
    
    /** The file object representing the resources file. */
    private static File file = new File(RESOURCES_FILE);

    /**
     * Constructs a new ResourceManager object.
     */
    public ResourceManager() {
        this.resources = new ArrayList<>();
    }

    /**
     * Adds a resource to the resource list.
     *
     * @param resource The resource to add
     */
    public void addResource(Resource resource) {
        resources.add(resource);
    }

    /**
     * Retrieves the list of resources.
     *
     * @return The list of resources
     */
    public List<Resource> getResources() {
        return resources;
    }

    /**
     * Displays the resources available for a specific study group.
     *
     * @param group The study group to display resources for
     */
    public void displayResourcesForGroup(StudyGroup group) {
        System.out.println("Resources for group " + group.getName() + ":");
        boolean foundResources = false;
        for (Resource resource : resources) {
            if (resource.getGroup().getName().equals(group.getName())) {
                foundResources = true;
                System.out.println("Title: " + resource.getTitle());
            }
        }
        if (!foundResources) {
            System.out.println("Your group doesn't have any resources.");
        }
    }

    /**
     * Writes the resources to a file.
     *
     * @param resources The list of resources to write to the file
     */
    public static void writeResourcesToFile(List<Resource> resources) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file))) {
            outputStream.writeObject(resources);
            System.out.println("Resources have been saved to " + RESOURCES_FILE);
        } catch (Exception Ignored) {
        }
    }

    /**
     * Reads resources from the file.
     *
     * @return The list of resources read from the file
     */
    public static List<Resource> readResourcesFromFile() {
        if (!file.exists()) {
            System.out.println("Resources file does not exist.");
            return new ArrayList<>();
        }

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file))) {
            @SuppressWarnings("unchecked")
            List<Resource> resources = (List<Resource>) inputStream.readObject();
            System.out.println("Resources have been loaded from " + RESOURCES_FILE);
            return resources;
        } catch (Exception Ignored) {
            return new ArrayList<>();
        }
    }
}
