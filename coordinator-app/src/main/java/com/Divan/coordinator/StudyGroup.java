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
import java.util.Scanner;

/**
 * The StudyGroup class represents a study group in the system.
 */
public class StudyGroup implements Serializable {
    /** The serial version UID for serialization. */
	private static final long serialVersionUID = 1L;

    /** The name of the study group. */
    private String name;

    /** A brief description of the study group. */
    private String description;

    /** The list of members in the study group. */
    private List<User> members;

    /**
     * Constructs a StudyGroup object with the specified name and description.
     *
     * @param name        The name of the study group.
     * @param description The description of the study group.
     */
    public StudyGroup(String name, String description) {
        this.name = name;
        this.description = description;
        this.members = new ArrayList<>();
    }

    /**
     * Gets the name of the study group.
     *
     * @return The name of the study group.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the description of the study group.
     *
     * @return The description of the study group.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the list of members in the study group.
     *
     * @return The list of members in the study group.
     */
    public List<User> getMembers() {
        return members;
    }

    /**
     * Adds a member to the study group.
     *
     * @param member The user to add as a member of the study group.
     */
    public void addMember(User member) {
        members.add(member);
    }

    // Methods for managing study groups

    // Define static list of study groups
    static List<StudyGroup> groups1 = new ArrayList<>();

    /**
     * Creates a new study group by taking input from the user.
     *
     * @param scanner The Scanner object for user input.
     */
    public static void createStudyGroup(Scanner scanner) {
        CoordinatorApp.clearScreen();
        System.out.println("---- Study Group ----");

        scanner.nextLine();

        // Input group details
        System.out.print("Enter Group Name: ");
        String groupName = scanner.nextLine();
        System.out.print("Enter Group Description: ");
        String groupDescription = scanner.nextLine();

        // Display user IDs and usernames
        Account.displayUsernamesWithIDs();

        List<User> groupMembers = new ArrayList<>();

        while (true) {
            // Select users to add to the group
            System.out.println("Enter user ID to add (or type 'exit' to finish adding users):");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                break; // Exit the loop if user types 'exit'
            }

            try {
                int id = Integer.parseInt(input);
                User user = Account.findUserById(id);
                if (user != null) {
                    if (!groupMembers.contains(user)) {
                        // Add user to the group members list
                        groupMembers.add(user);

                        System.out.println("Added user: " + user.getUsername() + " to the group.");
                    } else {
                        System.out.println("User with ID " + id + " is already in the group.");
                    }
                } else {
                    System.out.println("User with ID " + id + " not found.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid user ID format: " + input);
            }
        }

        // Create the study group with the provided details and members
        StudyGroup studyGroup = new StudyGroup(groupName, groupDescription);
        for (User member : groupMembers) {
            studyGroup.addMember(member);
            member.setGroup(studyGroup);
            Account.saveUsersToFile("users.bin");
        }

        groups1.add(studyGroup);
        FileUtility.writeGroupsToFile(groups1);

        System.out.println("Study group created successfully!");
        System.out.println("1. Create another study group");
        System.out.println("2. Back to menu");
        int choice = scanner.nextInt(); // Assuming getValidChoice is defined elsewhere
        if (choice == 1) {
            createStudyGroup(scanner);
        } else {
            CoordinatorApp.groupManagementMenu(scanner); // Assuming groupManagementMenu is defined elsewhere
        }
    }

    /**
     * Displays the study groups for viewing.
     *
     * @param scanner The Scanner object for user input.
     */
    public static void viewStudyGroup(Scanner scanner) {
        List<StudyGroup> loadedGroups = FileUtility.readGroupsFromFile();
        if (loadedGroups != null && !loadedGroups.isEmpty()) {
            CoordinatorApp.clearScreen();
            ViewGroups.viewGroups(loadedGroups, scanner);
        } else {
            System.out.println("No study groups found.");
            CoordinatorApp.groupManagementMenu(scanner);
        }
    }

}
