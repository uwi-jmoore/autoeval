package assignmenttests.classlevel.factory.classlevel;

import assignmenttests.AssignmentTest;
import assignmenttests.classlevel.factory.AssignmentTestFactory;
import assignmenttests.classlevel.products.attribute.AttributeSignatureTest;

public class AttributeSignatureTestFactory extends AssignmentTestFactory {
    public static AssignmentTest createTest(String... type) {
        return new AttributeSignatureTest();
    }


}
