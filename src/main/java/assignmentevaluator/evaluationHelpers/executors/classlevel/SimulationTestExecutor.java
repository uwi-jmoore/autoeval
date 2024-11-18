package assignmentevaluator.evaluationHelpers.executors.classlevel;

import org.junit.platform.launcher.listeners.TestExecutionSummary;

/**
 * Executes simulation-based tests for a student's class-level assignment.
 * This class handles the execution of a single simulation test on the specified 
 * class file and provides feedback based on the test results.
 */
public class SimulationTestExecutor extends ClassTestExecutor {

    /**
     * Default constructor that initializes the base properties from the superclass.
     */
    public SimulationTestExecutor() {
        super();
    }

    /**
     * Executes the assigned simulation test and generates feedback based on the results.
     * The test evaluates a specific simulation scenario against the provided class file.
     */
    @Override
    public void executeTest() {
        // Set the test type and initialize test feedback
        testType = test.toString();
        testFeedback.setTestType(testType);

        // Configure the test with the evaluated class file path and setup details
        test.setClassFilePath(evaluatingClassFile.getAbsolutePath());
        test.setUpTestDetails(testSetupDetailMap);

        // Execute the test and get the summary of results
        TestExecutionSummary testReturn = test.executeTest(test.getClass()).getSummary();

        // Determine success or failure and handle feedback accordingly
        if (testReturn.getTestsFailedCount() == 0 && testReturn.getContainersFailedCount() == 0) {
            handleAllPass();
        } else {
            handleFailure(testReturn);
        }

        // Add the generated feedback to the assignment's feedback report
        assignmentFeedBack.addTestResults(testFeedback);
    }
}
