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

import java.util.List;
import java.util.Scanner;

/**
 * The ViewGroups class provides methods to view study groups and their details.
 */
public class ViewGroups {

    /**
     * Displays a list of study groups and allows the user to view group details.
     *
     * @param groups  The list of study groups to display.
     * @param scanner The Scanner object for user input.
     */
    public static void viewGroups(List<StudyGroup> groups, Scanner scanner) {
        System.out.println("---- Study Groups ----");
            int index = 1;
            for (StudyGroup group : groups) {
                System.out.println(index + ". " + group.getName());
                index++;
            }

            // Allow user to view group details
            scanner.nextLine();
            System.out.println("Enter the number of the group to view details (or 0 to go back):");
            int choice = scanner.nextInt();
            if (choice == 0) {
                CoordinatorApp.groupManagementMenu(scanner);
            } else if (choice > 0 && choice <= groups.size()) {
                StudyGroup selectedGroup = groups.get(choice - 1);
                viewGroupDetails(selectedGroup, scanner);
            }
        }

    /**
     * Displays the details of a specific study group.
     *
     * @param group   The study group to view details for.
     * @param scanner The Scanner object for user input.
     */
    private static void viewGroupDetails(StudyGroup group, Scanner scanner) {
        System.out.println("---- Group Details ----");
        System.out.println("Group Name: " + group.getName());
        System.out.println("Group Description: " + group.getDescription());
        System.out.println("Group Members:");
        for (User member : group.getMembers()) {
            System.out.println("- " + member.getUsername());
        }

        scanner.nextLine();
        System.out.println("Write 'return' to return");
        String input = scanner.nextLine().trim();

        if (input.equalsIgnoreCase("return")) {
            CoordinatorApp.groupManagementMenu(scanner);
        }
    }
}
