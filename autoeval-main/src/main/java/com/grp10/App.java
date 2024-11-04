package com.grp10;

import filehandler.FileHandler;

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
            "\\Unzipping\\extraction_test\\extraction_src";



        fileHandler.extractAssignments(directoryPath);


        //Area for user creation of Tests to Evaluate Assignments

    }
}
