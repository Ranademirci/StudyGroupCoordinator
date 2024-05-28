package com.Divan.coordinator;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ResourceManagerTest {
	
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
    public void testReadResourcesFromFileNotFound() {
        // Given
        ResourceManager resourceManager = new ResourceManager();
        
        // When
        List<Resource> resources = resourceManager.readResourcesFromFile();
        
        // Then
        assertTrue(resources.isEmpty());
    }

    @Test
    public void testReadResourcesFromFileError() {
        // Given
        ResourceManager resourceManager = new ResourceManager();
        
        // When
        // Attempt to read resources from a file that causes an error (e.g., invalid format)
        List<Resource> resources = resourceManager.readResourcesFromFile();
        
        // Then
        assertTrue(resources.isEmpty());
    }

    @Test
    public void testWriteResourcesToFileError() {
        // Given
        ResourceManager resourceManager = new ResourceManager();
        List<Resource> resources = new ArrayList<>();
        resources.add(new Resource("Title","des","link", new StudyGroup("Group","des")));
        
        // When
        // Attempt to write resources to a file that causes an error (e.g., file permission issue)
        resourceManager.writeResourcesToFile(resources);
        
    }

    @Test
    public void testDisplayResourcesForGroupNoResource() {
        // Given
        ResourceManager resourceManager = new ResourceManager();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // When
        resourceManager.displayResourcesForGroup(new StudyGroup("No Resources","des"));

        // Then
        assertTrue(outContent.toString().contains("Your group doesn't have any resources."));
        System.setOut(System.out); // Reset System.out
    }
}
