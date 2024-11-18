package assignmentevaluator.evaluationHelpers.executors.programlevel;

import org.junit.platform.launcher.listeners.TestExecutionSummary;

import assignmentevaluator.feedback.types.ConcreteTestFeedback;

/**
 * Class that represents an executor for running program-level tests on assignments.
 * This class specifically handles the execution of tests that check whether 
 * the assignment runs correctly (e.g., whether the program produces an exit code 0).
 */
public class RunTestExecutor extends ProgramTestExecutor {

    /**
     * Constructor for the RunTestExecutor class.
     * Initializes the test feedback object that will be used to provide feedback
     * based on the outcome of the test execution.
     */
    public RunTestExecutor(){
        testFeedback = new ConcreteTestFeedback();
    }

    /**
     * Handles the case where the test passes.
     * If the assignment runs successfully and returns exit code 0, the marks are awarded
     * and feedback is set indicating that the assignment ran successfully.
     */
    @Override
    public void handlePass() {
        testFeedback.setMarks(marks);
        testFeedback.setFeedbackMsg("Assignment Runs, Exit Code 0 returned");
    }

    /**
     * Handles the case where the test fails.
     * If the assignment fails to run, this method processes the test execution summary,
     * providing details on which test failed and the reason for the failure.
     * 
     * @param testExecutionSummary The summary of the test execution, containing failure details
     */
    @Override
    public void handleFailure(TestExecutionSummary testExecutionSummary) {
        testFeedback.setMarks(0);
        StringBuilder failureMsg = new StringBuilder("Assignment failed to run because of: ");
        TestExecutionSummary.Failure[] failures = testExecutionSummary.getFailures().toArray(new TestExecutionSummary.Failure[0]);
        for (TestExecutionSummary.Failure failure : failures) {
            failureMsg.append("Test: ")
                .append(failure.getTestIdentifier().getDisplayName())
                .append(" failed. Reason: ")
                .append(failure.getException().getMessage())
                .append("\t");
        }
        testFeedback.setFeedbackMsg(String.valueOf(failureMsg));
    }

    /**
     * Executes the test by setting up the test details and running the test.
     * It checks the execution summary to determine whether the assignment ran successfully
     * or if there were any failures.
     * If the test passes, it invokes the {@link #handlePass()} method; otherwise, it invokes
     * {@link #handleFailure(TestExecutionSummary)}.
     */
    @Override
    public void executeTest() {
        testType = test.toString();
        testFeedback.setTestType(testType);
        test.setUpTestDetails(testSetupDetailMap);
        TestExecutionSummary testExecutionSummary = test.executeTest(test.getClass()).getSummary();
        if (testExecutionSummary.getTestsFailedCount() == 0 && testExecutionSummary.getContainersFailedCount() == 0) {
            handlePass();
        } else {
            handleFailure(testExecutionSummary);
        }
        assignmentFeedBack.addTestResults(testFeedback);
    }
}
