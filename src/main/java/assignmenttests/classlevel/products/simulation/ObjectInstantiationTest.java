package assignmenttests.classlevel.products.simulation;

import assignmentevaluator.evaluationHelpers.AssignmentRunner;
import assignmenttests.classlevel.ClassTestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.*;
import java.util.List;
import java.util.Map;

import static assignmenttests.classlevel.ClassLevelHelpers.findMissingKeys;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class that verifies the detection of object instantiation in a given assignment.
 * This test checks whether a specific class instance is created within the given assignment code.
 * It extends {@link ClassTestBase} and implements {@link ProgrammaticAction} to perform the necessary checks.
 *
 * <p>
 * The test verifies:
 * - If an object of a specified class is instantiated within the code.
 * - The setup includes information such as the target class, the file containing the code, and the object to check for instantiation.
 * </p>
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ObjectInstantiationTest extends ClassTestBase implements ProgrammaticAction {
    protected static InstantiationAgent instantiationAgent;
    protected static File directory;

    /**
     * Constructs an {@code ObjectInstantiationTest} instance and initializes the {@code instantiationAgent}.
     */
    public ObjectInstantiationTest() {
        instantiationAgent = new InstantiationAgent();
    }

    /**
     * Returns the name of the test.
     *
     * @return The name of the test ("Object Instantiation Test").
     */
    @Override
    public String toString() {
        return "Object Instantiation Test";
    }

    /**
     * Sets up the details required for the test by extracting the necessary values from the provided map.
     * This includes the object to check for instantiation, the target class name, and the file path.
     * If any required keys are missing, they are logged as an error.
     *
     * @param setUpContent A map containing the setup details for the test.
     */
    @Override
    public void setUpTestDetails(Map<String, Object> setUpContent) {
        List<String> expectedSetupContents = List.of(
            "object",   // The class object to check for instantiation
            "target",   // The target class name
            "file"      // The file containing the code to be tested
        );
        List<String> missingKeys = findMissingKeys(setUpContent, expectedSetupContents);
        if (missingKeys.isEmpty()) {
            instantiationAgent.setTargetClassName(setUpContent.get("object").toString());
            ObjectInstantiationTest.directory = (File) setUpContent.get("file");
        } else {
            System.err.println("Missing keys in setup Map: " + missingKeys);
        }
    }

    /**
     * Initializes the instantiation test before each test method is executed.
     * Calls the superclass setup method and sets the file for the instantiation agent.
     */
    @BeforeEach
    public void initInstantiationTest() {
        super.classTestBaseSetUp();
        instantiationAgent.setFile(directory);
    }

    /**
     * Verifies the object instantiation by executing the action to check if the target class instance
     * is detected during the execution of the code.
     */
    @Test
    public void obTest() {
        assertTrue(executeAction(), "ChatBotPlatform instance should have been detected.");
    }

    /**
     * Executes the action to check if the target instance is found in the code.
     *
     * @return {@code true} if the instance is found, {@code false} otherwise.
     */
    @Override
    public boolean executeAction() {
        return instantiationAgent.findInstance();
    }
}
