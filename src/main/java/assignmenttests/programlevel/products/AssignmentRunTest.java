package assignmenttests.programlevel.products;

import assignmentevaluator.evaluationHelpers.AssignmentRunner;
import assignmenttests.programlevel.ProgramTestBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static assignmenttests.classlevel.ClassLevelHelpers.findMissingKeys;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class is responsible for testing the execution of assignments.
 * It extends {@link ProgramTestBase} and ensures that the assignment runs
 * successfully after being compiled.
 * 
 * <p>
 * The test ensures that the assignment compiles correctly and executes with
 * the expected behavior by utilizing an instance of {@link AssignmentRunner}.
 * It is run in the {@code PER_CLASS} lifecycle to reuse the test instance.
 * </p>
 * 
 * @see ProgramTestBase
 * @see AssignmentRunner
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AssignmentRunTest extends ProgramTestBase {

    /**
     * Default constructor for {@code AssignmentRunTest}.
     * Calls the constructor of the superclass {@link ProgramTestBase}.
     */
    public AssignmentRunTest() {
        super();
    }

    /**
     * Overrides the {@code toString} method to return the name of the test.
     * 
     * @return A string representation of the test name, "Assignment Run Test".
     */
    @Override
    public String toString() {
        return "Assignment Run Test";
    }

    /**
     * A static variable representing the main file of the assignment.
     */
    protected static File assignmentMainFile;

    /**
     * Sets the main file for the assignment.
     * This file will be used by the {@link AssignmentRunner} to execute the program.
     * 
     * @param mainFile The main file to set.
     */
    public void setAssignmentMainFile(File mainFile) {
        AssignmentRunTest.assignmentMainFile = mainFile;
    }

    /**
     * Configures the test details for running the assignment. This includes setting
     * the main file and input data required for the test.
     * It also passes the inputs to the {@link AssignmentRunner}.
     * 
     * @param setUpContent A map containing setup details for the test.
     */
    protected void setRunTestDetails(Map<String, Object> setUpContent) {
        super.setCompileTestDetails(setUpContent);
        setAssignmentMainFile((File) setUpContent.get("mainFile"));
        runner.setRunnerMainMethodFile(assignmentMainFile);
        
        @SuppressWarnings("unchecked")
        List<Object> list = (List<Object>) setUpContent.get("inputs");
        runner.addTestInputList(list);
    }

    /**
     * Verifies that the required setup content is present before running the test.
     * It checks for the keys "assignmentDirectory", "mainFile", and "inputs" in the provided map.
     * If any keys are missing, an error is logged.
     * 
     * @param setUpContent A map containing the setup details for the test.
     */
    @Override
    public void setUpTestDetails(Map<String, Object> setUpContent) {
        List<String> expectedSetupContents = Arrays.asList(
            "assignmentDirectory",
            "mainFile",
            "inputs"
        );
        List<String> missingKeys = findMissingKeys(setUpContent, expectedSetupContents);
        
        if (missingKeys.isEmpty()) {
            setRunTestDetails(setUpContent);
        } else {
            System.err.println("Missing keys in setup Map: " + missingKeys);
        }
    }

    /**
     * Tests if the assignment compiles correctly.
     * This test checks whether the assignment can be compiled without errors.
     * 
     * @throws AssertionError if the assignment does not compile successfully.
     */
    @Test
    @DisplayName("Test Assignment Compiles")
    public void testCompiling() {
        assertTrue(runner.compileAssignment(), "Assignment Does Not Compile");
    }

    /**
     * Tests if the assignment runs correctly.
     * This test ensures that the compiled assignment can be executed successfully.
     * If the assignment fails to run, the exit code is included in the error message.
     * 
     * @throws AssertionError if the assignment fails to run successfully.
     */
    @Test
    @DisplayName("Test Assignment Runs")
    public void testRunning() {
        assertTrue(runner.runAssignment(), "Assignment Failed to run successfully, exitcode "
            + runner.getAssignmentExitCode()
            + " returned");
    }
}
