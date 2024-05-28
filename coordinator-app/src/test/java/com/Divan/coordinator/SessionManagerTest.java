package com.Divan.coordinator;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SessionManagerTest {
	
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
    public void testReadSessionsFromFileNotFound() {
        // Given
        SessionManager sessionManager = new SessionManager();
        
        // When
        List<Session> sessions = sessionManager.readSessionsFromFile();
        
        // Then
        assertTrue(sessions.isEmpty());
    }

    @Test
    public void testReadSessionsFromFileError() {
        // Given
        SessionManager sessionManager = new SessionManager();
        
        // When
        // Attempt to read sessions from a file that causes an error (e.g., invalid format)
        List<Session> sessions = sessionManager.readSessionsFromFile();
        
        // Then
        assertTrue(sessions.isEmpty());
    }

    @Test
    public void testWriteSessionsToFileError() {
        // Given
        SessionManager sessionManager = new SessionManager();
        List<Session> sessions = new ArrayList<>();
        sessions.add(new Session("Title","date", new StudyGroup("Group","des"),"des"));
        
        // When
        // Attempt to write sessions to a file that causes an error (e.g., file permission issue)
        sessionManager.writeSessionsToFile(sessions);
        
    }

    @Test
    public void testDisplaySessionsForGroupNoSession() {
        // Given
        SessionManager sessionManager = new SessionManager();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // When
        sessionManager.displaySessionsForGroup(new StudyGroup("No Sessions","des"));

        // Then
        assertTrue(outContent.toString().contains("Your group doesn't have any session."));
        System.setOut(System.out); // Reset System.out
    }
}
