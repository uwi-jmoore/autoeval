package assignmentevaluator.evaluationHelpers.executors.programlevel;

import java.io.File;
import java.util.Map;

import org.junit.platform.launcher.listeners.TestExecutionSummary;

import assignmentevaluator.evaluationHelpers.executors.TestExecutor;

/**
 * Abstract class that represents a test executor for program-level testing.
 * This class extends the TestExecutor class and provides common functionality
 * for executing tests on programs (assignments).
 */
public abstract class ProgramTestExecutor extends TestExecutor {

    // A map containing setup details for the test
    protected Map<String, Object> testSetupDetailMap;

    // The directory containing the assignment files to be tested
    protected File assignmentDirectory;

    /**
     * Sets the test setup details for the test executor.
     * This method will be used to provide the configuration or setup parameters
     * that are required to run the specific test for the program.
     * 
     * @param testSetupDetailMap A map containing the setup details for the test
     */
    public void setTestSetupDetailMap(Map<String, Object> testSetupDetailMap) {
        this.testSetupDetailMap = testSetupDetailMap;
    }

    /**
     * Handles the case where the test passes. 
     * This method will be implemented by subclasses to define the behavior when a test is successful.
     */
    public abstract void handlePass();

    /**
     * Handles the case where the test fails.
     * This method will be implemented by subclasses to define the behavior when a test fails,
     * including processing the test execution summary to gather detailed failure information.
     * 
     * @param testExecutionSummary The summary of the test execution, containing failure details
     */
    public abstract void handleFailure(TestExecutionSummary testExecutionSummary);
}
