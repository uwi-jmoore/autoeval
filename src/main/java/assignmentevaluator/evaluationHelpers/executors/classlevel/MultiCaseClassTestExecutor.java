package assignmentevaluator.evaluationHelpers.executors.classlevel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.platform.launcher.listeners.TestExecutionSummary;

import assignmentevaluator.evaluationHelpers.CustomCase;
import assignmentevaluator.feedback.types.ConcreteTestFeedback;

/**
 * Executes multiple test cases for class-level evaluation of a student's assignment.
 * Each test case involves executing the same test under different conditions such as 
 * varying input parameters, expected return values, and modified attributes.
 * This class handles the execution of these tests and generates feedback for the results.
 */
public class MultiCaseClassTestExecutor extends ClassTestExecutor {

    /**
     * List tracking whether each sub-test case passes or fails.
     */
    protected List<Boolean> subTestPasses;

    /**
     * List of test cases to be executed.
     */
    protected List<CustomCase> testCases;

    /**
     * Default constructor that initializes the test feedback, 
     * the list to track test case results, and the test case conditions.
     */
    public MultiCaseClassTestExecutor() {
        testCases = new ArrayList<>();
        subTestPasses = new ArrayList<>();
        testFeedback = new ConcreteTestFeedback();
    }

    /**
     * Adds a single test case to the list of test cases to be executed.
     *
     * @param customCase the test case to add.
     */
    public void addTestCase(CustomCase customCase) {
        testCases.add(customCase);
    }

    /**
     * Sets the list of test cases to be executed.
     *
     * @param customCases the list of test cases to set.
     */
    public void setTestCases(List<CustomCase> customCases) {
        testCases = customCases;
    }

    /**
     * Executes all the assigned test cases on the evaluated file and provides feedback 
     * based on the test results.
     */
    @Override
    public void executeTest() {
        testType = "Multiple Case " + test.toString();
        testFeedback.setTestType(testType);
        test.setClassFilePath(evaluatingClassFile.getAbsolutePath());

        StringBuilder baseMsg = new StringBuilder("Multi-case class test results: ");
        for (CustomCase customCase : testCases) {
            String caseFeedback;
            test.setUpTestDetails(setUpCaseRunDetailMap(customCase));
            TestExecutionSummary testReturn = test.executeTest(test.getClass()).getSummary();
            boolean casePassed;

            if (testReturn.getTestsFailedCount() == 0 && testReturn.getContainersFailedCount() == 0) {
                casePassed = true;
                caseFeedback = handleAllPass(customCase);
            } else {
                casePassed = false;
                caseFeedback = handleFailure(testReturn, customCase);
            }
            subTestPasses.add(casePassed);
            baseMsg.append(caseFeedback);
        }

        int subtestFailCount = (int) subTestPasses.stream().filter(aBoolean -> !aBoolean).count();
        testFeedback.setFeedbackMsg(String.valueOf(baseMsg));
        testFeedback.setMarks(subtestFailCount == testCases.size() ? 0 : marks - subtestFailCount);
        assignmentFeedBack.addTestResults(testFeedback);
    }

    /**
     * Sets up the details for the current test case run.
     *
     * @param cCase the current test case being run.
     * @return a map of setup details to configure the test.
     */
    private Map<String, Object> setUpCaseRunDetailMap(CustomCase cCase) {
        Map<String, Object> modifiedtestSetupDetailMap = testSetupDetailMap;
        modifiedtestSetupDetailMap.put("testModifiedClassAttributes", cCase.getCustomTestAttributeValue());
        modifiedtestSetupDetailMap.put("methodParameterInputs", cCase.getCustomParams());
        modifiedtestSetupDetailMap.put("methodModifiedClassAttributes", cCase.getCustomExpectedAttributeValue());
        modifiedtestSetupDetailMap.put("methodReturn", cCase.getCustomReturn());
        return modifiedtestSetupDetailMap;
    }

    /**
     * Handles the case where all tests pass for a given test case.
     *
     * @param customCase the current test case being run.
     * @return a feedback message indicating success.
     */
    private String handleAllPass(CustomCase customCase) {
        return "#All Tests Passed for case with; " + customCase;
    }

    /**
     * Handles test failures for a given test case by constructing a detailed feedback message.
     *
     * @param testReturn the summary of the executed test.
     * @param customCase the current test case being run.
     * @return a feedback message indicating the details of failed tests.
     */
    private String handleFailure(TestExecutionSummary testReturn, CustomCase customCase) {
        StringBuilder failureMsg = new StringBuilder("For case with; " + customCase + "--:these tests failed: ");
        TestExecutionSummary.Failure[] failures = testReturn.getFailures().toArray(new TestExecutionSummary.Failure[0]);
        for (TestExecutionSummary.Failure failure : failures) {
            failureMsg.append("Test: ")
                .append(failure.getTestIdentifier().getDisplayName())
                .append(" failed. Reason: ")
                .append(failure.getException().getMessage())
                .append("\t");
        }
        return String.valueOf(failureMsg);
    }
}
