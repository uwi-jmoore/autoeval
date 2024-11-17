package assignmenttests.classlevel.factory.programlevel;

import assignmenttests.AssignmentTest;
import assignmenttests.classlevel.factory.AssignmentTestFactory;
import assignmenttests.programlevel.products.AssignmentCompileTest;

public class CompileTestFactory extends AssignmentTestFactory {
    public static AssignmentTest createTest(String... type){
        return new AssignmentCompileTest();
    }
}
