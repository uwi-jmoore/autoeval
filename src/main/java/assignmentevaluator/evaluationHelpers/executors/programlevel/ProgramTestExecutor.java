package assignmentevaluator.evaluationHelpers.executors.programlevel;

import assignmentevaluator.evaluationHelpers.executors.TestExecutor;

import org.junit.platform.launcher.listeners.TestExecutionSummary;

import java.io.File;
import java.util.Map;

public abstract class ProgramTestExecutor extends TestExecutor {
//    protected AssignmentTest test;

    protected Map<String, Object> testSetupDetailMap;

    protected File assignmentDirectory;


    public void setTestSetupDetailMap(Map<String, Object> testSetupDetailMap) {
        this.testSetupDetailMap = testSetupDetailMap;
    }

    public abstract void handlePass();
    public abstract void handleFailure(TestExecutionSummary testExecutionSummary);
}
