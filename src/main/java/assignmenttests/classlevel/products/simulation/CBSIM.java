package assignmenttests.classlevel.products.simulation;

import assignmentevaluator.evaluationHelpers.AssignmentRunner;
import assignmenttests.classlevel.ClassTestBase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static assignmenttests.classlevel.ClassLevelHelpers.findMissingKeys;
import static assignmenttests.classlevel.products.simulation.MethodCallCounter.countMethodCalls;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Class-level test for simulating and verifying assignment behavior.
 * This test verifies method call counts and console output matches based on the assignment setup.
 * 
 * <p>
 * This test extends {@link ClassTestBase} and performs the following:
 * - Verifies console outputs for correctness.
 * - Ensures expected method calls are made during execution.
 * </p>
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CBSIM extends ClassTestBase {
    
    /**
     * The directory where the assignment files are located.
     */
    protected static File assignmentDirectory;

    /**
     * The runner used to execute the assignment and capture outputs.
     */
    protected AssignmentRunner runner;

    /**
     * Expected console outputs to match against during the tests.
     */
    protected static List<String> expectedConsoleOutputs;

    /**
     * List of method call expectations for the assignment.
     */
    protected static List<MethodCalls> methodCalls;

    /**
     * The file path to the assignment to be tested.
     */
    protected static String filePath;

    /**
     * Constructs a {@code CBSIM} instance and initializes the {@code runner}.
     */
    public CBSIM() {
        runner = new AssignmentRunner();
    }

    /**
     * Sets the expected method calls for the simulation test.
     *
     * @param methodCalls The list of expected method calls and their counts.
     */
    protected void setMethodCalls(List<MethodCalls> methodCalls) {
        CBSIM.methodCalls = methodCalls;
    }

    /**
     * Sets the expected console outputs for the simulation test.
     *
     * @param expectedConsoleOutputs The list of expected console outputs.
     */
    protected void setExpectedConsoleOutputs(List<String> expectedConsoleOutputs) {
        CBSIM.expectedConsoleOutputs = expectedConsoleOutputs;
    }

    /**
     * Sets the file path of the assignment to be tested.
     *
     * @param filePath The file path to the assignment.
     */
    protected void setFilePath(String filePath) {
        CBSIM.filePath = filePath;
    }

    /**
     * Compares the runner output stream to the expected console output.
     * This method checks if all components in the expected output are present in the runner output.
     * 
     * @param component The expected output component to match against.
     * @return true if the runner output contains all parts of the component, false otherwise.
     */
    protected boolean streamOutputMatch(String component) {
        String[] broken = component.split("[@]");
        for (String s : broken) {
            if (!runner.getRunnerOutputStream().contains(s)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Verifies that all expected method calls were made the correct number of times.
     * 
     * @return true if all method calls match the expected counts, false otherwise.
     * @throws IOException If there is an issue reading the file.
     */
    protected boolean testMethodCalls() throws IOException {
        for (MethodCalls methodCall : methodCalls) {
            if (countMethodCalls(filePath, methodCall.methodName()) < methodCall.expectedCalls()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Test to verify that "Hello World" appears in the output stream.
     * This checks if the first expected console output is present.
     */
    @Test
    public void testHelloWorld() {
        assertTrue(runner.getRunnerOutputStream().contains(expectedConsoleOutputs.get(0)), "Expected 'Hello World' output not found.");
    }

    /**
     * Test to verify that the second expected console output (ChatBotStats) is present in the output stream.
     */
    @Test
    public void testChatBotStats() {
        assertTrue(streamOutputMatch(expectedConsoleOutputs.get(1)), "Expected ChatBotStats output not found.");
    }

    /**
     * Test to verify that the expected method calls were made during the simulation.
     * This checks the method call count against the expectations.
     * 
     * @throws IOException If there is an issue with reading the file during method call validation.
     */
    @Test
    public void createdChatBots() throws IOException {
        assertTrue(testMethodCalls(), "Expected method calls were not made correctly.");
    }

    /**
     * Sets up the test details, ensuring that all required setup keys are present in the provided map.
     * If any keys are missing, an error is logged.
     * 
     * @param setUpContent A map containing setup details for the test.
     */
    @Override
    public void setUpTestDetails(Map<String, Object> setUpContent) {
        List<String> expectedSetupContents = List.of(
            "consoleOut", // Expected console outputs
            "methodCall", // Expected method call information
            "file" // File path of the assignment
        );
        List<String> missingKeys = findMissingKeys(setUpContent, expectedSetupContents);
        if (missingKeys.isEmpty()) {
            setUpSimTest(setUpContent);
        } else {
            System.err.println("Missing keys in setup Map: " + missingKeys);
        }
    }

    /**
     * Initializes the simulation test by calling the superclass setup method.
     * 
     * @param setUpContent A map containing setup details for the test.
     */
    protected void setUpSimTest(Map<String, Object> setUpContent) {
        super.classTestBaseSetUp();
    }
}
