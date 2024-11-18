package assignmenttests.programlevel.products;

import assignmenttests.programlevel.ProgramTestBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class is responsible for testing the compilation of assignments.
 * It extends the {@link ProgramTestBase} class, inheriting common testing functionality.
 * The test ensures that the assignment compiles without errors.
 * 
 * <p>
 * This class uses JUnit 5 annotations for testing and operates in the 
 * {@code PER_CLASS} lifecycle, meaning that the test instance is reused across 
 * multiple tests, which is useful for maintaining state between tests if needed.
 * </p>
 * 
 * @see ProgramTestBase
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AssignmentCompileTest extends ProgramTestBase {

    /**
     * Default constructor for {@code AssignmentCompileTest}.
     * Invokes the constructor of the superclass {@link ProgramTestBase}.
     */
    public AssignmentCompileTest() {
        super();
    }

    /**
     * Overrides the {@code toString} method to return the name of the test.
     * 
     * @return A string representation of the test name, "Assignment Compile Test".
     */
    @Override
    public String toString() {
        return "Assignment Compile Test";
    }

    /**
     * Tests the compilation of the assignment.
     * This test is annotated with {@link DisplayName} for improved readability in test reports.
     * It uses an assertion to check if the assignment compiles successfully.
     * 
     * @throws AssertionError if the assignment does not compile.
     */
    @Test
    @DisplayName("Assignment Compile Test")
    public void testAssignmentCompile() {
        assertTrue(compileAssignmentTester(), "Assignment Does Not Compile");
    }
}
