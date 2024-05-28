package com.Divan.coordinator;

import org.junit.*;

import java.io.*;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.*;

public class AccountTest {
    private static final String FILE_PATH = "test_users.bin";

    @Before
    public void setup() {
        // Delete the files after the test
        CoordinatorAppTest.clearFiles();
    }
    
    @After
    public void cleanup() {
        // Delete the files after the test
        CoordinatorAppTest.clearFiles();
    }
    

    @Test
    public void testSaveAndLoadUsers() {
        // Create test users
        User user1 = new User("John", "Doe", 1, "john.doe", "password");
        User user2 = new User("Jane", "Smith", 2, "jane.smith", "password123");

        Account account = new Account();
        List<User> userList = account.getUserList();

        // Add users to the list
        userList.add(user1);
        userList.add(user2);

        // Save users to file
        account.saveUsersToFile(FILE_PATH);

        // Load users from file
        account.loadUsersFromFile(FILE_PATH);

        // Retrieve loaded users
        List<User> loadedUsers = account.getUserList();

        // Check if loaded users match the original users
        assertEquals(userList.size(), loadedUsers.size());
        for (int i = 0; i < userList.size(); i++) {
            User originalUser = userList.get(i);
            User loadedUser = loadedUsers.get(i);
            assertEquals(originalUser.getName(), loadedUser.getName());
            assertEquals(originalUser.getSurname(), loadedUser.getSurname());
            assertEquals(originalUser.getId(), loadedUser.getId());
            assertEquals(originalUser.getUsername(), loadedUser.getUsername());
            assertEquals(originalUser.getPassword(), loadedUser.getPassword());
        }
    }

    @Test
    public void testRegister() {
        // Set up scanner with mocked input
        Scanner scanner = new Scanner("\nJohn\nDoe\n1\njohn.doe\npassword\n");
        Account account = new Account();

        // Perform registration
        account.register(scanner);

        // Check if the user is registered
        List<User> userList = account.getUserList();
        assertEquals(1, userList.size());
        User registeredUser = userList.get(0);
        assertEquals("John", registeredUser.getName());
        assertEquals("Doe", registeredUser.getSurname());
        assertEquals(1, registeredUser.getId());
        assertEquals("john.doe", registeredUser.getUsername());
        assertEquals("password", registeredUser.getPassword());
    }
    
    @Test
    public void testRegisterNotUnique() {
        // Set up scanner with mocked input
        Scanner scanner = new Scanner("\nJohn\nDoe\n1\njohn\npassword\n");
        Scanner scanner2 = new Scanner("\nJohn\nDone\n1\n2\njohn\njohn.done\npassword\n");
        Account account = new Account();

        // Perform registration
        account.register(scanner);
        
        // Redirect standard output to check printed message
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Attempt to register another user with the same ID and username
        account.register(scanner2);
        // Reset standard output
        System.setOut(System.out);

        assertTrue(outContent.toString().contains("ID already exists. Please choose another one."));
        assertTrue(outContent.toString().contains("Username already exists. Please choose another one."));
        assertTrue(outContent.toString().contains("Registration successful!"));
        
    }

    @Test
    public void testLogin() {
        // Set up scanner with mocked input
        Scanner scanner = new Scanner("\njohn.doe\npassword\n");
        Account account = new Account();

        // Register a user
        account.register(new Scanner("\nJohn\nDoe\n1\njohn.doe\npassword\n"));

        // Perform login
        User loggedInUser = account.login(scanner);

        // Check if login is successful
        assertNotNull(loggedInUser);
        assertEquals("john.doe", loggedInUser.getUsername());
        assertEquals("password", loggedInUser.getPassword());
    }
    
    @Test
    public void testLoginReturnNull() {
        // Set up scanner with mocked input
        Scanner scanner = new Scanner("\njohn.doe\nwrongpassword\n");
        Account account = new Account();

        // Register a user
        account.register(new Scanner("\nJohn\nDoe\n1\njohn.doe\npassword\n"));

        // Perform login
        User loggedInUser = account.login(scanner);

        // Check if login is successful
        assertNull(loggedInUser);
    }

    @Test
    public void testLoginMenu() {
        // Set up scanner with mocked input
        Scanner scanner = new Scanner("1\nJohn\nDoe\n1\njohn.doe\npassword\n2\njohn.doe\npassword\n");
        Account account = new Account();

        // Perform login through menu
        User loggedInUser = account.loginMenu(scanner);

        // Check if login through menu is successful
        assertNotNull(loggedInUser);
        assertEquals("john.doe", loggedInUser.getUsername());
        assertEquals("password", loggedInUser.getPassword());
    }
    
    @Test
    public void testLoginMenuWrongCredentials() {
        // Set up scanner with mocked input
        Scanner scanner = new Scanner("1\nJohn\nDoe\n1\njohn.doe\npassword\n2\njohn.dooe\npassword\n2\njohn.doe\npassword\n");
        Account account = new Account();
        
        // Redirect standard output to check printed usernames and IDs
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        
        // Perform login through menu
        User loggedInUser = account.loginMenu(scanner);
        
        // Reset standard output
        System.setOut(System.out);

        // Check if login through menu is unsuccessful
        assertTrue(outContent.toString().contains("Login failed. Please try again."));

    }

    @Test
    public void testDisplayUsernamesWithIDs() {
        // Set up scanner with mocked input
        Scanner scanner = new Scanner(System.in);
        Account account = new Account();

        // Register a user
        account.register(new Scanner("\nJohn\nDoe\n1\njohn.doe\npassword\n"));

        // Redirect standard output to check printed usernames and IDs
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Display usernames with IDs
        account.displayUsernamesWithIDs();

        // Reset standard output
        System.setOut(System.out);

        assertTrue(outContent.toString().contains("User IDs and Usernames"));
        assertTrue(outContent.toString().contains("ID: 1 - Username: john.doe"));
    }

    @Test
    public void testFindUserById() {
        // Set up scanner with mocked input
        Scanner scanner = new Scanner(System.in);
        Account account = new Account();

        // Register a user
        account.register(new Scanner("\nJohn\nDoe\n1\njohn.doe\npassword\n"));

        // Find user by ID
        User foundUser = account.findUserById(1);

        // Check if the user is found
        assertNotNull(foundUser);
        assertEquals("john.doe", foundUser.getUsername());
    }
    
    @Test
    public void testFindUserByIdReturnNull() {
        // Set up scanner with mocked input
        Scanner scanner = new Scanner(System.in);
        Account account = new Account();

        // Register a user
        account.register(new Scanner("\nJohn\nDoe\n1\njohn.doe\npassword\n"));

        // Find user by ID
        User foundUser = account.findUserById(2);

        // Check if the user is found
        assertNull(foundUser);
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

        // Test additional lines
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(outContent));
        
        // Create a non-existing file
        account.loadUsersFromFile("non_existing_file.bin");
        assertTrue(outContent.toString().contains("Error: File not found - non_existing_file.bin"));
        
        // Create a file with invalid data format
        File invalidDataFile = new File("invalid_data_format.bin");
        try (FileOutputStream fos = new FileOutputStream(invalidDataFile);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject("Invalid Data");
        } catch (Exception Ignored) {
        }
        account.loadUsersFromFile("invalid_data_format.bin");
        assertTrue(outContent.toString().contains("Error: Invalid data format in file - invalid_data_format.bin"));
        
        // Create a file in a non-existing directory
        account.saveUsersToFile("non_existing_directory/test_users.bin");
        assertTrue(outContent.toString().contains("Error"));
        
        System.setErr(System.err); // Reset System.err

        outContent.reset(); // Reset the content
    }
    
}
