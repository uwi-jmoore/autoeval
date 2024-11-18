package assignmentevaluator.evaluationHelpers.testsetup.classlevel;

import java.util.List;

import assignmenttests.classlevel.products.method.supports.ClassTestAttributeExpectedValue;
import assignmenttests.classlevel.products.method.supports.ClassTestParameter;

/**
 * Configures the setup for class-level modifier method tests.
 * This class is responsible for initializing and setting up the input parameters 
 * and expected changes in class attributes for modifier methods.
 */
public class ModifierMethodTestSetup extends MethodTestSetupService {

    /**
     * Constructs a new `ModifierMethodTestSetup` instance.
     * It initializes the parent class and sets up empty mappings for the method input parameters
     * and the modified class attributes.
     */
    public ModifierMethodTestSetup(){
        super();
        map.put("methodParameterInputs", null);
        map.put("methodModifiedClassAttributes", null);
    }

    /**
     * Adds the method input parameters to be used during testing the modifier method.
     *
     * @param methodParameterInput a list of parameters to be passed to the modifier method during testing.
     */
    public void addMethodInputParams(List<ClassTestParameter> methodParameterInput){
        map.put("methodParameterInputs", methodParameterInput);
    }

    /**
     * Adds the expected changes to the class attributes after calling the modifier method.
     *
     * @param modifiedClassAttributes a list of expected modified class attributes after invoking the method.
     */
    public void addMethodExpectedAttrChanges(List<ClassTestAttributeExpectedValue> modifiedClassAttributes){
        map.put("methodModifiedClassAttributes", modifiedClassAttributes);
    }
}
