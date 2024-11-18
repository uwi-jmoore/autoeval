package assignmenttests.classlevel.factory.classlevel;

import assignmenttests.AssignmentTest;
import assignmenttests.classlevel.factory.AssignmentTestFactory;
import assignmenttests.classlevel.products.attribute.AttributeSignatureTest;

/**
 * Factory class for creating instances of AttributeSignatureTest.
 * This class extends AssignmentTestFactory and provides a method to create
 * attribute signature tests.
 */
public class AttributeSignatureTestFactory extends AssignmentTestFactory {

    /**
     * Creates an instance of AttributeSignatureTest.
     *
     * @param type optional parameter that can be used to specify the type of test,
     *             currently not utilized in this factory method
     * @return a new instance of AttributeSignatureTest
     */
    public static AssignmentTest createTest(String... type) {
        return new AttributeSignatureTest(); // Returns a new AttributeSignatureTest instance
    }
}
