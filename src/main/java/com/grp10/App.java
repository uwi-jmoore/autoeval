package com.grp10;

import java.io.IOException;

public final class App {
    public static void main(String[] args) throws IOException {
        String directoryPath = "C:\\Users\\felix\\Downloads\\Project Src\\ASSIGNMENT 1\\AssignmentTarget";

        // Create an instance of ApplicationExecutor
        ApplicationExecutor applicationExecutor = new ApplicationExecutor();

        try {
            // Call the execute method, passing the directory path
            applicationExecutor.execute(directoryPath);
        } catch (IOException e) {
            // Handle any potential IOExceptions
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}
