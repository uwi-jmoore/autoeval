package assignmenttests.classlevel;

import assignmentevaluator.classloader.AssignmentClassLoader;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;

import java.io.File;

import static filehandler.filehelperservice.FileOperationHelpers.getFileNameFromPathString;
import static org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder.request;

/**
 * Base class for class-level tests, providing setup and test execution functionality.
 * 
 * <p>
 * The {@code ClassTestBase} class provides the necessary setup and utility methods for running class-level tests. 
 * It includes methods for loading the class under test, executing tests with JUnit, and converting primitive types to their wrapper types.
 * </p>
 */
public abstract class ClassTestBase implements ClassTest {
    /**
     * The loaded class to be tested.
     */
    protected static Class<?> loadedClass;

    /**
     * The class loader used to load the class under test.
     */
    protected static AssignmentClassLoader testLoader;

    /**
     * The file path to the class file for the test.
     */
    protected static String classFilePath;

    /**
     * Sets the file path for the class to be tested.
     *
     * @param classFilePath The path to the class file.
     */
    public void setClassFilePath(String classFilePath) {
        ClassTestBase.classFilePath = classFilePath;
    }

    /**
     * Initializes the test by loading the class to be tested using the {@link AssignmentClassLoader}.
     * This method is invoked during the setup phase before any tests are executed.
     */
    protected void classTestBaseSetUp() {
        ClassTestBase.testLoader = new AssignmentClassLoader(new File(classFilePath).getParentFile());
        String className = getFileNameFromPathString(classFilePath);
        try {
            ClassTestBase.loadedClass = testLoader.loadClass(className);
        } catch (ClassNotFoundException e) {
            System.err.println("ClassNotFoundException occurred while trying to load class: "
                + className
                + ". Reason: "
                + e.getMessage());
        }
    }

    /**
     * Executes the test for the specified target test class and generates a summary of the test results.
     *
     * @param targetTestClass The class containing the tests to be executed.
     * @return A {@link SummaryGeneratingListener} containing the results of the executed tests.
     */
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
     * Converts a primitive class type to its corresponding wrapper class.
     *
     * @param primitive The primitive class type to be converted.
     * @return The wrapper class for the given primitive type.
     */
    protected Class<?> primitiveToWrapper(Class<?> primitive) {
        return switch (primitive.getName()) {
            case "boolean" -> Boolean.class;
            case "byte" -> Byte.class;
            case "char" -> Character.class;
            case "double" -> Double.class;
            case "float" -> Float.class;
            case "int" -> Integer.class;
            case "long" -> Long.class;
            case "short" -> Short.class;
            default -> primitive;
        };
    }
}
