package assignmentevaluator.evaluationHelpers.testsetup.classlevel;

import assignmentevaluator.evaluationHelpers.testsetup.TestSetupService;
import assignmenttests.classlevel.products.method.supports.ClassTestAttributeExpectedValue;
import assignmenttests.classlevel.products.method.supports.ClassTestParameter;

import java.util.HashMap;
import java.util.List;

public class ConstructorTestSetup extends TestSetupService {
    public ConstructorTestSetup(
        List<ClassTestParameter> methodParameterInput,
        List<ClassTestAttributeExpectedValue> modifiedClassAttributes
    ){
        map = new HashMap<>();
        map.put("methodParameterInputs",methodParameterInput);
        map.put("methodModifiedClassAttributes",modifiedClassAttributes);
    }
}
