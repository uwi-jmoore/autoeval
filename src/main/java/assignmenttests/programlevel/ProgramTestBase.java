package assignmenttests.programlevel;

import assignmentevaluator.evaluationHelpers.AssignmentRunner;
import assignmenttests.AssignmentTest;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;

import java.io.File;
import java.util.List;
import java.util.Map;

import static assignmenttests.classlevel.ClassLevelHelpers.findMissingKeys;
import static org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder.request;

/**
 * Abstract base class for program-level assignment tests.
 * Provides setup and utility methods for executing tests on assignments.
 * It integrates with JUnit 5 and allows for the compilation and execution of assignments.
 * 
 * <p>
 * This class is extended by specific tests to implement functionality that 
 * evaluates assignments in different aspects, such as compiling and running tests.
 * </p>
 */
public abstract class ProgramTestBase implements AssignmentTest {
    /**
     * The directory containing the assignment files.
     */
    protected static File assignmentDirectory;

    /**
     * The {@link AssignmentRunner} instance used for compiling and running assignments.
     */
    protected AssignmentRunner runner;

    /**
     * Constructs a {@code ProgramTestBase} instance and initializes the {@code runner}.
     */
    public ProgramTestBase() {
        runner = new AssignmentRunner();
    }

    /**
     * Sets the directory where the assignment files are located.
     * This directory is used for compilation and execution of the assignment.
     * 
     * @param assignmentDirectory the directory containing assignment files.
     */
    public void setAssignmentDirectory(File assignmentDirectory) {
        ProgramTestBase.assignmentDirectory = assignmentDirectory;
    }

    /**
     * Executes the test class provided as a parameter.
     * This method launches the test using JUnit 5's {@link Launcher} and returns the results in a listener.
     * 
     * @param targetTestClass The class of the test to execute.
     * @return A {@link SummaryGeneratingListener} containing the results of the executed test.
     */
    @Override
    public SummaryGeneratingListener executeTest(Class<?> targetTestClass) {
        Launcher launcher = LauncherFactory.create();
        SummaryGeneratingListener listener = new SummaryGeneratingListener();
        LauncherDiscoveryRequest request = request()
            .selectors(
                DiscoverySelectors.selectClass(targetTestClass)
            )
            .build();
        launcher.execute(request, listener);
        return listener;
    }

    /**
     * Verifies that the required setup content is present before running the test.
     * It checks for the key "assignmentDirectory" in the provided map.
     * If the key is missing, an error is logged.
     * 
     * @param setUpContent A map containing setup details for the test.
     */
    public void setUpTestDetails(Map<String, Object> setUpContent) {
        List<String> expectedSetupContents = List.of(
            "assignmentDirectory"
        );
        List<String> missingKeys = findMissingKeys(setUpContent, expectedSetupContents);
        
        if (missingKeys.isEmpty()) {
            setCompileTestDetails(setUpContent);
        } else {
            System.err.println("Missing keys in setup Map: " + missingKeys);
        }
    }

    /**
     * Configures the setup details for compilation by setting the assignment directory
     * and updating the {@link AssignmentRunner} with this directory.
     * 
     * @param setUpContent A map containing setup details for the test.
     */
    protected void setCompileTestDetails(Map<String, Object> setUpContent) {
        setAssignmentDirectory((File) setUpContent.get("assignmentDirectory"));
        runner.setRunnerAssignmentDirectory(assignmentDirectory);
    }

    /**
     * Compiles all Java files in the given assignment directory.
     * This method utilizes the {@link AssignmentRunner} to perform the compilation.
     * 
     * @return true if compilation is successful, false if no Java files are found or compilation fails.
     */
    protected boolean compileAssignmentTester() {
        return runner.compileAssignment();
    }
}
