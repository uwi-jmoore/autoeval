package assignmenttests.classlevel.factory.classlevel;

import assignmenttests.AssignmentTest;
import assignmenttests.classlevel.factory.AssignmentTestFactory;
import assignmenttests.classlevel.products.method.concrete.ConstructorTest;
import assignmenttests.classlevel.products.method.concrete.ModifierTest;
import assignmenttests.classlevel.products.method.concrete.RetrievalTest;

/**
 * Factory class for creating method-related tests.
 * This class extends the AssignmentTestFactory and provides a method to create
 * specific types of assignment tests related to methods, such as modifiers, constructors, and retrievals.
 */
public class MethodTestFactory extends AssignmentTestFactory {

    /**
     * Creates an instance of AssignmentTest based on the specified test type.
     *
     * @param type the type of method test to create
     * @return an instance of AssignmentTest corresponding to the specified type,
     *         or null if the type is not recognized
     */
    public static AssignmentTest createTest(String type) {
        return switch (type) {
            case "modifier" -> new ModifierTest(); // Creates a ModifierTest if type is "modifier"
            case "constructor" -> new ConstructorTest(); // Creates a ConstructorTest if type is "constructor"
            case "retriever" -> new RetrievalTest(); // Creates a RetrievalTest if type is "retriever"
            default -> null; // Returns null for unrecognized types
        };
    }
}
