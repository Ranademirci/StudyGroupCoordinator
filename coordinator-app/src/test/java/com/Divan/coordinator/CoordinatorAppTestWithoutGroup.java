package com.Divan.coordinator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CoordinatorAppTestWithoutGroup {
	
    @Before
    public void setUp() {
        // Clean up any existing files before each test
        CoordinatorAppTest.clearFiles();
        
        // Initialize account and add a user for testing
        Account account = new Account();
        User testUser = new User("Test", "User", 1234, "testuser", "password");
        account.getUserList().add(testUser);
        
        // Set current user for testing
        
        CoordinatorApp.currentUser = testUser;
    }
    
    @After
    public void cleanup() {
        // Delete the files after the test
        CoordinatorAppTest.clearFiles();
    }
	
    @Test
    public void testSessionSchedulingWithoutGroup() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String input = "1\n4\n5\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        
        CoordinatorApp.sessionSchedulingMenu(scanner);
        
        String menuOutput = outputStream.toString();
        
        assertTrue(menuOutput.contains("You dont have a group"));
        
        
    }
    
    @Test
    public void testSessionViewWithoutGroup() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String input = "2\n4\n5\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        
        CoordinatorApp.sessionSchedulingMenu(scanner);
        
        String menuOutput = outputStream.toString();
        
        assertTrue(menuOutput.contains("You dont have a group"));
        
        
    }
    
    @Test
    public void testSessionEditWithoutGroup() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String input = "3\n4\n5\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        
        CoordinatorApp.sessionSchedulingMenu(scanner);
        
        String menuOutput = outputStream.toString();
        
        assertTrue(menuOutput.contains("You dont have a group"));
        
        
    }
    
    @Test
    public void testResourceSharingWithoutGroup() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String input = "1\n4\n5\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        
        CoordinatorApp.resourceSharingMenu(scanner);
        
        String menuOutput = outputStream.toString();
        
        assertTrue(menuOutput.contains("You dont have a group"));
        
    }
    @Test
    public void testResourceViewWithoutGroup() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String input = "2\n4\n5\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        
        CoordinatorApp.resourceSharingMenu(scanner);
        
        String menuOutput = outputStream.toString();
        
        assertTrue(menuOutput.contains("You dont have a group"));
        
    }
    
    @Test
    public void testResourceEditWithoutGroup() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String input = "3\n4\n5\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        
        CoordinatorApp.resourceSharingMenu(scanner);
        
        String menuOutput = outputStream.toString();
        
        assertTrue(menuOutput.contains("You dont have a group"));
        
    }
    @Test
    public void testDiscussionCreatingWithoutGroup() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String input = "1\n4\n5\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        
        CoordinatorApp.discussionBoardMenu(scanner);
        
        String menuOutput = outputStream.toString();
        
        assertTrue(menuOutput.contains("You dont have a group"));
        
    }

    @Test
    public void testDiscussionViewWithoutGroup() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String input = "2\n4\n5\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        
        CoordinatorApp.discussionBoardMenu(scanner);
        
        String menuOutput = outputStream.toString();
        
        assertTrue(menuOutput.contains("You dont have a group"));
        
    }
    @Test
    public void testDiscussionEditWithoutGroup() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String input = "3\n4\n5\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        
        CoordinatorApp.discussionBoardMenu(scanner);
        
        String menuOutput = outputStream.toString();
        
        assertTrue(menuOutput.contains("You dont have a group"));
        
    }
}
