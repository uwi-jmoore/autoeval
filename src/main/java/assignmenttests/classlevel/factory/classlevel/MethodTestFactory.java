package assignmenttests.classlevel.factory.classlevel;

import assignmenttests.AssignmentTest;
import assignmenttests.classlevel.factory.AssignmentTestFactory;
import assignmenttests.classlevel.products.method.concrete.ConstructorTest;
import assignmenttests.classlevel.products.method.concrete.ModifierTest;
import assignmenttests.classlevel.products.method.concrete.RetrievalTest;

public class MethodTestFactory extends AssignmentTestFactory {
    public static AssignmentTest createTest(String type) {
        return switch (type){
            case "modifier" -> new ModifierTest();
            case "constructor" -> new ConstructorTest();
            case "retriever" -> new RetrievalTest();
            default -> null;
        };
    }
}
