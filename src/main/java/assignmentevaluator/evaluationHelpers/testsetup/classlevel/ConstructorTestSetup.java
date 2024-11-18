package assignmentevaluator.evaluationHelpers.testsetup.classlevel;

import java.util.HashMap;
import java.util.List;

import assignmentevaluator.evaluationHelpers.testsetup.TestSetupService;
import assignmenttests.classlevel.products.method.supports.ClassTestAttributeExpectedValue;
import assignmenttests.classlevel.products.method.supports.ClassTestParameter;

/**
 * Configures the setup for class-level constructor tests.
 * This class is responsible for initializing the parameters and modified class attributes used in testing constructors.
 * It stores information about the method parameters and class attributes that are expected to be modified after constructing the class.
 */
public class ConstructorTestSetup extends TestSetupService {

    /**
     * Constructs a new `ConstructorTestSetup` instance.
     *
     * @param methodParameterInput a list of parameters to be passed to the constructor during testing.
     * @param modifiedClassAttributes a list of modified class attributes that result from calling the constructor.
     */
    public ConstructorTestSetup(
        List<ClassTestParameter> methodParameterInput,
        List<ClassTestAttributeExpectedValue> modifiedClassAttributes
    ){
        map = new HashMap<>();
        map.put("methodParameterInputs", methodParameterInput);
        map.put("methodModifiedClassAttributes", modifiedClassAttributes);
    }
}

