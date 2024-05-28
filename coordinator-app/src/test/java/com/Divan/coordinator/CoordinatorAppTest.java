package com.Divan.coordinator;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.*;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class CoordinatorAppTest {

    @Before
    public void setUp() {
        // Clean up any existing files before each test
        clearFiles();
        
        // Initialize account and add a user for testing
        Account account = new Account();
        User testUser = new User("Test", "User", 1234, "testuser", "password");
        account.getUserList().add(testUser);
        StudyGroup testGroup = new StudyGroup("TestGroup", "TestDescription");
        testUser.setGroup(testGroup);
        
        // Set current user for testing
        
        CoordinatorApp.currentUser = testUser;
    }

    @After
    public void cleanup() {
        // Delete the files after the test
        clearFiles();
    }
    
    public static void clearFiles() {
        File studyGroupsFile = new File("Studygroups.bin");
        File usersFile = new File("users.bin");
        File discussionsFile = new File("Discussions.bin");
        File sessionsFile = new File("Sessions.bin");
        File invalidDataFormatFile = new File("invalid_data_format.bin");
        File resourcesFile = new File("Resources.bin");
        File testUsersFile = new File("test_users.bin");

        deleteIfExists(studyGroupsFile);
        deleteIfExists(usersFile);
        deleteIfExists(discussionsFile);
        deleteIfExists(sessionsFile);
        deleteIfExists(invalidDataFormatFile);
        deleteIfExists(resourcesFile);
        deleteIfExists(testUsersFile);
    }

    private static void deleteIfExists(File file) {
        if (file.exists()) {
            file.delete();
        }
    }
    
    @Test
    public void testMainMenuDisplay() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String input = "1\n3\n2\n4\n3\n4\n4\n4\n6\na\n-1\n\n5\n"; // Exiting the program
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        
        CoordinatorApp.mainMenu(scanner);
        
        String menuOutput = outputStream.toString();

        assertTrue(menuOutput.contains("--- Main Menu ---"));
        assertTrue(menuOutput.contains("1. Group Management"));
        assertTrue(menuOutput.contains("2. Session Scheduling"));
        assertTrue(menuOutput.contains("3. Resource Sharing"));
        assertTrue(menuOutput.contains("4. Discussion Board"));
        assertTrue(menuOutput.contains("5. Exit"));
    }

    @Test
    public void testGroupManagementMenuDisplay() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String input = "3\n6\n5\n"; // Back to Menu
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        
        CoordinatorApp.groupManagementMenu(scanner);
        
        String menuOutput = outputStream.toString();

        assertTrue(menuOutput.contains("--- Group Management Menu ---"));
        assertTrue(menuOutput.contains("1. Create Study Group"));
        assertTrue(menuOutput.contains("2. View Study Group"));
        assertTrue(menuOutput.contains("3. Back to Menu"));
    }
    
    @Test
    public void testGroupCreatingCreateAgain() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String input = "1\ngroup\ndescription\n1234\n\nexit\n1\ngroup2\ngroupdes2\n1234\nexit\n2\n3\n5\n"; // Back to Menu
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        
        CoordinatorApp.groupManagementMenu(scanner);
        
        List<StudyGroup> loadedGroups = FileUtility.readGroupsFromFile();
        
        assertTrue(loadedGroups.size() > 1);
    }
    
    @Test
    public void testGroupCreating() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String input = "1\ngroup\ndescription\n1234\nexit\n2\n3\n5\n"; // Back to Menu
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        
        CoordinatorApp.groupManagementMenu(scanner);
        
        String menuOutput = outputStream.toString();
        
        List<StudyGroup> loadedGroups = FileUtility.readGroupsFromFile();
        
        assertTrue(loadedGroups != null);
    }
    
    @Test
    public void testGroupCreatingUserAlreadyInGroup() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String input = "1\ngroup\ndescription\n1234\n1234\nexit\n2\n3\n5\n"; // Back to Menu
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        
        CoordinatorApp.groupManagementMenu(scanner);
        
        String menuOutput = outputStream.toString();
        
        assertTrue(menuOutput.contains("User with ID 1234 is already in the group."));
    }
    
    @Test
    public void testGroupCreatingUserNotFound() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String input = "1\ngroup\ndescription\n1234\n12345\nexit\n2\n3\n5\n"; // Back to Menu
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        
        CoordinatorApp.groupManagementMenu(scanner);
        
        String menuOutput = outputStream.toString();
          
        assertTrue(menuOutput.contains("User with ID 12345 not found."));
    }
    
    @Test
    public void testGroupCreatingInvalidUser() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String input = "1\ngroup\ndescription\n1234\na\nexit\n2\n3\n5\n"; // Back to Menu
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        
        CoordinatorApp.groupManagementMenu(scanner);
        
        String menuOutput = outputStream.toString();
 
        assertTrue(menuOutput.contains("Invalid user ID format: a"));
    }
    
    @Test
    public void testGroupViewing() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String input = "1\ngroup\ndescription\n1234\nexit\n2\n2\n0\n2\n\n3\n5\n"; // Back to Menu
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        
        CoordinatorApp.groupManagementMenu(scanner);
        
        String menuOutput = outputStream.toString();
        
        assertTrue(menuOutput.contains("---- Study Groups ----"));
    }
    
    @Test
    public void testGroupViewingDetails() {
    	ViewGroups view = new ViewGroups();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String input = "1\ngroup\ndescription\n1234\nexit\n2\n2\n1\nreturn\n3\n5\n"; // Back to Menu
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        
        CoordinatorApp.groupManagementMenu(scanner);
        
        String menuOutput = outputStream.toString();
        
        assertTrue(menuOutput.contains("---- Study Groups ----"));
    }
    
    @Test
    public void testGroupViewingDetailsInvalid() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String input = "1\ngroup\ndescription\n1234\nexit\n2\n2\n-1\n1\nreturn\n3\n5\n"; // Back to Menu
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        
        CoordinatorApp.groupManagementMenu(scanner);
        
        String menuOutput = outputStream.toString();
        
        assertTrue(menuOutput.contains("---- Study Groups ----"));
    }
    
    @Test
    public void testGroupViewingDetailsInvalid2() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String input = "1\ngroup\ndescription\n1234\nexit\n2\n2\n8\n1\nreturn\n3\n5\n"; // Back to Menu
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        
        CoordinatorApp.groupManagementMenu(scanner);
        
        String menuOutput = outputStream.toString();
        
        assertTrue(menuOutput.contains("---- Study Groups ----"));
    }
    
    @Test
    public void testGroupViewingDetailsInvalid3() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String input = "1\ngroup\ndescription\n1234\nexit\n2\n2\n0\n3\n5\n"; // Back to Menu
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        
        CoordinatorApp.groupManagementMenu(scanner);
        
        String menuOutput = outputStream.toString();
        
        assertTrue(menuOutput.contains("---- Study Groups ----"));
    }
    
    @Test
    public void testGroupViewingNoGroupsAvailable() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String input = "2\n0\n2\n\n3\n5\n"; // Back to Menu
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        
        CoordinatorApp.groupManagementMenu(scanner);
        
        String menuOutput = outputStream.toString();
        
        assertTrue(menuOutput.contains("No study groups found."));
    }

    @Test
    public void testSessionSchedulingMenuDisplay() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String input = "5\n4\n6\n5\n"; // Back to Menu
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        
        CoordinatorApp.sessionSchedulingMenu(scanner);
        
        String menuOutput = outputStream.toString();

        assertTrue(menuOutput.contains("--- Session Scheduling Menu ---"));
        assertTrue(menuOutput.contains("1. Schedule Session"));
        assertTrue(menuOutput.contains("2. View Sessions"));
        assertTrue(menuOutput.contains("3. Edit Sessions"));
        assertTrue(menuOutput.contains("4. Back to Main Menu"));
    }
    
    @Test
    public void testEditSessionSchedulingMenuDisplay() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String input = "3\ncancel\n\n5\n"; // Back to Menu
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        
        CoordinatorApp.sessionSchedulingMenu(scanner);
        
        String menuOutput = outputStream.toString();

        assertTrue(menuOutput.contains("--- Session Scheduling Menu ---"));
        assertTrue(menuOutput.contains("1. Schedule Session"));
        assertTrue(menuOutput.contains("2. View Sessions"));
        assertTrue(menuOutput.contains("3. Edit Sessions"));
        assertTrue(menuOutput.contains("4. Back to Main Menu"));
    }
    
    @Test
    public void testViewSessionSchedulingMenuDisplay() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String input = "2\ncancel\n4\n5\n"; // Back to Menu
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        
        CoordinatorApp.sessionSchedulingMenu(scanner);
        
        String menuOutput = outputStream.toString();

        assertTrue(menuOutput.contains("--- View Session Details ---"));
    }
    
    @Test
    public void testSessionScheduling() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String input = "\n1\nTestSession\n2024-03-28\nDescription\n4\n5\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        
        CoordinatorApp.sessionSchedulingMenu(scanner);
        
        List<Session> loadedSessions = SessionManager.readSessionsFromFile();
        
        assertTrue(2 >= loadedSessions.size());
        assertEquals("TestSession", loadedSessions.get(0).getTitle());
        assertEquals("2024-03-28", loadedSessions.get(0).getDate());
        assertEquals("Description", loadedSessions.get(0).getDescription());
    }
    
    @Test
    public void sessionView_SessionFound() {
        // Arrange
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        

        // Creating a session and adding it to the list of sessions
        Session session = new Session("TestSession", "2024-03-28", new StudyGroup("TestGroup", "TestDescription"), "Description");
        CoordinatorApp.sessionManager.addSession(session);

        // Setting up user input
        String input = "\nTestSession\n\n4\n5\n"; // Input to select the session and then go back to the menu
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        // Act
        CoordinatorApp.viewSessions(scanner);
        String menuOutput = outputStream.toString();

        // Assert
        assertTrue(menuOutput.contains("Title: TestSession"));
        assertTrue(menuOutput.contains("Date: 2024-03-28"));
        assertTrue(menuOutput.contains("Description: Description"));
    }

    @Test
    public void sessionView_SessionNotFound() {
        // Arrange
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Setting up user input
        String input = "\nNonExistentSession\n\n4\n5\n"; // Input to select the session and then go back to the menu
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        // Act
        CoordinatorApp.viewSessions(scanner);
        String menuOutput = outputStream.toString();

        // Assert
        assertTrue(menuOutput.contains("Session not found or you don't have access to view this session."));
    }
    
    @Test
    public void testEditSessionDetails_SessionFound() {
        // Arrange
        SessionManager sessionManager = new SessionManager();
        CoordinatorApp.sessionManager = sessionManager;

        // Creating a session and adding it to the list of sessions
        Session session = new Session("TestSession", "2024-03-28", new StudyGroup("TestGroup", "TestDescription"), "Description");
        sessionManager.addSession(session);

        // Setting up user input
        String input = "\nTestSession\nNewTestSession\n2024-03-29\nNewDescription\n4\n5\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        // Act
        CoordinatorApp.editSessionDetails(scanner);

        // Assert
        assertEquals("NewTestSession", session.getTitle());
        assertEquals("2024-03-29", session.getDate());
        assertEquals("NewDescription", session.getDescription());
    }

    @Test
    public void testEditSessionDetails_SessionNotFound() {
        // Arrange
        SessionManager sessionManager = new SessionManager();
        CoordinatorApp.sessionManager = sessionManager;
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Setting up user input
        String input = "\nNonExistentSession\n4\n5\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        // Act
        CoordinatorApp.editSessionDetails(scanner);
        String menuOutput = outputStream.toString();

        // Assert
        assertTrue(menuOutput.contains("Session not found or you don't have access to edit this session."));
    }
    
    @Test
    public void testEditSessionDetails_Cancel() {
        // Arrange
        SessionManager sessionManager = new SessionManager();
        CoordinatorApp.sessionManager = sessionManager;
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Setting up user input
        String input = "cancel\n4\n5\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        // Act
        CoordinatorApp.editSessionDetails(scanner);
        String menuOutput = outputStream.toString();

        // Assert
        assertTrue(menuOutput.contains("Session not found or you don't have access to edit this session."));
    }
    
    @Test
    public void testResourceSharingMenuDisplay() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String input = "5\n4\n6\n5\n"; // Back to Menu
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        
        CoordinatorApp.resourceSharingMenu(scanner);
        
        String menuOutput = outputStream.toString();

        assertTrue(menuOutput.contains("--- Resource Sharing Menu ---"));
        assertTrue(menuOutput.contains("1. Share Resource"));
        assertTrue(menuOutput.contains("2. View Resources"));
        assertTrue(menuOutput.contains("3. Edit Resource"));
        assertTrue(menuOutput.contains("4. Back to Main Menu"));
    }
    
    @Test
    public void testEditResourceMenuDisplay() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String input = "3\ncancel\n5\n"; // Back to Menu
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        
        CoordinatorApp.resourceSharingMenu(scanner);
        
        String menuOutput = outputStream.toString();

        assertTrue(menuOutput.contains("Enter the title"));
    }
    
    @Test
    public void testViewResourceSharingMenuDisplay() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String input = "2\ncancel\n4\n6\n5\n"; // Back to Menu
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        
        CoordinatorApp.resourceSharingMenu(scanner);
        
        String menuOutput = outputStream.toString();

        assertTrue(menuOutput.contains("--- Resource Sharing Menu ---"));
        assertTrue(menuOutput.contains("1. Share Resource"));
        assertTrue(menuOutput.contains("2. View Resources"));
        assertTrue(menuOutput.contains("3. Edit Resources"));
        assertTrue(menuOutput.contains("4. Back to Main Menu"));
    }
    
    @Test
    public void testResourceSharing() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String input = "1\nTestResource\nTest\nhttp://testresource.com\n4\n5\n"; // Back to Menu
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        
        CoordinatorApp.resourceSharingMenu(scanner);
        
        List<Resource> loadedResources = ResourceManager.readResourcesFromFile();
        
        assertEquals(1, loadedResources.size());
        assertEquals("TestResource", loadedResources.get(0).getTitle());
        assertEquals("Test", loadedResources.get(0).getDescription());
        assertEquals("http://testresource.com", loadedResources.get(0).getLink());
    }
    
    @Test
    public void resourceView_ResourceFound() {
        // Arrange
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        
        // Creating a resource and adding it to the list of resources
        Resource resource = new Resource("TestResource", "Description", "http://testresource.com", new StudyGroup("TestGroup", "TestDescription"));
        CoordinatorApp.resourceManager.addResource(resource);
        
        // Setting up user input
        String input = "\nTestResource\n\n4\n5\n"; // Input to select the resource and then go back to the menu
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        
        // Act
        CoordinatorApp.viewResourceDetails(scanner);
        String menuOutput = outputStream.toString();
        
        // Assert
        assertTrue(menuOutput.contains("Title: TestResource"));
        assertTrue(menuOutput.contains("Description: Description"));
        assertTrue(menuOutput.contains("Link: http://testresource.com"));
    }

    @Test
    public void resourceView_ResourceNotFound() {
        // Arrange
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Setting up user input
        String input = "\nNonExistentResource\n\n4\n5\n"; // Input to select the resource and then go back to the menu
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        // Act
        CoordinatorApp.viewResourceDetails(scanner);
        String menuOutput = outputStream.toString();

        // Assert
        assertTrue(menuOutput.contains("Resource not found"));
    }
    
    @Test
    public void testDiscussionMenuDisplay() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String input = "5\n4\n6\n5\n"; // Back to Menu
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        
        CoordinatorApp.discussionBoardMenu(scanner);
        
        String menuOutput = outputStream.toString();

        assertTrue(menuOutput.contains("--- Discussion Board Menu ---"));
        assertTrue(menuOutput.contains("1. Create Discussion"));
        assertTrue(menuOutput.contains("2. View Discussions"));
        assertTrue(menuOutput.contains("3. Edit Discussion"));
        assertTrue(menuOutput.contains("4. Back to Main Menu"));
    }
    
    @Test
    public void testEditDiscussionMenuDisplay() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String input = "3\ncancel\n6\n5\n"; // Back to Menu
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        
        CoordinatorApp.discussionBoardMenu(scanner);
        
        String menuOutput = outputStream.toString();

        assertTrue(menuOutput.contains("Enter the title"));
    }
    
    @Test
    public void testEditResourceDetails_ResourceFound() {
        // Arrange
        ResourceManager resourceManager = new ResourceManager();
        CoordinatorApp.resourceManager = resourceManager;

        // Creating a resource and adding it to the list of resources
        StudyGroup testGroup = new StudyGroup("TestGroup", "TestDescription");
        Resource resource = new Resource("TestResource", "Description", "http://testresource.com", testGroup);
        resourceManager.addResource(resource);

        // Setting up user input
        String input = "\nTestResource\nNewTestResource\nNewType\nNewDescription\n\n4\n5\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        // Act
        CoordinatorApp.editResourceDetails(scanner);

        // Assert
        assertEquals("NewTestResource", resource.getTitle());
    }
    
    @Test
    public void testEditResourceDetails_Cancel() {
        // Arrange
        ResourceManager resourceManager = new ResourceManager();
        CoordinatorApp.resourceManager = resourceManager;

        // Creating a resource and adding it to the list of resources
        StudyGroup testGroup = new StudyGroup("TestGroup", "TestDescription");
        Resource resource = new Resource("TestResource", "Description", "http://testresource.com", testGroup);
        resourceManager.addResource(resource);

        // Setting up user input
        String input = "\ncancel\n\n5\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        // Act
        CoordinatorApp.editResourceDetails(scanner);

        // Assert
        assertEquals("TestResource", resource.getTitle());
    }

    @Test
    public void testEditResourceDetails_ResourceNotFound() {
        // Arrange
        ResourceManager resourceManager = new ResourceManager();
        CoordinatorApp.resourceManager = resourceManager;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Setting up user input
        String input = "\nNonExistentResource\n\n4\n5\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        // Act
        CoordinatorApp.editResourceDetails(scanner);
        String menuOutput = outputStream.toString();

        // Assert
        assertTrue(menuOutput.contains("Resource not found"));
    }
    
    @Test
    public void testViewDiscussionMenuDisplay() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String input = "2\ncancel\n4\n6\n5\n"; // Back to Menu
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        
        CoordinatorApp.discussionBoardMenu(scanner);
        
        String menuOutput = outputStream.toString();

        assertTrue(menuOutput.contains("--- Discussion Board Menu ---"));
        assertTrue(menuOutput.contains("1. Create Discussion"));
        assertTrue(menuOutput.contains("2. View Discussions"));
        assertTrue(menuOutput.contains("3. Edit Discussion"));
        assertTrue(menuOutput.contains("4. Back to Main Menu"));
    }

    @Test
    public void testDiscussionCreation() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String input = "1\nTestDiscussion\n4\n5\n"; // Back to Menu
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        
        CoordinatorApp.discussionBoardMenu(scanner);
        
        List<Discussion> loadedDiscussions = DiscussionManager.readDiscussionsFromFile();
        
        assertEquals("TestDiscussion", loadedDiscussions.get(0).getTopic());
    }
    
    @Test
    public void testDiscussionComment() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String input = "1\nTestDiscussion\n2\nTestDiscussion\nComment\n2\ncancel\n4\n5\n"; // Back to Menu
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        
        CoordinatorApp.discussionBoardMenu(scanner);
        
        List<Discussion> loadedDiscussions = DiscussionManager.readDiscussionsFromFile();
        
        assertEquals("TestDiscussion", loadedDiscussions.get(0).getTopic());
    }
    
    @Test
    public void discussionView_DiscussionFound() {
        // Arrange
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        
        // Creating a discussion and adding it to the list of discussions
        Discussion discussion = new Discussion("TestDiscussion", new StudyGroup("TestGroup", "TestDescription"));
        CoordinatorApp.discussionManager.addDiscussion(discussion);
        
        // Setting up user input
        String input = "\nTestDiscussion\n\n4\n5\n"; // Input to select the discussion and then go back to the menu
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        
        // Act
        CoordinatorApp.viewDiscussions(scanner);
        String menuOutput = outputStream.toString();
        
        // Assert
        assertTrue(menuOutput.contains("Topic: TestDiscussion"));
        assertTrue(menuOutput.contains("Comments:"));
        assertTrue(menuOutput.contains("Enter the topic of the discussion you want to comment on (or type 'cancel' to return to the menu):"));
    }

    @Test
    public void discussionView_DiscussionNotFound() {
        // Arrange
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Setting up user input
        String input = "\nNonExistentDiscussion\n\n4\n5\n"; // Input to select the discussion and then go back to the menu
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        // Act
        CoordinatorApp.viewDiscussions(scanner);
        String menuOutput = outputStream.toString();

        // Assert
        assertTrue(menuOutput.contains("Discussion not found"));
    }
    
    @Test
    public void testEditDiscussionDetails_DiscussionFound() {
        // Arrange
        DiscussionManager discussionManager = new DiscussionManager();
        CoordinatorApp.discussionManager = discussionManager;

        // Creating a discussion and adding it to the list of discussions
        StudyGroup testGroup = new StudyGroup("TestGroup", "TestDescription");
        Discussion discussion = new Discussion("TestDiscussion", testGroup);
        discussionManager.addDiscussion(discussion);

        // Setting up user input
        String input = "\nTestDiscussion\nNewTestDiscussion\nNewTopic\nNewDescription\n\n4\n5\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        // Act
        CoordinatorApp.editDiscussionDetails(scanner);

        // Assert
        assertEquals("NewTestDiscussion", discussion.getTopic());
    }
    
    @Test
    public void testEditDiscussionDetails_Cancel() {
        // Arrange
        DiscussionManager discussionManager = new DiscussionManager();
        CoordinatorApp.discussionManager = discussionManager;

        // Creating a discussion and adding it to the list of discussions
        StudyGroup testGroup = new StudyGroup("TestGroup", "TestDescription");
        Discussion discussion = new Discussion("TestDiscussion", testGroup);
        discussionManager.addDiscussion(discussion);

        // Setting up user input
        String input = "\ncancel\n\n4\n5\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        // Act
        CoordinatorApp.editDiscussionDetails(scanner);

        // Assert
        assertEquals("TestDiscussion", discussion.getTopic());
    }

    @Test
    public void testEditDiscussionDetails_DiscussionNotFound() {
        // Arrange
        DiscussionManager discussionManager = new DiscussionManager();
        CoordinatorApp.discussionManager = discussionManager;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Setting up user input
        String input = "\nNonExistentDiscussion\n\n4\n5\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        // Act
        CoordinatorApp.editDiscussionDetails(scanner);
        String menuOutput = outputStream.toString();

        // Assert
        assertTrue(menuOutput.contains("Discussion not found"));
    }

    @Test
    public void testSaveAndLoadUsersToFile() {
        Account account = new Account();

        // Add a user to the list
        User user = new User("Test", "User", 1234, "testuser", "password");
        account.getUserList().clear();
        account.getUserList().add(user);

        // Save users to file
        String fileName = "test_users.bin";
        account.saveUsersToFile(fileName);

        // Load users from file
        account.getUserList().clear();
        account.loadUsersFromFile(fileName);

        // Check if loaded users match the original list
        List<User> loadedUsers = account.getUserList();
        assertEquals(1, loadedUsers.size());

        User loadedUser = loadedUsers.get(0);
        assertEquals(user.getName(), loadedUser.getName());
        assertEquals(user.getSurname(), loadedUser.getSurname());
        assertEquals(user.getId(), loadedUser.getId());
        assertEquals(user.getUsername(), loadedUser.getUsername());
        assertEquals(user.getPassword(), loadedUser.getPassword());

        // Clean up - delete test file
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void loadAllTestEmpty() {
    	
        // Arrange
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Setting up user input
        String input = "1\n2\n3\n4\n5\n6\n2\n5\n6\n5\n"; // Input to select the discussion and then go back to the menu
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        // Act
    	CoordinatorApp.loadAll(scanner);
        String menuOutput = outputStream.toString();

        // Assert
        assertTrue(menuOutput.contains("Exit"));
    }
    
    @Test
    public void loadAllTest() {
    	
        ArrayList<Discussion> discussions = new ArrayList<>();
        ArrayList<Resource> resources = new ArrayList<>();
        ArrayList<Session> sessions = new ArrayList<>();

        discussions.add(new Discussion("Topic", new StudyGroup("Discussions","des")));
        resources.add(new Resource("Res", "des", "link", new StudyGroup("Resources","des")));
        sessions.add(new Session("Title", "Date", new StudyGroup("Session","des"), "Description"));

        DiscussionManager.writeDiscussionsToFile(discussions);
        ResourceManager.writeResourcesToFile(resources);
        SessionManager.writeSessionsToFile(sessions);
    	
        // Arrange
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Setting up user input
        String input = "1\n2\n3\n4\n5\n6\n2\n5\n6\n5\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        // Act
    	CoordinatorApp.loadAll(scanner);
        String menuOutput = outputStream.toString();

        // Assert
        clearFiles();
        assertTrue(menuOutput.contains("Exit"));
    }

}
