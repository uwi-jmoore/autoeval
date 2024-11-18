package assignmenttests.classlevel.factory.programlevel;

import assignmenttests.AssignmentTest;
import assignmenttests.classlevel.factory.AssignmentTestFactory;
import assignmenttests.programlevel.products.AssignmentRunTest;

/**
 * Factory class for creating instances of {@link AssignmentRunTest}.
 * Extends {@link AssignmentTestFactory}.
 *
 * <p>This factory provides a static method {@code createTest} to generate
 * a new instance of {@link AssignmentRunTest}. The method accepts an optional
 * array of {@code String} arguments, though these are not utilized in the current implementation.</p>
 */
public class RunTestFactory extends AssignmentTestFactory {

    /**
     * Creates a new instance of {@link AssignmentRunTest}.
     *
     * @param type Optional {@code String} arguments specifying test types
     *             (currently not used in this implementation).
     * @return A new instance of {@link AssignmentRunTest}.
     */
    public static AssignmentTest createTest(String... type) {
        return new AssignmentRunTest();
    }
}
