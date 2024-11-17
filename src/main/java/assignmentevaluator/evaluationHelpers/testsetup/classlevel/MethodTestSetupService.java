package assignmentevaluator.evaluationHelpers.testsetup.classlevel;

import assignmentevaluator.evaluationHelpers.testsetup.TestSetupService;
import assignmenttests.classlevel.products.method.supports.ClassTestAttributeTestValue;
import assignmenttests.classlevel.products.method.supports.ClassTestRunMethod;
import assignmenttests.classlevel.products.method.supports.MethodReturn;

import java.util.HashMap;
import java.util.List;

public abstract class MethodTestSetupService extends TestSetupService {

    public MethodTestSetupService(){
        map = new HashMap<>();
        map.put("methodName",null);
        map.put("methodReturn",null);
        map.put("isStatic",null);
        map.put("testModifiedClassAttributes",null);
        map.put("preTestMethods",null);
    }

    public void addMethodName(String methodName){
        map.put("methodName",methodName);
    }
    public void addMethodReturn(MethodReturn methodReturn){
        map.put("methodReturn", methodReturn);
    }
    public void addIsStatic(boolean isMethodStatic){
        map.put("isStatic",isMethodStatic);
    }
    public void addTestModAttrs(List<ClassTestAttributeTestValue> testModifiedValues){
        map.put("testModifiedClassAttributes",testModifiedValues);
    }

    public void addPreTestMethods(List<ClassTestRunMethod> preTestMethods){
        map.put("preTestMethods",preTestMethods);
    }
}
