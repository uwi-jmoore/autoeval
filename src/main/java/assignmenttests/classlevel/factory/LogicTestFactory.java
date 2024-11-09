package assignmenttests.classlevel.factory;

import assignmenttests.classlevel.ClassTest;
import assignmenttests.classlevel.concreteproducts.LogicTest;

public class LogicTestFactory extends ClassLevelTestFactory{
    public static ClassTest createClassTest() {
        return new LogicTest();
    }
}
