package assignmenttests.programlevel;


import java.io.File;


/**
 * Abstract class representing an operational test on an assignment.
 * Provides the structure for evaluating program-level tests and assigning marks.
 */
public abstract class AssignmentOperational{
    private File mainFile;
    private File assignmentDirectory;
    private int marks;

    /**
     * Evaluates the program-level test on the assignment.
     *
     * @return true if the test passes, false otherwise
     */
    public abstract boolean evaluateProgramLevelTest();

    /**
     * Retrieves the marks awarded for the assignment based on test evaluation.
     *
     * @return the assigned marks if the test passes, or 0 if it fails
     */
    public int getMarks() {
        return evaluateProgramLevelTest() ? marks : 0;
    }

    /**
     * Sets the main file for the assignment test.
     *
     * @param mainFile the main file to be tested
     */
    public void setMainFile(File mainFile) {
        this.mainFile = mainFile;
    }

    /**
     * Retrieves the assignment directory containing all relevant files.
     *
     * @return the directory of the assignment
     */
    public File getAssignmentDirectory() {
        return assignmentDirectory;
    }

    /**
     * Sets the directory where the assignment files are located.
     *
     * @param assignmentDirectory the directory containing the assignment files
     */
    public void setAssignmentDirectory(File assignmentDirectory) {
        this.assignmentDirectory = assignmentDirectory;
    }

    /**
     * Retrieves the main file for this assignment.
     *
     * @return the main file of the assignment
     */
    public File getMainFile() {
        return mainFile;
    }

    /**
     * Sets the number of marks assigned for the test if it passes.
     *
     * @param marks the marks to be awarded upon passing the test
     */
    public void setMarks(int marks) {
        this.marks = marks;
    }


}
