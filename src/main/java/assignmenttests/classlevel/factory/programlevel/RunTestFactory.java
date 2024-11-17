package assignmenttests.classlevel.factory.programlevel;

import assignmenttests.AssignmentTest;
import assignmenttests.classlevel.factory.AssignmentTestFactory;
import assignmenttests.programlevel.products.AssignmentRunTest;

public class RunTestFactory extends AssignmentTestFactory {
    public static AssignmentTest createTest(String... type){
        return new AssignmentRunTest();
    }
}
