package com.Divan.coordinator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DiscussionManagerTest {
	
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
    public void testReadDiscussionsFromFileNotFound() {
        // Given
        DiscussionManager discussionManager = new DiscussionManager();
        
        // When
        List<Discussion> discussions = discussionManager.readDiscussionsFromFile();
        
        // Then
        assertTrue(discussions.isEmpty());
    }

    @Test
    public void testReadDiscussionsFromFileError() {
        // Given
        DiscussionManager discussionManager = new DiscussionManager();
        
        // When
        // Attempt to read discussions from a file that causes an error (e.g., invalid format)
        List<Discussion> discussions = discussionManager.readDiscussionsFromFile();
        
        // Then
        assertTrue(discussions.isEmpty());
    }

    @Test
    public void testWriteDiscussionsToFileError() {
        // Given
        DiscussionManager discussionManager = new DiscussionManager();
        List<Discussion> discussions = new ArrayList<>();
        discussions.add(new Discussion("Title", new StudyGroup("Group","des")));
        
        // When
        // Attempt to write discussions to a file that causes an error (e.g., file permission issue)
        discussionManager.writeDiscussionsToFile(discussions);
        
    }

    @Test
    public void testDisplayDiscussionsForGroupNoDiscussion() {
        // Given
        DiscussionManager discussionManager = new DiscussionManager();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // When
        discussionManager.displayDiscussionsForGroup(new StudyGroup("No Discussions","des"));

        // Then
        assertTrue(outContent.toString().contains("Discussions for group No Discussions:"));
        System.setOut(System.out); // Reset System.out
    }
    
    @Test
    public void testDiscussionForCurrentGroup() {
        // Given
        DiscussionManager discussionManager = new DiscussionManager();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        StudyGroup studyGroup = new StudyGroup("Discussions","des");
        
        Discussion discussion = new Discussion("Topic", studyGroup);
        discussionManager.addDiscussion(discussion);
        
        // When
        discussionManager.displayDiscussionsForGroup(studyGroup);

        // Then
        assertTrue(outContent.toString().contains("Topic: "));
        System.setOut(System.out); // Reset System.out
    }
    
    @Test
    public void testNoDiscussionForCurrentGroup() {
        // Given
        DiscussionManager discussionManager = new DiscussionManager();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Discussion discussion = new Discussion("Topic", new StudyGroup("Discussions","des"));
        discussionManager.addDiscussion(discussion);
        
        // When
        discussionManager.displayDiscussionsForGroup(new StudyGroup("No Discussions","des"));

        // Then
        assertTrue(outContent.toString().contains("Your group doesn't have any discussions"));
        System.setOut(System.out); // Reset System.out
    }
}
