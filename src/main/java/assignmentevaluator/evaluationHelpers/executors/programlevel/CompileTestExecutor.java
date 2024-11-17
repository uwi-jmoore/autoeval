package assignmentevaluator.evaluationHelpers.executors.programlevel;

import assignmentevaluator.feedback.types.ConcreteTestFeedback;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

public class CompileTestExecutor extends ProgramTestExecutor {

    public CompileTestExecutor(){
        testFeedback = new ConcreteTestFeedback();
    }
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

    @Override
    public void handlePass() {
        testFeedback.setMarks(marks);
        testFeedback.setFeedbackMsg("Assignment Compiles");
    }

    @Override
    public void handleFailure(TestExecutionSummary testExecutionSummary) {
        testFeedback.setMarks(0);
        StringBuilder failureMsg = new StringBuilder("Assignment failed to compile because of: ");
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

}
