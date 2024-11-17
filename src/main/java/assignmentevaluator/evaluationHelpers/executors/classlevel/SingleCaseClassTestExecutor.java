package assignmentevaluator.evaluationHelpers.executors.classlevel;


import org.junit.platform.launcher.listeners.TestExecutionSummary;
import org.junit.platform.launcher.listeners.TestExecutionSummary.Failure;

import assignmentevaluator.feedback.types.ConcreteTestFeedback;

/**
 * Executes class level tests for a student's assignment and provides feedback based on the test results.
 * This class evaluates a given class file by running specified tests and generates feedback on
 * whether the tests passed or failed, and assigns marks accordingly.
 */
public class SingleCaseClassTestExecutor extends ClassTestExecutor {
    /**
     * Default constructor that initializes the test feedback.
     */
    public SingleCaseClassTestExecutor() {
        testFeedback = new ConcreteTestFeedback();
    }


    /**
     * Executes the assigned test case on the evaluated file and provides feedback based on the results.
     */
    public void executeTest() {
        testType = test.toString();
        testFeedback.setTestType(testType);
        test.setClassFilePath(evaluatingClassFile.getAbsolutePath());
        test.setUpTestDetails(testSetupDetailMap);
//        TestExecutionSummary testReturn = getTestReturn(test.getClass()).getSummary();
        TestExecutionSummary testReturn = test.executeTest(test.getClass()).getSummary();
        if (testReturn.getTestsFailedCount() == 0 && testReturn.getContainersFailedCount() == 0) {
            handleAllPass();
        } else {
            handleFailure(testReturn);
        }
        assignmentFeedBack.addTestResults(testFeedback);
    }

    /**
     * Handles the case when all tests pass by setting appropriate marks and feedback message.
     */
    private void handleAllPass() {
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
