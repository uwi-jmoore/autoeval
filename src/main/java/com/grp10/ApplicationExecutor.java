package com.grp10;

import assignmentevaluator.AssignmentEvaluator;
import filehandler.FileHandler;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * The ApplicationExecutor class is responsible for managing the execution flow of extracting
 * student assignments and evaluating them.
 * <p>
 * It interacts with the user to get the directory path of student assignments and processes
 * the files in that directory for evaluation.
 */
public class ApplicationExecutor {

    /**
     * Executes the assignment extraction and evaluation process.
     * <p>
     * This method prompts the user for the directory path containing the student assignments,
     * extracts the assignments from the specified directory, and then evaluates them using
     * the AssignmentEvaluator.
     *
     * @param directoryPath The directory path where student assignments are located.
     *                      If a value is provided by the user, it will override this parameter.
     * @throws IOException If there is an issue with file handling or directory access.
     */
    public void execute(String directoryPath) throws IOException {
        // Creating a Scanner object to read user input
        Scanner userInput = new Scanner(System.in);
        System.out.println("What is the path to the Student Assignments? ");

        // Prompt user for the directory path where student assignments are located
        directoryPath = userInput.nextLine();

        // Create an instance of FileHandler to manage the extraction of assignments
        FileHandler fileHandler = new FileHandler();

        // For testing purposes, we override the user input with a hardcoded path
        // directoryPath = "C:\\Users\\felix\\Downloads\\Project Src\\ASSIGNMENT 1\\AssignmentTarget";

        // Extract the assignments from the provided directory path
        File extractionTarget = fileHandler.extractAssignments(directoryPath);

        // Create an instance of AssignmentEvaluator to evaluate the extracted assignments
        AssignmentEvaluator evaluator = new AssignmentEvaluator();
        evaluator.setStudentAssignmentDirectory(extractionTarget);

        // Evaluate the assignments
        evaluator.evaluateAssignments();
    }
}
