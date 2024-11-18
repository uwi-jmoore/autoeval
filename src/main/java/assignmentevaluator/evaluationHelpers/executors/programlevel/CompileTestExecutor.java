package assignmentevaluator.evaluationHelpers.executors.programlevel;

import org.junit.platform.launcher.listeners.TestExecutionSummary;

import assignmentevaluator.feedback.types.ConcreteTestFeedback;

/**
 * This class is responsible for executing the compilation test for a program. It evaluates whether
 * the program compiles successfully and generates feedback based on the result.
 */
public class CompileTestExecutor extends ProgramTestExecutor {

    /**
     * Constructs a new CompileTestExecutor instance, initializing the test feedback.
     */
    public CompileTestExecutor(){
        testFeedback = new ConcreteTestFeedback();
    }

    /**
     * Executes the compilation test. This method sets up the test details, runs the test, and handles
     * the results by calling the appropriate methods based on whether the program compiled successfully.
     */
    @Override
    public void executeTest() {
        testType = test.toString();
        testFeedback.setTestType(testType);  // Set the test type to be the test's class name
        test.setUpTestDetails(testSetupDetailMap);  // Set up test details using the configuration map
        TestExecutionSummary testExecutionSummary = test.executeTest(test.getClass()).getSummary();  // Execute the test and get the results summary
        
        // Check if the program passed all tests (compilation) and handle the result
        if (testExecutionSummary.getTestsFailedCount() == 0 && testExecutionSummary.getContainersFailedCount() == 0) {
            handlePass();  // If no failures, handle as a pass
        } else {
            handleFailure(testExecutionSummary);  // If there are failures, handle as a failure
        }
        
        // Add the feedback results to the assignment's feedback
        assignmentFeedBack.addTestResults(testFeedback);
    }

    /**
     * Handles the case where the program compilation test passes successfully.
     * This method sets the feedback message to indicate the assignment compiled successfully
     * and assigns the full marks for the test.
     */
    @Override
    public void handlePass() {
        testFeedback.setMarks(marks);  // Set marks to the predefined value
        testFeedback.setFeedbackMsg("Assignment Compiles");  // Set feedback message to indicate success
    }

    /**
     * Handles the case where the program compilation test fails.
     * This method processes the failures and generates a detailed feedback message that includes
     * the failed test cases and the reasons for their failure.
     *
     * @param testExecutionSummary The summary of the test execution, which includes details of any failures.
     */
    @Override
    public void handleFailure(TestExecutionSummary testExecutionSummary) {
        testFeedback.setMarks(0);  // Set marks to 0 in case of failure
        StringBuilder failureMsg = new StringBuilder("Assignment failed to compile because of: ");
        
        // Iterate through the failures and append details to the failure message
        TestExecutionSummary.Failure[] failures = testExecutionSummary.getFailures().toArray(new TestExecutionSummary.Failure[0]);
        for (TestExecutionSummary.Failure failure : failures) {
            failureMsg.append("Test: ")
                .append(failure.getTestIdentifier().getDisplayName())
                .append(" failed. Reason: ")
                .append(failure.getException().getMessage())
                .append("\t");
        }
        
        // Set the feedback message to the generated failure details
        testFeedback.setFeedbackMsg(String.valueOf(failureMsg));
    }
}
