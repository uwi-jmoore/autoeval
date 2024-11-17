package assignmentevaluator.evaluationHelpers;

import java.io.File;
import java.util.Map;

import org.junit.platform.launcher.listeners.TestExecutionSummary;
import org.junit.platform.launcher.listeners.TestExecutionSummary.Failure;

import assignmentevaluator.AssignmentFeedBack;
import static assignmentevaluator.evaluationHelpers.EvalHelpers.getTestReturn;
import assignmentevaluator.feedback.types.ConcreteTestFeedback;
import assignmenttests.classlevel.ClassTest;

/**
 * Executes test cases for a student's assignment and provides feedback based on the test results.
 * This class evaluates a given class file by running specified tests and generates feedback on 
 * whether the tests passed or failed, and assigns marks accordingly.
 */
public class ClassTestExecutor {

    /** The feedback object that keeps track of the student's results. */
    private AssignmentFeedBack assignmentFeedBack;

    /** The class file being evaluated. */
    private File evalutatingFile;

    /** The marks the student will receive on passing the test case(s). */
    private int marks;

    /** The specific test that will be executed on the student's class. */
    private ClassTest test;

    /** Stores feedback for the specific test case. */
    private ConcreteTestFeedback testFeedback;

    /** A map containing setup details for the test case. */
    private Map<String, String> testSetupDetailMap;

    /** Type of test being executed. */
    private String testType;

    /**
     * Default constructor that initializes the test feedback.
     */
    public ClassTestExecutor() {
        testFeedback = new ConcreteTestFeedback();
    }

    /**
     * Sets the assignment feedback object to store results.
     * 
     * @param assignmentFeedBack the feedback object that stores test results.
     */
    public void setAssignmentFeedBack(AssignmentFeedBack assignmentFeedBack) {
        this.assignmentFeedBack = assignmentFeedBack;
    }

    /**
     * Gets the assignment feedback containing the results.
     * 
     * @return the assignment feedback.
     */
    public AssignmentFeedBack getAssignmentFeedBack() {
        return assignmentFeedBack;
    }

    /**
     * Sets the file being evaluated.
     * 
     * @param evalutatingFile the file to be tested.
     */
    public void setEvaluatingFile(File evalutatingFile) {
        this.evalutatingFile = evalutatingFile;
    }

    /**
     * Sets the marks for passing the test cases.
     * 
     * @param marks the score awarded upon passing the tests.
     */
    public void setMarks(int marks) {
        this.marks = marks;
    }

    /**
     * Sets the test case to be executed.
     * 
     * @param test the test case for evaluation.
     */
    public void setClassTest(ClassTest test) {
        this.test = test;
    }

    /**
     * Sets up the details for the test using a map of configuration values.
     * 
     * @param testSetupDetailMap a map of details to configure the test.
     */
    public void setTestSetupDetailMap(Map<String, String> testSetupDetailMap) {
        this.testSetupDetailMap = testSetupDetailMap;
    }

    /**
     * Executes the assigned test case on the evaluated file and provides feedback based on the results.
     */
    public void execute() {
        testType = test.toString();
        testFeedback.setTestType(testType);
        test.setClassFilePath(evalutatingFile.getAbsolutePath());
        test.setUpTestDetails(testSetupDetailMap);
        TestExecutionSummary testReturn = getTestReturn(test.getClass()).getSummary();
        if (testReturn.getTestsFailedCount() == 0 && testReturn.getContainersFailedCount() == 0) {
            handleAllPass(testReturn);
        } else {
            handleFailure(testReturn);
        }
        assignmentFeedBack.addTestResults(testFeedback);
    }

    /**
     * Handles the case when all tests pass by setting appropriate marks and feedback message.
     * 
     * @param testReturn the summary of the executed test.
     */
    private void handleAllPass(TestExecutionSummary testReturn) {
        testFeedback.setMarks(marks);
        testFeedback.setFeedbackMsg("All Tests Passed");
    }

    /**
     * Handles test failures by setting a zero mark and constructing a feedback message
     * detailing which tests failed and the reasons for failure.
     * 
     * @param testReturn the summary of the executed test.
     */
    private void handleFailure(TestExecutionSummary testReturn) {
        testFeedback.setMarks(0);
        StringBuilder failureMsg = new StringBuilder("These tests failed: ");
        Failure[] failures = testReturn.getFailures().toArray(new Failure[0]);
        for (Failure failure : failures) {
            failureMsg.append("Test: ")
                      .append(failure.getTestIdentifier().getDisplayName())
                      .append(" failed. Reason: ")
                      .append(failure.getException().getMessage())
                      .append("\t");
        }
        testFeedback.setFeedbackMsg(String.valueOf(failureMsg));
    }
}
