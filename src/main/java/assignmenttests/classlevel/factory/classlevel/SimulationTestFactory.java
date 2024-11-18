package assignmenttests.classlevel.factory.classlevel;

import assignmenttests.AssignmentTest;
import assignmenttests.classlevel.factory.programlevel.testFactory;
import assignmenttests.classlevel.products.simulation.ObjectInstantiationTest;

/**
 * Factory class for creating simulation tests.
 * This class extends the testFactory and overrides the createAssignmentTest method
 * to provide specific implementations for creating different types of assignment tests.
 */
public class SimulationTestFactory extends testFactory {

    /**
     * Creates an instance of AssignmentTest based on the specified type.
     *
     * @param type the type of assignment test to create
     * @return an instance of AssignmentTest, or null if the type is not recognized
     */
    @Override
    public AssignmentTest createAssignmentTest(String type) {
        return switch (type){
            case "object" -> new ObjectInstantiationTest();
            case "rem" -> new CBSIM();
            default -> null;
        };
    }
}
