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
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.tree.DefaultTreeCellEditor.EditorContainer;

/**
 * CoordinatorApp is the main class that manages the functionality of the coordinator application.
 * This application allows users to manage study groups, schedule sessions, share resources, and participate in discussions.
 */
public class CoordinatorApp {
    /** The account associated with the application. */
    private static Account account = new Account();
    
    /** Scanner object for user input. */
    private static Scanner scanner = new Scanner(System.in);
    
    /** The currently logged-in user. */
    public static User currentUser;
    
    /** The session manager for managing sessions. */
    public static SessionManager sessionManager = new SessionManager();
    
    /** The resource manager for managing resources. */
    public static ResourceManager resourceManager = new ResourceManager();
    
    /** The discussion manager for managing discussions. */
    public static DiscussionManager discussionManager = new DiscussionManager();
    
    /** List of discussions in the application. */
    public static List<Discussion> discussions = new ArrayList<Discussion>();
    
    /** List of resources in the application. */
    public static List<Resource> resources = new ArrayList<Resource>();
    
    /** List of sessions in the application. */
    public static List<Session> sessions = new ArrayList<Session>();

    /**
     * Clears the console screen.
     */
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Displays the main menu of the application.
     * @param scanner Scanner object for user input
     */
    public static void mainMenu(Scanner scanner) {
        clearScreen();
        System.out.println("--- Main Menu ---            Logged in as: " + currentUser.getUsername());
        System.out.println("1. Group Management");
        System.out.println("2. Session Scheduling");
        System.out.println("3. Resource Sharing");
        System.out.println("4. Discussion Board");
        System.out.println("5. Exit");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                groupManagementMenu(scanner);
                break;
            case 2:
                sessionSchedulingMenu(scanner);
                break;
            case 3:
                resourceSharingMenu(scanner);
                break;
            case 4:
                discussionBoardMenu(scanner);
                break;
            case 5:
                System.out.println("Exiting the program...");
                scanner.close();
                return;
        }
    }
    
    /**
     * Displays the group management menu.
     * @param scanner Scanner object for user input
     */
    public static void groupManagementMenu(Scanner scanner) {
        clearScreen();
        System.out.println("--- Group Management Menu ---");
        System.out.println("1. Create Study Group");
        System.out.println("2. View Study Group");
        System.out.println("3. Back to Menu");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                StudyGroup.createStudyGroup(scanner);
                break;
            case 2:
            	StudyGroup.viewStudyGroup(scanner);
                break;
            case 3:
            	mainMenu(scanner);
                return;
        }
    }

    /**
     * Displays the session scheduling menu.
     * @param scanner Scanner object for user input
     */
    public static void sessionSchedulingMenu(Scanner scanner) {
        clearScreen();
        System.out.println("--- Session Scheduling Menu ---");
        System.out.println("1. Schedule Session");
        System.out.println("2. View Sessions");
        System.out.println("3. Edit Sessions");
        System.out.println("4. Back to Main Menu");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
            	if(currentUser.getGroup()!= null)
            	{
                    scheduleSession(scanner);
            	}
            	else {
            	System.out.println("You dont have a group");
            	sessionSchedulingMenu(scanner);
            	}
                break;
            case 2:
            	if(currentUser.getGroup()!= null)
            	{
            		viewSessions(scanner);
            	}
            	else {
            	System.out.println("You dont have a group");
            	sessionSchedulingMenu(scanner);
              
            	}
                break;
            case 3:
            	if(currentUser.getGroup()!= null)
            	{
            		editSessionDetails(scanner);
            	}
            	else {
            	System.out.println("You dont have a group");
            	sessionSchedulingMenu(scanner);
            	}
            	break;
            case 4:
                mainMenu(scanner);
                break;
        }
    }

    /**
     * Allows the user to schedule a session.
     * @param scanner Scanner object for user input
     */
    public static void scheduleSession(Scanner scanner) {
        clearScreen();
        System.out.println("--- Schedule Session ---");
        // Implement session scheduling logic here
        System.out.println("Enter session title:");
        String title = scanner.next();
        System.out.println("Enter session date:");
        String date = scanner.next();
        System.out.println("Enter session description:");
        String description = scanner.next();
        Session session = new Session(title, date, currentUser.getGroup(), description);
        sessions.add(session);
        SessionManager.writeSessionsToFile(sessions);
        System.out.println("Session scheduled successfully!");
        sessionSchedulingMenu(scanner);
    }

    /**
     * Displays the list of sessions for the user's group and allows viewing session details.
     * @param scanner Scanner object for user input
     */
    public static void viewSessions(Scanner scanner) {
    	scanner.nextLine();
        clearScreen();
      
        
        System.out.println("--- View Session Details ---");
        sessionManager.displaySessionsForGroup(currentUser.getGroup());
        System.out.println("Enter the title of the resource you want to view details for (or type 'cancel' to return to the menu):");
        String titleChoice = scanner.nextLine();
        if (titleChoice.equalsIgnoreCase("cancel")) {
            sessionSchedulingMenu(scanner); // Return to resource sharing menu if user cancels
            return;
        }

        // Search for the resource with the specified title and group
        Session sessionToShow = null;
        for (Session session : sessionManager.getSessions()) {
            if (session.getTitle().equalsIgnoreCase(titleChoice) && session.getGroup().getName().equals(currentUser.getGroup().getName())) {
                sessionToShow = session;
                break;
            }
        }

        // If resource is found, display its details
        if (sessionToShow != null) {
            System.out.println("Title: " + sessionToShow.getTitle());
            System.out.println("Date: " + sessionToShow.getDate());
            System.out.println("Description: " + sessionToShow.getDescription());
        } else {
            System.out.println("Session not found or you don't have access to view this session.");
        }

        // After viewing resource details, return to resource sharing menu
        scanner.nextLine(); // Consume newline character
        return;
    }
    
    /**
     * @brief Allows the user to edit details of a session.
     * 
     * This method displays a list of sessions for the current user's group and
     * prompts the user to choose a session to edit. It then allows the user to
     * edit the session's title, date, and description.
     * 
     * @param scanner The Scanner object used for input.
     */
    public static void editSessionDetails(Scanner scanner) {
        scanner.nextLine(); // Consume newline character
        clearScreen();
        System.out.println("--- Edit Session Details ---");
        sessionManager.displaySessionsForGroup(currentUser.getGroup());
        System.out.println("Enter the title of the session you want to edit details for (or type 'cancel' to return to the menu):");
        String titleChoice = scanner.nextLine();
        if (titleChoice.equalsIgnoreCase("cancel")) {
            scanner.nextLine(); // Consume newline character
            return;
        }

        // Search for the session with the specified title and group
        Session sessionToEdit = null;
        for (Session session : sessionManager.getSessions()) {
            if (session.getTitle().equalsIgnoreCase(titleChoice) && session.getGroup().getName().equals(currentUser.getGroup().getName())) {
                sessionToEdit = session;
                break;
            }
        }

        // If session is found, ask for new details and perform editing
        if (sessionToEdit != null) {
            System.out.println("Enter new title:");
            String newTitle = scanner.nextLine();
            System.out.println("Enter new date:");
            String newDate = scanner.nextLine();
            System.out.println("Enter new description:");
            String newDescription = scanner.nextLine();

            // Perform editing
            sessionToEdit.setTitle(newTitle);
            sessionToEdit.setDate(newDate);
            sessionToEdit.setDescription(newDescription);
            SessionManager.writeSessionsToFile(sessionManager.getSessions());
            System.out.println("Session details edited successfully!");
        } else {
            System.out.println("Session not found or you don't have access to edit this session.");
        }

        // After editing session details, return to main menu
        scanner.nextLine(); // Consume newline character
        return;
    }

    /**
     * Displays the resource sharing menu and handles user choices.
     * Allows users to share resources or view existing resources.
     * @param scanner Scanner object for user input
     */
    public static void resourceSharingMenu(Scanner scanner) {
        clearScreen();
        System.out.println("--- Resource Sharing Menu ---");
        System.out.println("1. Share Resource");
        System.out.println("2. View Resources");
        System.out.println("3. Edit Resources");
        System.out.println("4. Back to Main Menu");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
            	if(currentUser.getGroup()!= null)
            	{
                    shareResource(scanner);
            	}
            	else {
            	System.out.println("You dont have a group");
            	resourceSharingMenu(scanner);
            	}
                break;
            case 2:
            	if(currentUser.getGroup()!= null)
            	{
                    viewResourceDetails(scanner);
            	}
            	else {
            	System.out.println("You dont have a group");
            	resourceSharingMenu(scanner);
            	}
                break;
            case 3:
            	if(currentUser.getGroup()!= null)
            	{
            		editResourceDetails(scanner);
            	}
            	else {
            	System.out.println("You dont have a group");
            	resourceSharingMenu(scanner);
            	}
            	break;
            case 4:
                mainMenu(scanner);
                break;
        }
    }

    /**
     * Allows the user to share a resource by providing title, description, and link.
     * Adds the shared resource to the system and saves it to file.
     * @param scanner Scanner object for user input
     */
    public static void shareResource(Scanner scanner) {
        clearScreen();
        System.out.println("--- Share Resource ---");
        // Implement resource sharing logic here
        System.out.println("Enter resource title:");
        String title = scanner.next();
        System.out.println("Enter resource description:");
        String description = scanner.next();
        System.out.println("Enter resource link:");
        String link = scanner.next();
        Resource resource = new Resource(title, description, link, currentUser.getGroup());
        resources.add(resource);
        ResourceManager.writeResourcesToFile(resources);
        // After sharing resource, return to resource sharing menu
        resourceSharingMenu(scanner);
    }

    /**
     * Allows the user to view details of a specific resource.
     * Displays the title, description, and link of the resource if found.
     * @param scanner Scanner object for user input
     */
    public static void viewResourceDetails(Scanner scanner) {
        scanner.nextLine();
        clearScreen();
        System.out.println("--- View Resource Details ---");
        resourceManager.displayResourcesForGroup(currentUser.getGroup());
        System.out.println("Enter the title of the resource you want to view details for (or type 'cancel' to return to the menu):");
        String titleChoice = scanner.nextLine();
        if (titleChoice.equalsIgnoreCase("cancel")) {
            resourceSharingMenu(scanner); // Return to resource sharing menu if user cancels
            return;
        }

        // Search for the resource with the specified title and group
        Resource resourceToShow = null;
        for (Resource resource : resourceManager.getResources()) {
            if (resource.getTitle().equalsIgnoreCase(titleChoice) && resource.getGroup().getName().equals(currentUser.getGroup().getName())) {
                resourceToShow = resource;
                break;
            }
        }

        // If resource is found, display its details
        if (resourceToShow != null) {
            System.out.println("Title: " + resourceToShow.getTitle());
            System.out.println("Description: " + resourceToShow.getDescription());
            System.out.println("Link: " + resourceToShow.getLink());
        } else {
            System.out.println("Resource not found or you don't have access to view this resource.");
        }

        // After viewing resource details, return to resource sharing menu
        scanner.nextLine(); // Consume newline character
        return;
    }
    /**
     * @brief Allows the user to edit details of a resource.
     * 
     * This method displays a list of resources for the current user's group and
     * prompts the user to choose a resource to edit. It then allows the user to
     * edit the resource's title, link, and description.
     * 
     * @param scanner The Scanner object used for input.
     */
    public static void editResourceDetails(Scanner scanner) {
        scanner.nextLine(); // Consume newline character
        clearScreen();
        System.out.println("--- Edit Resource Details ---");
        resourceManager.displayResourcesForGroup(currentUser.getGroup());
        System.out.println("Enter the title of the resource you want to edit details for (or type 'cancel' to return to the menu):");
        String titleChoice = scanner.nextLine();
        if (titleChoice.equalsIgnoreCase("cancel")) {
            return;
        }

        // Search for the resource with the specified title and group
        Resource resourceToEdit = null;
        for (Resource resource : resourceManager.getResources()) {
            if (resource.getTitle().equalsIgnoreCase(titleChoice) && resource.getGroup().getName().equals(currentUser.getGroup().getName())) {
                resourceToEdit = resource;
                break;
            }
        }

        // If resource is found, ask for new details and perform editing
        if (resourceToEdit != null) {
            System.out.println("Enter new title:");
            String newTitle = scanner.nextLine();
            System.out.println("Enter new date:");
            String newDescription = scanner.nextLine();
            System.out.println("Enter new description:");
            String newLink = scanner.nextLine();

            // Perform editing
            resourceToEdit.setTitle(newTitle);
            resourceToEdit.setDescription(newDescription);
            resourceToEdit.setLink(newLink);
            ResourceManager.writeResourcesToFile(resourceManager.getResources());
            System.out.println("Resource details edited successfully!");
        } else {
            System.out.println("Resource not found or you don't have access to edit this resource.");
        }

        // After editing resource details, return to main menu
        scanner.nextLine(); // Consume newline character
        return;
    }
    
    /**
     * Displays the discussion board menu and handles user choices.
     * Allows users to create a new discussion or view existing discussions.
     * @param scanner Scanner object for user input
     */
    public static void discussionBoardMenu(Scanner scanner) {
        clearScreen();
        System.out.println("--- Discussion Board Menu ---");
        System.out.println("1. Create Discussion");
        System.out.println("2. View Discussions");
        System.out.println("3. Edit Discussion");
        System.out.println("4. Back to Main Menu");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
            	if(currentUser.getGroup()!= null)
            	{
                    createDiscussion(scanner);
            	}
            	else {
            	System.out.println("You dont have a group");
            	discussionBoardMenu(scanner);
            	}
                break;
            case 2:
            	if(currentUser.getGroup()!= null)
            	{
            		viewDiscussions(scanner);
            	}
            	else {
            	System.out.println("You dont have a group");
            	discussionBoardMenu(scanner);
            	}
                
                break;
            case 3:
            	if(currentUser.getGroup()!= null)
            	{
            		editDiscussionDetails(scanner);
            	}
            	else {
            	System.out.println("You dont have a group");
            	discussionBoardMenu(scanner);
            	}
            	break;
            case 4:
                mainMenu(scanner);
                break;
        }
    }

    /**
     * Allows the user to create a new discussion by providing a topic.
     * Adds the created discussion to the system and saves it to file.
     * @param scanner Scanner object for user input
     */
    public static void createDiscussion(Scanner scanner) {
        clearScreen();
        System.out.println("--- Create Discussion ---");
        // Implement discussion creation logic here
        System.out.println("Enter discussion topic:");
        String topic = scanner.next();
        Discussion discussion = new Discussion(topic, currentUser.getGroup());
        //discussionManager.addDiscussion(discussion);
        System.out.println("Discussion created successfully!");
        discussions.add(discussion);
        DiscussionManager.writeDiscussionsToFile(discussions);
        // After creating discussion, return to discussion board menu
        discussionBoardMenu(scanner);
    }

    /**
     * Allows the user to view details of discussions and add comments to them.
     * @param scanner Scanner object for user input
     */
    public static void viewDiscussions(Scanner scanner) {
        clearScreen();
        System.out.println("--- View Discussions ---");
        // Implement logic to view discussions here
        discussionManager.displayDiscussionsForGroup(currentUser.getGroup()); // Display discussions for the current user's group

        System.out.println("Enter the topic of the discussion you want to comment on (or type 'cancel' to return to the menu):");
        String topicChoice = scanner.nextLine();
        topicChoice = scanner.nextLine();
        if (topicChoice.equalsIgnoreCase("cancel")) {
            discussionBoardMenu(scanner); // Return to discussion board menu if user cancels
            return;
        }

        // Search for the discussion with the specified topic
        Discussion discussionToComment = null;
        for (Discussion discussion : discussionManager.getDiscussions()) {
            if (discussion.getTopic().equalsIgnoreCase(topicChoice)) {
                discussionToComment = discussion;
                break;
            }
        }

        // If discussion is found, prompt for comment and add it
        if (discussionToComment != null) {
            System.out.println("Enter your comment:");
            String comment = scanner.nextLine();
            discussionToComment.addComment(comment);
            discussionManager.writeDiscussionsToFile(discussions);
            System.out.println("Comment added successfully!");
        } else {
            System.out.println("Discussion not found.");
        }

        // After viewing discussions, return to discussion board menu
        discussionBoardMenu(scanner);
    }
    
    /**
     * @brief Allows the user to edit details of a discussion.
     * 
     * This method displays the discussions for the current user's group and prompts the user to choose a discussion to edit.
     * Users can edit the topic of the discussion if they have permission.
     * 
     * @param scanner The Scanner object used for input.
     */
    public static void editDiscussionDetails(Scanner scanner) {
        scanner.nextLine(); // Consume newline character
        clearScreen();
        System.out.println("--- Edit Discussion Details ---");
        discussionManager.displayDiscussionsForGroup(currentUser.getGroup());
        System.out.println("Enter the title of the discussion you want to edit details for (or type 'cancel' to return to the menu):");
        String titleChoice = scanner.nextLine();
        if (titleChoice.equalsIgnoreCase("cancel")) {
            return;
        }

        // Search for the discussion with the specified title and group
        Discussion discussionToEdit = null;
        for (Discussion discussion : discussionManager.getDiscussions()) {
            if (discussion.getTopic().equalsIgnoreCase(titleChoice) && discussion.getGroup().getName().equals(currentUser.getGroup().getName())) {
                discussionToEdit = discussion;
                break;
            }
        }

        // If discussion is found, ask for new details and perform editing
        if (discussionToEdit != null) {
            System.out.println("Enter new topic:");
            String newTopic = scanner.nextLine();

            // Perform editing
            discussionToEdit.setTopic(newTopic);
            DiscussionManager.writeDiscussionsToFile(discussionManager.getDiscussions());
            System.out.println("Discussion topic edited successfully!");
        } else {
            System.out.println("Discussion not found or you don't have access to edit this discussion.");
        }

        // After editing resource details, return to main menu
        scanner.nextLine(); // Consume newline character
    }
    
    /**
     * Main load point of the CoordinatorApp program.
     * Initializes the application by loading data and starting the main menu.
     * 
     */
    public static void loadAll(Scanner scanner) {
        // Load initial data from files
        account.loadUsersFromFile("users.bin");
        discussions = discussionManager.readDiscussionsFromFile();
        resources = resourceManager.readResourcesFromFile();
        sessions = sessionManager.readSessionsFromFile();

        // Add loaded data to respective managers
        if(discussions != null) {
        for (Discussion discussion1 : discussions) {
            discussionManager.addDiscussion(discussion1);
        }
        }
        if(resources != null) {
        for (Resource resource : resources) {
            resourceManager.addResource(resource);
        }
        }
        if(sessions != null) {
        for (Session session : sessions) {
            sessionManager.addSession(session);
        }
        }

        // Update lists with the added data
        discussions = discussionManager.getDiscussions();
        resources = resourceManager.getResources();
        sessions = sessionManager.getSessions();

        // Perform user login and start the main menu
        currentUser = account.loginMenu(scanner);
        mainMenu(scanner);
    }

    /**
     * Main entry point of the CoordinatorApp program.
     * Initializes the application by loading data and starting the main menu.
     * 
     * @param args Command line arguments (not used)
     */
    @SuppressWarnings("static-access")
    public static void main(String[] args) {
    	loadAll(scanner);
    }
}
