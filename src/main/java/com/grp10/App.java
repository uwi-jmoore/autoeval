package com.grp10;

import assignmentevaluator.AssignmentEvaluator;
import filehandler.FileHandler;
import assignmenttests.programlevel.AssignmentRun;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import static filehandler.filehelperservice.FileOperationHelpers.pathToFile;


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


//        testingRun();

        //

    }
    public static void testingRun(){
        AssignmentRun runTest = new AssignmentRun();
        File assignmentDirectory = pathToFile("C:\\Users\\Chimera\\Desktop\\Project_Testbed\\Unzipping\\" +
            "extraction_test\\Assignments-main_assign\\Assignment_1");
        File mainClass = pathToFile("C:\\Users\\Chimera\\Desktop\\Project_Testbed\\Unzipping\\extraction_test" +
            "\\Assignments-main_assign\\Assignment_1\\ChatBotSimulation.java");

        runTest.setAssignmentDirectory(assignmentDirectory);

        runTest.setMainFile(mainClass);

        System.out.println(runTest.evaluateProgramLevelTest());
    }
}
