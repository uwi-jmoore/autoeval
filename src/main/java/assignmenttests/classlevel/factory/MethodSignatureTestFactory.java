package assignmenttests.classlevel.factory;

import assignmenttests.classlevel.ClassTest;
import assignmenttests.classlevel.concreteproducts.MethodSignatureTest;

public class MethodSignatureTestFactory extends ClassLevelTestFactory{
    public static ClassTest createClassTest() {
        return new MethodSignatureTest();
    }
}
