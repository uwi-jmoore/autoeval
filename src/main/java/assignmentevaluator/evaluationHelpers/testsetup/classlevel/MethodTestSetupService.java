package assignmentevaluator.evaluationHelpers.testsetup.classlevel;

import java.util.HashMap;
import java.util.List;

import assignmentevaluator.evaluationHelpers.testsetup.TestSetupService;
import assignmenttests.classlevel.products.method.supports.ClassTestAttributeTestValue;
import assignmenttests.classlevel.products.method.supports.ClassTestRunMethod;
import assignmenttests.classlevel.products.method.supports.MethodReturn;

/**
 * Abstract class that provides a template for setting up method-related test configurations.
 * This class is intended to be extended by other classes that set up tests for methods within a class.
 * It holds and manages various test setup details such as method name, return type, static status,
 * modified class attributes, and pre-test methods.
 */
public abstract class MethodTestSetupService extends TestSetupService {

    /**
     * Constructor that initializes the test setup map with default values for method-related test configurations.
     */
    public MethodTestSetupService(){
        map = new HashMap<>();
        map.put("methodName", null);
        map.put("methodReturn", null);
        map.put("isStatic", null);
        map.put("testModifiedClassAttributes", null);
        map.put("preTestMethods", null);
    }

    /**
     * Adds the name of the method to the test setup map.
     *
     * @param methodName the name of the method to be tested.
     */
    public void addMethodName(String methodName){
        map.put("methodName", methodName);
    }

    /**
     * Adds the return type of the method to the test setup map.
     *
     * @param methodReturn the return type of the method to be tested.
     */
    public void addMethodReturn(MethodReturn methodReturn){
        map.put("methodReturn", methodReturn);
    }

    /**
     * Specifies whether the method is static or not in the test setup.
     *
     * @param isMethodStatic true if the method is static, false otherwise.
     */
    public void addIsStatic(boolean isMethodStatic){
        map.put("isStatic", isMethodStatic);
    }

    /**
     * Adds the list of modified class attributes that are affected by the method being tested.
     *
     * @param testModifiedValues the list of modified class attributes.
     */
    public void addTestModAttrs(List<ClassTestAttributeTestValue> testModifiedValues){
        map.put("testModifiedClassAttributes", testModifiedValues);
    }

    /**
     * Adds the list of pre-test methods that should be executed before testing the method itself.
     *
     * @param preTestMethods the list of pre-test methods to be executed.
     */
    public void addPreTestMethods(List<ClassTestRunMethod> preTestMethods){
        map.put("preTestMethods", preTestMethods);
    }
}
