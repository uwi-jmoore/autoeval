package assignmentevaluator.evaluationHelpers.executors.classlevel;


import org.junit.platform.launcher.listeners.TestExecutionSummary;

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
        testType = Desc +"-"+ test.toString();
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
}
