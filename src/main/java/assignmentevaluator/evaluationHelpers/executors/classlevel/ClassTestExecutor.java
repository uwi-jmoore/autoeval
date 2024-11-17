package assignmentevaluator.evaluationHelpers.executors.classlevel;

import java.io.File;
import java.util.Map;

import org.junit.platform.launcher.listeners.TestExecutionSummary;

import assignmentevaluator.evaluationHelpers.executors.TestExecutor;
import assignmentevaluator.feedback.types.ConcreteTestFeedback;
import assignmenttests.classlevel.ClassTest;

/**
 * An abstract executor class responsible for evaluating a class-level test case.
 * This class defines the structure and shared functionality required for running
 * tests on a specific class file and handling the results.
 */
public abstract class ClassTestExecutor extends TestExecutor {

    /**
     * The class file being evaluated.
     */
    protected File evaluatingClassFile;

    /**
     * The specific class-level test that will be executed on the student's class.
     */
    protected ClassTest test;

    /**
     * A map containing setup details for the test case.
     */
    protected Map<String, Object> testSetupDetailMap;

    /**
     * Constructs a {@code ClassTestExecutor} and initializes feedback with a concrete implementation.
     */
    public ClassTestExecutor() {
        testFeedback = new ConcreteTestFeedback();
    }

    /**
     * Sets the file being evaluated.
     *
     * @param evaluatingClassFile the file to be tested.
     */
    public void setEvaluatingFile(File evaluatingClassFile) {
        this.evaluatingClassFile = evaluatingClassFile;
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
    public void setTestSetupDetailMap(Map<String, Object> testSetupDetailMap) {
        this.testSetupDetailMap = testSetupDetailMap;
    }

    /**
     * Handles the scenario where all tests pass successfully.
     * Sets the appropriate marks and feedback message to indicate success.
     */
    protected void handleAllPass() {
        testFeedback.setMarks(marks);
        testFeedback.setFeedbackMsg("All Tests Passed");
    }

    /**
     * Handles test failures by assigning a zero mark and generating feedback
     * that includes detailed information about the failed tests and their reasons.
     *
     * @param testReturn the summary of the executed test.
     */
    protected void handleFailure(TestExecutionSummary testReturn) {
        testFeedback.setMarks(0);
        StringBuilder failureMsg = new StringBuilder("These tests failed: ");
        TestExecutionSummary.Failure[] failures = testReturn.getFailures().toArray(new TestExecutionSummary.Failure[0]);
        for (TestExecutionSummary.Failure failure : failures) {
            failureMsg.append("Test: ")
                .append(failure.getTestIdentifier().getDisplayName())
                .append(" failed. Reason: ")
                .append(failure.getException().getMessage())
                .append("\t");
        }
        testFeedback.setFeedbackMsg(String.valueOf(failureMsg));
    }
}

