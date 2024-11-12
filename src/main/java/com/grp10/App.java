package com.grp10;

import java.io.IOException;
import java.util.Scanner;

public final class App {
    public static void main(String[] args) throws IOException {
        // Hardcoded directory path for the sake of testing
        String directoryPath = "C:\\Users\\felix\\Downloads\\Project src\\ASSIGNMENT 1\\AssignmentTarget";

        // Creating a Scanner object to read user input
        Scanner userInput = new Scanner(System.in);
        System.out.println("What is the path to the Student Assignments? ");

        // Prompt user for the directory path where student assignments are located
        directoryPath = userInput.nextLine();

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
