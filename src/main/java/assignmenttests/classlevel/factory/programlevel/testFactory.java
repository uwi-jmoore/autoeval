package assignmenttests.classlevel.factory.programlevel;

import assignmenttests.AssignmentTest;

/**
 * Abstract base class for creating different types of {@link AssignmentTest} instances.
 *
 * <p>Subclasses must implement the {@code createAssignmentTest} method to define the
 * logic for generating specific types of {@link AssignmentTest} objects based on the given type.</p>
 */
public abstract class testFactory {

    /**
     * Creates an instance of {@link AssignmentTest} based on the specified type.
     *
     * @param type A {@code String} representing the type of {@link AssignmentTest} to create.
     * @return An instance of {@link AssignmentTest} corresponding to the specified type.
     *         The actual implementation is defined by subclasses.
     */
    public abstract AssignmentTest createAssignmentTest(String type);
}
