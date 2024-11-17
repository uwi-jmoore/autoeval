package assignmentevaluator.evaluationHelpers.executors.classlevel;

import org.junit.platform.launcher.listeners.TestExecutionSummary;

import assignmentevaluator.feedback.types.ConcreteTestFeedback;

/**
 * Executes a single test case for a student's class-level assignment and generates feedback based on the test results.
 * This class evaluates a specified class file by running a single test case and assigns marks based on the outcome.
 */
public class SingleCaseClassTestExecutor extends ClassTestExecutor {

    /**
     * Default constructor that initializes the test feedback object.
     */
    public SingleCaseClassTestExecutor() {
        testFeedback = new ConcreteTestFeedback();
    }

    /**
     * Executes the assigned single test case on the evaluated file.
     * The method configures the test environment, runs the test, and generates feedback 
     * that indicates whether the test passed or failed.
     */
    public void executeTest() {
        // Set the test type and initialize test feedback
        testType = test.toString();
        testFeedback.setTestType(testType);

        // Set the file path for the class to be evaluated
        test.setClassFilePath(evaluatingClassFile.getAbsolutePath());

        // Set up the test details with the provided configuration map
        test.setUpTestDetails(testSetupDetailMap);

        // Execute the test and retrieve the summary of results
        TestExecutionSummary testReturn = test.executeTest(test.getClass()).getSummary();

        // Determine if the test passed or failed, and handle feedback accordingly
        if (testReturn.getTestsFailedCount() == 0 && testReturn.getContainersFailedCount() == 0) {
            handleAllPass();
        } else {
            handleFailure(testReturn);
        }

        // Add the feedback from this test to the overall assignment feedback
        assignmentFeedBack.addTestResults(testFeedback);
    }
}
