package assignmentevaluator.evaluationHelpers.executors.classlevel;

import assignmentevaluator.evaluationHelpers.CustomCase;
import assignmentevaluator.feedback.types.ConcreteTestFeedback;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Executes class level tests for a student's assignment and provides feedback based on the test results.
 * Tests are executed multiple times for each supplied test condition of input parameter, attribute preset, expected post method attribute value, and expected return
 * This class evaluates a given class file by running specified tests and generates feedback on
 * whether the tests passed or failed, and assigns marks accordingly.
 */
public class MultiCaseClassTestExecutor extends ClassTestExecutor {
    protected List<Boolean> subTestPasses;

    protected List<CustomCase> testCases;

    /**
     * Default constructor that initializes the test feedback, the list to track test case pass and failure, and the test case conditions.
     */
    public MultiCaseClassTestExecutor(){
        testCases = new ArrayList<>();
        subTestPasses = new ArrayList<>();
        testFeedback = new ConcreteTestFeedback();
    }

    public void addTestCase(CustomCase customCase){
        testCases.add(customCase);
    }

    public void setTestCases(List<CustomCase> customCases){
        testCases = customCases;
    }


    /**
     * Executes the assigned test case on the evaluated file and provides feedback based on the results.
     */
    @Override
    public void executeTest() {
        testType = "Multiple Case "+ test.toString();
        testFeedback.setTestType(testType);
        test.setClassFilePath(evaluatingClassFile.getAbsolutePath());
//        test.getClassTestData().setClassFilePath(evaluatingClassFile.getAbsolutePath());
        StringBuilder baseMsg = new StringBuilder("Multi-case class test results: ");
        for(CustomCase customCase: testCases){
            String caseFeedback;
            test.setUpTestDetails(setUpCaseRunDetailMap(customCase));
            TestExecutionSummary testReturn = test.executeTest(test.getClass()).getSummary();
            boolean casePassed;
            if (testReturn.getTestsFailedCount() == 0 && testReturn.getContainersFailedCount() == 0) {
                casePassed = true;
                caseFeedback =  handleAllPass(customCase);
            } else {
                casePassed = false;
                caseFeedback =  handleFailure(testReturn,customCase);
            }
            subTestPasses.add(casePassed);
            baseMsg.append(caseFeedback);
        }
        int subtestFailCount = (int) subTestPasses.stream().filter(aBoolean -> false).count();
        testFeedback.setFeedbackMsg(String.valueOf(baseMsg));
        testFeedback.setMarks(subtestFailCount == testCases.size() ?
            0 :
            marks - subtestFailCount);
        assignmentFeedBack.addTestResults(testFeedback);
    }


    /**
     * Sets up the detail map for the test using the elements of the current test case.
     * @return a {@link Map<Object, String>} readable by the setUpTestDetails() method of the classTest
     * @param cCase the current test case being run.
     */
    private Map<String, Object> setUpCaseRunDetailMap(CustomCase cCase){
        Map<String, Object> modifiedtestSetupDetailMap = testSetupDetailMap;
        modifiedtestSetupDetailMap.put("testModifiedClassAttributes",cCase.getCustomTestAttributeValue());
        modifiedtestSetupDetailMap.put("methodParameterInputs",cCase.getCustomParams());
        modifiedtestSetupDetailMap.put("methodModifiedClassAttributes",cCase.getCustomExpectedAttributeValue());
        modifiedtestSetupDetailMap.put("methodReturn",cCase.getCustomReturn());
        return modifiedtestSetupDetailMap;
    }

    /**
     * Handles the case when all tests pass returning an all passed feedback message.
     * @return the feedback that all tests passed
     * @param customCase the current test case being run.
     */
    private String handleAllPass(CustomCase customCase) {
        return "#All Tests Passed for case with; " + customCase;

    }


    /**
     * Handles test failures by constructing a feedback message
     * detailing which tests failed and the reasons for failure.
     *
     * @return the feedback on all failed tests
     * @param testReturn the summary of the executed test.
     */
    private String handleFailure(TestExecutionSummary testReturn,CustomCase customCase) {

        StringBuilder failureMsg = new StringBuilder("For case with; " +customCase+ "--:these tests failed: ");
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
