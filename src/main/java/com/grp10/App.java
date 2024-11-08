package com.grp10;

import assignmentevaluator.AssignmentEvaluator;
import filehandler.FileHandler;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;



public final class App {


    public static void main(String[] args) throws IOException {
        Scanner userInput = new Scanner(System.in);
        System.out.println("What is the path to the Student Assignments? ");

        //for user input
        String directoryPath = userInput.nextLine();

        FileHandler fileHandler = new FileHandler();

        //for testing
        directoryPath = "C:\\Users\\Chimera\\Desktop\\Project_Testbed" +
            "\\Unzipping\\extraction_test\\main_assign";


        File extractionTarget = fileHandler.extractAssignments(directoryPath);

        AssignmentEvaluator evaluator = new AssignmentEvaluator();
        evaluator.setStudentAssignmentDirectory(extractionTarget);
        evaluator.evaluateAssignments();


        //

    }
}
