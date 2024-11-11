package assignmentevaluator;

import java.util.ArrayList;
import java.util.List;

import assignmentevaluator.feedback.TestFeedback;

/**
 * Represents feedback for a student's assignment.
 * Contains information such as student details, expected files, and test results.
 */
public class AssignmentFeedBack {

    private List<TestFeedback> testResults = new ArrayList<>();
    private List<String> expectedFiles = new ArrayList<>();

    private String studentID;
    private String studentName;
    private String assignmentTitle;

    // Expected Files

    /**
     * Adds a filename to the list of expected files for the assignment.
     * 
     * @param filename the name of the expected file
     */
    public void addFileToExpected(String filename) {
        expectedFiles.add(filename);
    }

    /**
     * Removes a filename from the list of expected files for the assignment.
     * 
     * @param filename the name of the file to remove
     */
    public void removeFileFromExpected(String filename) {
        expectedFiles.remove(filename);
    }

    /**
     * Returns the list of expected files for the assignment.
     * 
     * @return a list of filenames that are expected in the assignment
     */
    public List<String> getExpectedFiles() {
        return expectedFiles;
    }

    // Getters

    /**
     * Returns the student's ID.
     * 
     * @return the student ID
     */
    public String getStudentID() {
        return studentID;
    }

    /**
     * Returns the student's name.
     * 
     * @return the student name
     */
    public String getStudentName() {
        return studentName;
    }

    /**
     * Returns the assignment title.
     * 
     * @return the assignment title
     */
    public String getAssignmentTitle() {
        return assignmentTitle;
    }

    /**
     * Returns the list of test results for the assignment.
     * 
     * @return a list of test feedback objects
     */
    public List<TestFeedback> getTestResults() {
        return testResults;
    }

    // Setters and adder

    /**
     * Sets the student's ID.
     * 
     * @param studentID the student ID to set
     */
    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    /**
     * Sets the student's name.
     * 
     * @param studentName the student name to set
     */
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    /**
     * Sets the assignment title.
     * 
     * @param assignmentTitle the assignment title to set
     */
    public void setAssignmentTitle(String assignmentTitle) {
        this.assignmentTitle = assignmentTitle;
    }

    /**
     * Adds a test result to the list of test results for the assignment.
     * 
     * @param result the test feedback object to add
     */
    public void addTestResults(TestFeedback result) {
        testResults.add(result);
    }
}
