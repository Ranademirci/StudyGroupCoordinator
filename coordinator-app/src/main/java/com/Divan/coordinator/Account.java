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
import java.util.Scanner;

/**
 * The Account class represents user account management and authentication.
 */
public class Account {
    /** File path for user data storage. */
    private String FILE_PATH = "users.bin";
    
    /** List of users registered in the system. */
    private static List<User> userList;

    /**
     * Constructs an Account object with an empty list of users.
     */
    public Account() {
        this.userList = new ArrayList<>();
    }

    /**
     * Gets the list of users stored in this account.
     *
     * @return The list of users
     */
    public List<User> getUserList() {
        return userList;
    }

    /**
     * Saves the list of users to a file.
     *
     * @param fileName The name of the file to save the users to
     */
    public static void saveUsersToFile(String fileName) {
        try {
            // Create or overwrite the file
            File file = new File(fileName);
            if (!file.exists()) {
                if (!file.createNewFile()) {
                    return;
                }
            }

            // Update group references if needed
            for (User user : userList) {
                if (user.getGroup() != null) {
                    user.setGroup(user.getGroup());
                }
            }

            // Write users to file using object serialization
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(userList);
            }
        } catch (Exception Ignored) {
        }
    }

    /**
     * Loads the list of users from a file.
     *
     * @param fileName The name of the file to load users from
     */
    @SuppressWarnings("unchecked")
    public void loadUsersFromFile(String fileName) {
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                System.err.println("Error: File not found - " + fileName);
                return;
            }

            // Read users from file using object deserialization
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                Object obj = ois.readObject();
                if (obj instanceof List) {
                    userList = (List<User>) obj;
                    System.out.println("Users loaded from file: " + fileName);
                } else {
                    System.err.println("Error: Invalid data format in file - " + fileName);
                }
            }
        } catch (Exception Ignored) {
        }
    }

    /**
     * Registers a new user by obtaining user information from the user via console input.
     *
     * @param scanner The scanner object for input
     */
    public void register(Scanner scanner) {
        CoordinatorApp.clearScreen();
        scanner.nextLine();
        System.out.println("---- Register ----");
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Surname: ");
        String surname = scanner.nextLine();
        int id;
        String username;

        // Check for unique ID
        do {
            System.out.print("ID: ");
            id = scanner.nextInt();
            scanner.nextLine(); // Consume newline character
            if (!isIdUnique(id)) {
                System.out.println("ID already exists. Please choose another one.");
            }
        } while (!isIdUnique(id));

        // Check for unique username
        do {
            System.out.print("Username: ");
            username = scanner.nextLine();
            if (!isUsernameUnique(username)) {
                System.out.println("Username already exists. Please choose another one.");
            }
        } while (!isUsernameUnique(username));

        System.out.print("Password: ");
        String password = scanner.nextLine();

        User account = new User(name, surname, id, username, password);
        userList.add(account); // Add the user to the list
        saveUsersToFile(FILE_PATH); // Save the list to file
        System.out.println("Registration successful!");
    }

    /**
     * Checks if the given ID is unique among users.
     * @param id The ID to check.
     * @return true if the ID is unique, false otherwise.
     */
    private boolean isIdUnique(int id) {
        for (User user : userList) {
            if (user.getId() == id) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the given username is unique among users.
     * @param username The username to check.
     * @return true if the username is unique, false otherwise.
     */
    private boolean isUsernameUnique(String username) {
        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Performs user login by verifying username and password.
     *
     * @param scanner The scanner object for input
     * @return The logged-in user or null if login fails
     */
    public User login(Scanner scanner) {
        scanner.nextLine();
        System.out.println("---- Login ----");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        for (User user : userList) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null; // Login failed
    }

    /**
     * Displays the menu for user registration or login, and handles user input accordingly.
     *
     * @param scanner The scanner object for input
     * @return The logged-in user
     */
    public User loginMenu(Scanner scanner) {
        while (true) {
            CoordinatorApp.clearScreen();

            System.out.println("---- Authentication ----");
            System.out.println("1. Register");
            System.out.println("2. Login");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    register(scanner);
                    break;
                case 2:
                    User loggedInUser = login(scanner);
                    if (loggedInUser != null) {
                        System.out.println("Login successful.");
                        return loggedInUser;
                    } else {
                        System.out.println("Login failed. Please try again.");
                    }
                    break;
            }
        }
    }

    /**
     * Displays the usernames and IDs of all users in the user list.
     */
    public static void displayUsernamesWithIDs() {
        System.out.println("User IDs and Usernames:");
        for (User user : userList) {
            System.out.println("ID: " + user.getId() + " - Username: " + user.getUsername());
        }
    }

    /**
     * Finds a user in the user list by their ID.
     *
     * @param id The ID of the user to find
     * @return The user object if found, or null if not found
     */
    public static User findUserById(int id) {
        for (User user : userList) {
            if (user.getId() == id) {
                return user; // Found the user with the given ID
            }
        }
        return null; // User with the given ID not found
    }
}
