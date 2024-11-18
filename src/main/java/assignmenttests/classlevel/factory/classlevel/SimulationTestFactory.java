package assignmenttests.classlevel.factory.classlevel;

import assignmenttests.AssignmentTest;
import assignmenttests.classlevel.factory.programlevel.testFactory;
import assignmenttests.classlevel.products.simulation.CBSIM;
import assignmenttests.classlevel.products.simulation.ObjectInstantiationTest;

public class SimulationTestFactory extends testFactory {
    @Override
    public AssignmentTest createAssignmentTest(String type) {
        return switch (type){
            case "object" -> new ObjectInstantiationTest();
            case "rem" -> new CBSIM();
            default -> null;
        };
    }
}
