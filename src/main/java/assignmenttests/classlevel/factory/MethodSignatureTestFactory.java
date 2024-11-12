package assignmenttests.classlevel.factory;

import assignmenttests.classlevel.ClassTest;
import assignmenttests.classlevel.concreteproducts.MethodTest;
import assignmenttests.classlevel.concreteproducts.method.ConstructorTest;

public class MethodSignatureTestFactory extends ClassLevelTestFactory{
    public static ClassTest createClassTest(String type) {
        return switch (type){
            case "constructor" -> new ConstructorTest();
            default -> null;
        };
    }
}
