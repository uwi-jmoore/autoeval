package assignmentevaluator.evaluationHelpers.testsetup.classlevel;

import assignmenttests.classlevel.products.method.supports.ClassTestAttributeExpectedValue;
import assignmenttests.classlevel.products.method.supports.ClassTestParameter;

import java.util.List;

public class ModifierMethodTestSetup extends MethodTestSetupService {
    public ModifierMethodTestSetup(){
        super();
        map.put("methodParameterInputs",null);
        map.put("methodModifiedClassAttributes",null);
    }

    public void addMethodInputParams(List<ClassTestParameter> methodParameterInput){
        map.put("methodParameterInputs",methodParameterInput);
    }

    public void addMethodExpectedAttrChanges(List<ClassTestAttributeExpectedValue> modifiedClassAttributes){
        map.put("methodModifiedClassAttributes",modifiedClassAttributes);
    }

}
