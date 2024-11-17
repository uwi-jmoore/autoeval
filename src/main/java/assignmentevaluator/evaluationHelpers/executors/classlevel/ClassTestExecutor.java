package assignmentevaluator.evaluationHelpers.executors.classlevel;

import assignmentevaluator.evaluationHelpers.executors.TestExecutor;
import assignmentevaluator.feedback.types.ConcreteTestFeedback;
import assignmenttests.classlevel.ClassTest;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

import java.io.File;
import java.util.Map;

public abstract class ClassTestExecutor extends TestExecutor {
    public ClassTestExecutor(){
        testFeedback = new ConcreteTestFeedback();
    }
    /** The class file being evaluated. */
    protected File evaluatingClassFile;

    /** The specific class level test that will be executed on the student's class. */
    protected ClassTest test;

    /** A map containing setup details for the test case. */
    protected Map<String, Object> testSetupDetailMap;




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
     * Handles the case when all tests pass by setting appropriate marks and feedback message.
     */
    protected void handleAllPass(){
        testFeedback.setMarks(marks);
        testFeedback.setFeedbackMsg("All Tests Passed");
    }

    /**
     * Handles test failures by setting a zero mark and constructing a feedback message
     * detailing which tests failed and the reasons for failure.
     *
     * @param testReturn the summary of the executed test.
     */
    protected void handleFailure(TestExecutionSummary testReturn){
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
