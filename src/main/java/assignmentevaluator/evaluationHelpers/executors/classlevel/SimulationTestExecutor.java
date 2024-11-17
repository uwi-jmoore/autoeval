package assignmentevaluator.evaluationHelpers.executors.classlevel;

import assignmentevaluator.evaluationHelpers.executors.TestExecutor;
import assignmentevaluator.feedback.types.ConcreteTestFeedback;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

public class SimulationTestExecutor extends ClassTestExecutor {
    public SimulationTestExecutor(){
       super();
    }
    @Override
    public void executeTest() {
        testType = test.toString();
        testFeedback.setTestType(testType);
        test.setClassFilePath(evaluatingClassFile.getAbsolutePath());
        test.setUpTestDetails(testSetupDetailMap);
        TestExecutionSummary testReturn = test.executeTest(test.getClass()).getSummary();
        if (testReturn.getTestsFailedCount() == 0 && testReturn.getContainersFailedCount() == 0) {
            handleAllPass();
        } else {
            handleFailure(testReturn);
        }
        assignmentFeedBack.addTestResults(testFeedback);
    }

}
