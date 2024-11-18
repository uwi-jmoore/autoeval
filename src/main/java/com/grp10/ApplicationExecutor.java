package com.grp10;

import assignmentevaluator.AssignmentEvaluator;
import assignmentevaluator.AssignmentFeedBack;
import assignmentevaluator.feedback.TestFeedback;
import filehandler.FileHandler;
import pdfgeneration.PdfReportGenerator;
import pdfgeneration.ReportGeneratorInterface;

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
        // Create an instance of FileHandler to manage the extraction of assignments
        FileHandler fileHandler = new FileHandler();

        // Extract the assignments from the provided directory path
        File extractionTarget = fileHandler.extractAssignments(directoryPath);

        // Create an instance of AssignmentEvaluator to evaluate the extracted assignments
        AssignmentEvaluator evaluator = new AssignmentEvaluator();
        evaluator.setStudentAssignmentDirectory(extractionTarget);

        // Evaluate the assignments
        evaluator.evaluateAssignments();

        //print reports
        for(AssignmentFeedBack assignmentFeedBack : evaluator.getAllStudentFeedBack()){
            ReportGeneratorInterface reportGenerator = new PdfReportGenerator();
            int totalstudentpoints = assignmentFeedBack.getTestResults()
                .stream()
                .mapToInt(TestFeedback::getTestMarks).sum();
            String pdfname = "Assignment Report "+assignmentFeedBack.getStudentID()+".pdf";
            reportGenerator.generateReport(assignmentFeedBack.getTestResults(),totalstudentpoints,pdfname,assignmentFeedBack);
        }



    }
}
