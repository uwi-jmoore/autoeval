package com.grp10;

import java.io.IOException;
import java.util.Scanner;

public final class App {
    public static void main(String[] args) throws IOException {
        String directoryPath;

        // Creating a Scanner object to read user input
        Scanner userInput = new Scanner(System.in);
        System.out.println("What is the path to the Student Assignments? ");

        // Prompt user for the directory path where student assignments are located
        directoryPath = userInput.nextLine();

        // Hardcoded directory path for the sake of testing
        directoryPath =  "C:\\Users\\Chimera\\Desktop\\Project_Testbed\\Unzipping\\extraction_test\\main_assign";

        // Create an instance of ApplicationExecutor
        ApplicationExecutor applicationExecutor = new ApplicationExecutor();

        try {
            // Call the executeTest method, passing the directory path
            applicationExecutor.execute(directoryPath);
        } catch (IOException e) {
            // Handle any potential IOExceptions
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}
