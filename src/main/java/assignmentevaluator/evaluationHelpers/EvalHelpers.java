package assignmentevaluator.evaluationHelpers;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.TestPlan;
import static org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder.request;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;

import static filehandler.filehelperservice.FileOperationHelpers.getDirectoryFilesOfExt;
import static filehandler.filehelperservice.FileOperationHelpers.getFileName;

/**
 * Utility class for helper methods used in evaluating assignments.
 */
public class EvalHelpers {

    /**
     * Retrieves an array of files with a specified extension from a given directory.
     *
     * @param studentAssignmentDirectory The directory containing the student's assignment files.
     * @param type The file extension type to filter for (e.g., "java" or "class").
     * @return An array of {@link File} objects that match the specified extension within the directory.
     */
    public static File[] getAssignmentFiles(File studentAssignmentDirectory, String type) {
        return getDirectoryFilesOfExt(studentAssignmentDirectory, type);
    }

    /**
     * Searches for a specific file in a given directory by its name and type.
     *
     * @param studentAssignmentDirectory The directory containing the student's assignment files.
     * @param fileName The name of the file to search for (without extension).
     * @param fileType The file type/extension to search for.
     * @return The {@link File} object that matches the given name and type, or {@code null} if not found.
     */
    public static File getFile(File studentAssignmentDirectory, String fileName, String fileType) {
        File[] assignmentJavaFiles = getAssignmentFiles(studentAssignmentDirectory, fileType);
        for (File f : assignmentJavaFiles) {
            if (Objects.equals(getFileName(f), fileName)) {
                return f;
            }
        }
        return null;
    }

    /**
     * Creates a map containing the details required for setting up an attribute test.
     *
     * @param attrName The attribute name.
     * @param dataType The data type of the attribute.
     * @param hasDefault Indicator if the attribute has a default value.
     * @param defaultVal The default value of the attribute.
     * @param isFinal Indicator if the attribute is final.
     * @param isStatic Indicator if the attribute is static.
     * @return A map of the attribute setup details where keys represent attribute properties.
     */
    public static Map<String, String> createAttributeTestSetupMap(String attrName, String dataType, String hasDefault, String defaultVal, String isFinal, String isStatic) {
        Map<String, String> map = new HashMap<>();
        map.put("attributeName", attrName);
        map.put("dataType", dataType);
        map.put("hasDefault", hasDefault);
        map.put("defaultValue", defaultVal);
        map.put("isFinal", isFinal);
        map.put("isStatic", isStatic);
        return map;
    }

    /**
     * Executes tests for a given class using JUnit's platform launcher and returns a summary of the results.
     *
     * @param targetClass The class to be tested.
     * @return A {@link SummaryGeneratingListener} containing the summary of test results.
     */
    public static SummaryGeneratingListener getTestReturn(Class<?> targetClass) {
        Launcher launcher = LauncherFactory.create();
        SummaryGeneratingListener listener = new SummaryGeneratingListener();

        LauncherDiscoveryRequest request = request()
            .selectors(
                DiscoverySelectors.selectClass(targetClass)
            )
            .build();

        // For debugging: Print discovered tests
        TestPlan testPlan = launcher.discover(request);
        System.out.println("Discovered Tests: " + testPlan.getRoots().size());
        for (TestIdentifier testIdentifier : testPlan.getRoots()) {
            System.out.println("Root Identifier: " + testIdentifier.getDisplayName());
            for (TestIdentifier child : testPlan.getChildren(testIdentifier)) {
                System.out.println("Child Identifier: " + child.getDisplayName());
                for (TestIdentifier grandChild : testPlan.getChildren(child)) {
                    System.out.println("GrandChild Identifier: " + grandChild.getDisplayName());
                }
            }
        }

        // Execute the tests
        launcher.execute(request, listener);
        return listener;
    }
}
