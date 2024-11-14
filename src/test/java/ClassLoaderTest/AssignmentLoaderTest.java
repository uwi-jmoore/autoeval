package ClassLoaderTest;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import assignmentevaluator.classloader.AssignmentClassLoader;

/**
 * The AssignmentLoaderTest class contains JUnit tests for verifying the behavior of
 * the AssignmentClassLoader, specifically the loading of classes from file paths.
 * <p>
 * The tests validate that classes can be loaded, have the correct names, and contain
 * expected fields or modifiers.
 * </p>
 */
public class AssignmentLoaderTest {

    /**
     * Tests the loading of a class file using the AssignmentClassLoader.
     * <p>
     * This test verifies that a class file at the specified path is correctly loaded.
     * It checks the following:
     * <ul>
     *     <li>The loaded class has the expected name, "ChatBot".</li>
     *     <li>The class has public access.</li>
     *     <li>A field named "chatBotName" exists in the loaded class.</li>
     * </ul>
     * </p>
     *
     * @throws IOException if there is an issue reading the class file.
     */
    @Test
    public void testLoadClassFromFile() throws IOException {
        String classFilePath = "C:\\Users\\Chimera\\Desktop\\Project_Testbed\\" +
            "Unzipping\\extraction_test\\Assignments-main_assign\\" +
            "Isabella_Augustus_816031354_A1\\Assignment_1\\ChatBot.class";

        AssignmentClassLoader testLoader = new AssignmentClassLoader();
        Class<?> loadedClass = testLoader.loadClassFromFile(classFilePath);

        assertAll("Testing Loading of class",
            () -> assertEquals(loadedClass.getName(), "ChatBot"),
            () -> assertTrue(Modifier.isPublic(loadedClass.getModifiers())),
            () -> {
                Field field = loadedClass.getDeclaredField("chatBotName");
                assertNotNull(field);
            }
        );
    }

    /**
     * Tests the loading of a test class file using the AssignmentClassLoader.
     * <p>
     * This test verifies that a class file at the specified path is correctly loaded,
     * and that the loaded class has the expected name, "IteratorTest".
     * </p>
     *
     * @throws IOException if there is an issue reading the test file.
     */
    @Test
    public void testLoadTest() throws IOException {
        String testFilePath = "C:\\Users\\Chimera\\Desktop\\METAVAULTS\\" +
            "OMICRON\\Computing\\OOP2\\UWI\\Project\\autoeval" +
            "\\src\\test\\java\\FileIteratorTest\\IteratorTest.java";

        AssignmentClassLoader testLoader = new AssignmentClassLoader();
        Class<?> loadedClass = testLoader.loadClassFromFile(testFilePath);

        assertAll("Testing Loading of class",
            () -> assertEquals(loadedClass.getName(), "IteratorTest")
        );
    }
}
