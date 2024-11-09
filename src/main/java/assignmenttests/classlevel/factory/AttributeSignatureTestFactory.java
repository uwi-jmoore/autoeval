package assignmenttests.classlevel.factory;

import assignmenttests.classlevel.ClassTest;
import assignmenttests.classlevel.concreteproducts.AttributeSignatureTest;

public class AttributeSignatureTestFactory extends ClassLevelTestFactory{
    public static ClassTest createClassTest() {
        return new AttributeSignatureTest();
    }


}
