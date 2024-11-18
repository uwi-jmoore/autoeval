package assignmenttests.classlevel.products.method.concrete;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static assignmentevaluator.evaluationHelpers.EvalHelpers.getOuterClassAttribute;
import static assignmenttests.classlevel.ClassLevelHelpers.findMissingKeys;
import assignmenttests.classlevel.products.method.MethodTest;
import assignmenttests.classlevel.products.method.supports.ClassTestAttributeExpectedValue;
import assignmenttests.classlevel.products.method.supports.ClassTestParameter;
import assignmenttests.classlevel.products.method.supports.ModifiesClassAttributes;
import assignmenttests.classlevel.products.method.supports.ParameterComposed;

/**
 * The ModifierTest class is responsible for testing modifier methods (setters, etc.)
 * in a class. It extends the MethodTest class and implements interfaces for parameter
 * composition and modification of class attributes.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ModifierTest extends MethodTest implements ParameterComposed, ModifiesClassAttributes {
    
    private static List<ClassTestAttributeExpectedValue> methodModifiedClassAttributes; // Holds expected modified class attributes
    private static List<ClassTestParameter> methodParameterInput; // Holds input parameters for the method

    /**
     * Returns a string representation of the ModifierTest instance.
     *
     * @return String representation of the test type.
     */
    @Override
    public String toString() {
        return "Modifier(Setter, etc) Method Test";
    }

    /**
     * Sets up the test details based on the provided setup content.
     *
     * @param setUpContent A map containing setup details for the test.
     */
    @Override
    public void setUpTestDetails(Map<String, Object> setUpContent) {
        List<String> expectedSetupContents = Arrays.asList(
            "methodName", "methodReturn", "isStatic", "methodParameterInputs", "methodModifiedClassAttributes", "testModifiedClassAttributes"
        );
        List<String> missingKeys = findMissingKeys(setUpContent, expectedSetupContents);
        if (missingKeys.isEmpty()) {
            setMethodTestDetails(setUpContent);
        } else {
            System.err.println("Missing keys in setup Map: " + missingKeys);
        }
    }

    /**
     * Sets the input parameters for the method being tested.
     *
     * @param inputParams A list of parameters to be used as input for the method.
     */
    public void setParameterInput(List<ClassTestParameter> inputParams) {
        ModifierTest.methodParameterInput = inputParams;
    }

    /**
     * Sets the modified class attributes expected after method execution.
     *
     * @param inputAttributes A list of expected modified class attributes.
     */
    public void setModifiedClassAttributes(List<ClassTestAttributeExpectedValue> inputAttributes) {
        ModifierTest.methodModifiedClassAttributes = inputAttributes;
    }

    /**
     * Initializes method test details from setup content.
     *
     * @param setUpContent A map containing setup details for the test.
     */
    protected void setMethodTestDetails(Map<String, Object> setUpContent) {
        super.setMethodTestDetails(setUpContent);
        Object methodParams = setUpContent.get("methodParameterInputs");
        Object methodAffectedAttrs = setUpContent.get("methodModifiedClassAttributes");
        methodModifiedClassAttributes = null;
        methodParameterInput = null;
        loadMethodParameters(methodParams);
        loadModifiedClassAttributes(methodAffectedAttrs);
    }

    /**
     * Loads method parameters from the provided list into the test framework.
     *
     * @param mapListValue The list of parameters to be loaded.
     */
    @Override
    public void loadMethodParameters(Object mapListValue) {
        if (mapListValue instanceof List<?> tempList && tempList.getFirst() instanceof ClassTestParameter) {
            @SuppressWarnings("unchecked")
            List<ClassTestParameter> list = (List<ClassTestParameter>) mapListValue;
            setParameterInput(list);
        }
    }

    /**
     * Loads modified class attributes from the provided list into the test framework.
     *
     * @param mapListValue The list of modified class attributes to be loaded.
     */
    @Override
    public void loadModifiedClassAttributes(Object mapListValue) {
        if (mapListValue instanceof List<?> tempList && tempList.getFirst() instanceof ClassTestAttributeExpectedValue) {
            @SuppressWarnings("unchecked")
            List<ClassTestAttributeExpectedValue> list = (List<ClassTestAttributeExpectedValue>) mapListValue;
            setModifiedClassAttributes(list);
        }
    }

    /**
     * Initializes standard method test setup before executing tests.
     */
    protected void stdMethodTestInit() {
        super.stdMethodTestInit();
        loadMethod();
    }

    /**
     * Loads the specified method from the loaded class using reflection.
     */
    protected void loadMethod() {
        try {
            MethodTest.method = loadedClass.getDeclaredMethod(methodName, getInputParameterTypes());
        } catch (NoSuchMethodException noSuchMethodException) {
            System.err.println("Could not load method " + methodName + ". Reason: " + noSuchMethodException.getMessage());
        }
        method.setAccessible(true);
    }

    /**
     * Checks if expected changes were made to the class instance after method execution.
     *
     * @param classInstance The instance of the class being tested.
     * @return True if expected changes match, false otherwise.
     */
    private boolean expectedClassChanges(Object classInstance) {
        return matchModifiedClassAttributes(classInstance, methodModifiedClassAttributes);
    }

    /**
     * Matches modified class attributes against expected values after method execution.
     *
     * @param classInstance The instance of the class being tested.
     * @param modifiedAttributes The list of expected modified attributes.
     * @return True if all attributes match, false otherwise.
     */
    public boolean matchModifiedClassAttributes(Object classInstance, List<ClassTestAttributeExpectedValue> modifiedAttributes) {
        for (ClassTestAttributeExpectedValue modAttr : modifiedAttributes) {
            try {
                Field field = loadedClass.getDeclaredField(modAttr.getName());
                field.setAccessible(true);
                if (!field.getType().equals(modAttr.getDatatype())) {
                    return false;
                }
                if (modAttr.getExpectedAttributeValue() == null) {
                    String temp = field.get(classInstance).toString();
                    return temp.substring(1, temp.length() - 1).trim().isEmpty();
                } else {
                    return field.get(classInstance).equals(modAttr.getExpectedAttributeValue());
                }
            } catch (NoSuchFieldException noSuchFieldException) {
                if (modAttr.getName().equals("messageNumber")) {
                    Field otherLoaded = getOuterClassAttribute(modAttr.getName(), testLoader, "ChatBot");
                    otherLoaded.setAccessible(true);
                    return recheckField(otherLoaded, modAttr);
                } else {
                    System.err.println("NoSuchFieldException. Attribute: " + modAttr.getName() + " not found in class." + "Reason: " + noSuchFieldException.getMessage());
                    return false;
                }
            } catch (IllegalAccessException illegalAccessException) {
                System.err.println("Illegal Access Exception occurred loading instance of attribute " + modAttr.getName() + ". Reason: " + illegalAccessException.getMessage());
                return false;
            }
        }
        return true;
    }

    /**
     * Rechecks a specific field against expected attribute values after modification.
     *
     * @param field The field to be checked.
     * @param modAttr The expected attribute value object to compare against.
     * @return True if matches, false otherwise.
     */
    private boolean recheckField(Field field, ClassTestAttributeExpectedValue modAttr) {
        if (!field.getType().equals(modAttr.getDatatype())) {
            return false;
        }
        if (modAttr.getExpectedAttributeValue() == null) {
            String temp;
            try {
                temp = field.get(classInstance).toString();
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            return temp.substring(1, temp.length() - 1).trim().isEmpty();
        } else {
            try {
                Object fieldVal = field.get(classInstance);
                Object expectVal = modAttr.getExpectedAttributeValue();
                return fieldVal.equals(expectVal);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

   /**
   * Gets an array of input parameter values for invoking the method under test.
   *
   * @return An array of parameter values or null if no parameters are defined.
   */
   public Object[] getInputParametersValues() {
       if (methodParameterInput == null) { 
           return null; 
       } 
       List<Object> paramArray = new ArrayList<>(); 
       for (ClassTestParameter classTestParameter : methodParameterInput) { 
           paramArray.add(classTestParameter.value()); 
       } 
       return paramArray.toArray(new Object[0]); 
   }

   /**
   * Gets an array of input parameter types for invoking the method under test.
   *
   * @return An array of parameter types or null if no parameters are defined.
   */
   public Class<?>[] getInputParameterTypes() { 
       if (methodParameterInput == null) { 
           return null; 
       } 
       List<Class<?>> paramArray = new ArrayList<>(); 
       for (ClassTestParameter classTestParameter : methodParameterInput) { 
           paramArray.add(classTestParameter.datatype()); 
       } 
       return paramArray.toArray(new Class<?>[0]); 
   }

   /**
   * Invokes the specified method on the class instance and returns its response.
   *
   * @return The response from invoking the method or null if invocation fails.
   */
   @Override
   protected Object getTestResponse() { 
       try { 
           return method.invoke(classInstance, getInputParametersValues()); 
       } catch (IllegalAccessException illegalAccessException) { 
           System.err.println("Trying to invoke method using class instance caused IllegalAccessException. Reason: " + illegalAccessException.getMessage()); 
       } catch (InvocationTargetException invocationTargetException) { 
           System.err.println("Trying to invoke method using class instance caused InvocationTargetException. Reason: " + invocationTargetException.getMessage()); 
       } 
       return null; 
   }

   /**
   * Initializes test before each execution by setting up standard test initialization
   * and obtaining a response from invoking the target method.
   */
   @BeforeEach
   public void initializeTest() { 
       stdMethodTestInit(); 
       testResponse = getTestResponse(); 
   }

   /**
   * Tests that a response is received from invoking the modifier method.
   */
   @Test
   @DisplayName("Response Existence Test")
   public void testModifierMethodResponseExistence() { 
       assertNotNull(testResponse,"Did not get a response"); 
   }

   /**
   * Tests that the datatype of the response matches expected datatype defined in MethodReturn object.
   */
   @Test
   @DisplayName("Response Datatype Correctness Test")
   public void testModifierMethodDatatypeResponse() { 
       assertEquals(testResponse.getClass(),methodReturn.datatype(),"Response Datatype is incorrect"); 
   }

   /**
   * Tests that the content of the response matches expected value defined in MethodReturn object.
   */
   @Test
   @DisplayName("Response Data Match Test")
   public void testModifierMethodResponseContent() { 
       assertTrue(testMethodReturnSimilarity(testResponse), "What was returned ["+testResponse +"] did not match expected ["+methodReturn.returnValue()+"]"); 
   }

   /**
   * Tests that modifications to class attributes were made as expected after invoking modifier methods.
   */
   @Test
   @DisplayName("Class Attribute Modification Test")
   public void testModifierMethodClassAttributeChange() { 
       assertTrue(methodModifiedClassAttributes == null || expectedClassChanges(classInstance),"Expected changes to class attributes not found"); 
   }

   /**
   * Resets all initializations and modifications made during tests after all tests have run.
   */
   @AfterAll
   public void resetAllInitializations() { 
       resetClassAttribute(); 
   }
}