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

/**
 * The SessionManager class handles the management of sessions in the system.
 */
public class SessionManager {
    /** The file path for sessions serialization. */
    public static final String SESSIONS_FILE = "Sessions.bin";
    
    /** The list of sessions managed by this manager. */
    private List<Session> sessions;

    /** The file object representing the sessions file. */
    private static File file = new File(SESSIONS_FILE);

    /**
     * Constructs a new SessionManager object with an empty list of sessions.
     */
    public SessionManager() {
        this.sessions = new ArrayList<>();
    }

    /**
     * Adds a session to the list of sessions.
     *
     * @param session The session to be added.
     */
    public void addSession(Session session) {
        sessions.add(session);
    }

    /**
     * Retrieves the list of sessions.
     *
     * @return The list of sessions.
     */
    public List<Session> getSessions() {
        return sessions;
    }

    /**
     * Displays sessions for a specific study group.
     *
     * @param group The study group for which sessions will be displayed.
     */
    public void displaySessionsForGroup(StudyGroup group) {
        System.out.println("Sessions for group " + group.getName() + ":");
        boolean foundSessions = false;
        for (Session session : sessions) {
            if (session.getGroup().getName().equals(group.getName())) {
                foundSessions = true;
                System.out.println("Title: " + session.getTitle());
            }
        }
        if (!foundSessions) {
            System.out.println("Your group doesn't have any session.");
        }
    }

    /**
     * Writes sessions to a file.
     *
     * @param sessions The list of sessions to be written to the file.
     */
    public static void writeSessionsToFile(List<Session> sessions) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
            outputStream.writeObject(sessions);
            outputStream.close();
        } catch (Exception ignored) {
        }
    }

    /**
     * Reads sessions from a file.
     *
     * @return The list of sessions read from the file.
     */
    public static List<Session> readSessionsFromFile() {
        if (!file.exists()) {
            System.out.println("Sessions file does not exist.");
            return new ArrayList<>();
        }

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file))) {
            @SuppressWarnings("unchecked")
            List<Session> sessions = (List<Session>) inputStream.readObject();
            System.out.println("Sessions have been loaded from " + SESSIONS_FILE);
            return sessions;
        } catch (Exception ignored) {
            return new ArrayList<>();
        }
    }
}

