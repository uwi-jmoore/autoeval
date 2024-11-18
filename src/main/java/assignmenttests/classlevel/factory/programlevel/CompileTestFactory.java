package assignmenttests.classlevel.factory.programlevel;

import assignmenttests.AssignmentTest;
import assignmenttests.classlevel.factory.AssignmentTestFactory;
import assignmenttests.programlevel.products.AssignmentCompileTest;

/**
 * Factory class for creating instances of {@link AssignmentCompileTest}.
 * Extends {@link AssignmentTestFactory}.
 *
 * <p>This factory provides a static method {@code createTest} to generate
 * a new instance of {@link AssignmentCompileTest}. The method accepts an optional
 * array of {@code String} arguments, though they are not utilized in the current implementation.</p>
 */
public class CompileTestFactory extends AssignmentTestFactory {

    /**
     * Creates a new instance of {@link AssignmentCompileTest}.
     *
     * @param type Optional {@code String} arguments specifying test types
     *             (currently not used in this implementation).
     * @return A new instance of {@link AssignmentCompileTest}.
     */
    public static AssignmentTest createTest(String... type) {
        return new AssignmentCompileTest();
    }
}
