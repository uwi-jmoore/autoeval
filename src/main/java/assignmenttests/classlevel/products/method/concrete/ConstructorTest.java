package assignmenttests.classlevel.products.method.concrete;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static assignmenttests.classlevel.ClassLevelHelpers.findMissingKeys;
import assignmenttests.classlevel.ClassTestBase;
import assignmenttests.classlevel.products.method.supports.ClassTestAttribute;
import assignmenttests.classlevel.products.method.supports.ClassTestAttributeExpectedValue;
import assignmenttests.classlevel.products.method.supports.ClassTestParameter;
import assignmenttests.classlevel.products.method.supports.ModifiesClassAttributes;
import assignmenttests.classlevel.products.method.supports.ParameterComposed;

/**
 * Test class for verifying constructor behavior in class-level testing.
 * This class extends {@link ClassTestBase} and implements 
 * {@link ParameterComposed} and {@link ModifiesClassAttributes} interfaces.
 */

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ConstructorTest extends ClassTestBase implements ParameterComposed, ModifiesClassAttributes {
    protected int index;
    protected static List<ClassTestParameter> parameterInput;
    protected static List<ClassTestAttributeExpectedValue> methodModifiedClassAttributes;


    /**
     * Returns the string representation of this test class.
     * @return String "Constructor Test".
     */

    @Override
    public String toString() {
        return "Constructor Test";
    }

      /**
     * Sets up the test details based on the provided map content.
     * Verifies the required keys are present and loads necessary parameters.
     * @param setUpContent A map containing setup data such as method parameters and modified attributes.
     */
    
    public void setUpTestDetails(Map<String, Object> setUpContent) {
        List<String> expectedSetupContents = List.of(
            "methodParameterInputs",
            "methodModifiedClassAttributes"
        );
        List<String> missingKeys = findMissingKeys(setUpContent,expectedSetupContents);
        if(missingKeys.isEmpty()){
            setConstructorTestDetails(setUpContent);
        }else{
            System.err.println("Missing keys in setup Map: "+missingKeys);
        }
    }


     /**
     * Loads constructor parameters and modified class attributes from the provided setup content.
     * @param setUpContent The map containing the setup data.
     */

    protected void setConstructorTestDetails(Map<String, Object> setUpContent){
        Object constructorParams = setUpContent.get("methodParameterInputs");
        Object constructorAffectedAttrs = setUpContent.get("methodModifiedClassAttributes");
        parameterInput = null;
        methodModifiedClassAttributes = null;
        loadMethodParameters(constructorParams);
        loadModifiedClassAttributes(constructorAffectedAttrs);
    }

     /**
     * Loads constructor parameters from the provided object.
     * @param mapListValue The object containing a list of constructor parameters.
     */
    @Override
    public void loadMethodParameters(Object mapListValue) {
        if(mapListValue instanceof List<?> tempList && tempList.getFirst() instanceof ClassTestParameter){
            @SuppressWarnings("unchecked")
            List<ClassTestParameter> list = (List<ClassTestParameter>) mapListValue;
            setParameterInput(list);
        }
    }

     /**
     * Loads the modified class attributes from the provided object.
     * @param mapListValue The object containing a list of expected modified class attributes.
     */

    @Override
    public void loadModifiedClassAttributes(Object mapListValue) {
        if(mapListValue instanceof List<?> tempList && tempList.getFirst() instanceof ClassTestAttributeExpectedValue){
            @SuppressWarnings("unchecked")
            List<ClassTestAttributeExpectedValue> list = (List<ClassTestAttributeExpectedValue>) mapListValue;
            setModifiedClassAttributes(list);
        }
    }

     /**
     * Sets the constructor parameters for this test class.
     * @param inputParams The list of constructor parameters.
     */

    @Override
    public void setParameterInput(List<ClassTestParameter> inputParams) {
        ConstructorTest.parameterInput = inputParams;
    }

     /**
     * Sets the modified class attributes for this test class.
     * @param inputAttributes The list of expected modified class attributes.
     */

    @Override
    public void setModifiedClassAttributes(List<ClassTestAttributeExpectedValue> inputAttributes) {
        ConstructorTest.methodModifiedClassAttributes = inputAttributes;
    }

     /**
     * Returns the values of the input parameters.
     * @return An array of input parameter values.
     */

    @Override
    public Object[] getInputParametersValues() {
        if(parameterInput == null){
            return null;
        }
        List<Object> paramArray = new ArrayList<>();
        for(ClassTestParameter classTestParameter: parameterInput){
            paramArray.add(classTestParameter.value());
        }

        return paramArray.toArray(new Object[0]);
    }

     /**
     * Returns the types of the input parameters.
     * @return An array of input parameter types.
     */

    @Override
    public Class<?>[] getInputParameterTypes() {
        if(parameterInput ==null){
            return null;
        }
        List<Class<?>> paramArray = new ArrayList<>();
        for(ClassTestParameter classTestParameter: parameterInput){
            paramArray.add(classTestParameter.datatype());
        }
        return paramArray.toArray(new Class<?>[0]);
    }

     /**
     * Returns the constructors of the loaded class, sorted by the number of parameters and parameter types.
     * @return An array of sorted constructors.
     */

    protected Constructor<?>[] getSortedConstructors(){
        Constructor<?>[] constructors = loadedClass.getConstructors();
        Arrays.sort(constructors, (c1, c2) -> {
            //Sorting by parameters ascending
            int paramCountCompare = Integer.compare(c1.getParameterCount(), c2.getParameterCount());
            if (paramCountCompare != 0) {
                return paramCountCompare;
            }
            //Sorting by parameters type name ascending
            Class<?>[] paramTypes1 = c1.getParameterTypes();
            Class<?>[] paramTypes2 = c2.getParameterTypes();
            for (int i = 0; i < paramTypes1.length && i < paramTypes2.length; i++) {
                int typeCompare = paramTypes1[i].getName().compareTo(paramTypes2[i].getName());
                if (typeCompare != 0) {
                    return typeCompare;
                }
            }
            return 0;
        });
        return constructors;
    }


     /**
     * Returns the number of unique attributes affected by the constructor.
     * @return The number of unique affected attributes.
     */

    protected int getUniqueAttributesAffected(){
        Set<ClassTestAttribute> uniqueAttributes = new HashSet<>(methodModifiedClassAttributes);
        return uniqueAttributes.size();
    }

     /**
     * Handles the constructor test and checks if the expected modified attributes match the class instance.
     * @param classInstance The instance of the class being tested.
     * @return True if the modified attributes match the expected values, otherwise false.
     */

    protected boolean handleConstructorTest(Object classInstance){
        int constructorCount = loadedClass.getConstructors().length;
        int modifiedClassAttributeListSize = methodModifiedClassAttributes.size();
        if((modifiedClassAttributeListSize / getUniqueAttributesAffected()) == constructorCount){
            List<ClassTestAttributeExpectedValue> subList = methodModifiedClassAttributes
                .subList(
                    index,
                    index + getUniqueAttributesAffected()
                );
            return matchModifiedClassAttributes(classInstance,subList);
        }else {
            System.err.println("Incorrect Number of ClassTestAttributes given to methodModifiedClassAttributes. " +
                "Expected "+ constructorCount * getUniqueAttributesAffected()+
                "elements, got "+modifiedClassAttributeListSize);
        }
        return false;
    }


    /**
     * Matches the modified class attributes with the expected values for the given class instance.
     * @param classInstance The instance of the class being tested.
     * @param modifiedAttributes The list of expected modified attributes.
     * @return True if the modified attributes match the expected values, otherwise false.
     */

    public boolean matchModifiedClassAttributes(Object classInstance, List<ClassTestAttributeExpectedValue> modifiedAttributes){
        for (ClassTestAttributeExpectedValue modAttr : modifiedAttributes) {
            try {
                Field field = loadedClass.getDeclaredField(modAttr.getName());
                field.setAccessible(true);
                if (!field.getType().equals(modAttr.getDatatype())) {
                    return false;
                }
                if(modAttr.getExpectedAttributeValue() == null){
                    String temp = field.get(classInstance).toString();
                    return temp.substring(1, temp.length() - 1).trim().isEmpty();
                }else{
                    return field.get(classInstance).equals(modAttr.getExpectedAttributeValue());
                }
            } catch (NoSuchFieldException noSuchFieldException) {
                System.err.println("NoSuchFieldException. Attribute: "
                    + modAttr.getName() + " not found in class." +
                    "Reason: " + noSuchFieldException.getMessage());
                return false;
            } catch (IllegalAccessException illegalAccessException) {
                System.err.println("Illegal Access Exception occurred loading instance of attribute "
                    + modAttr.getName()
                    + ". Reason: " + illegalAccessException.getMessage());
                return false;
            }
        }
        return true;
    }

      /**
     * Creates a new instance of the constructor with the input parameter values.
     * @param constructor The constructor to create an instance of.
     * @return The new instance created by the constructor.
     * @throws InvocationTargetException If the constructor throws an exception.
     * @throws InstantiationException If the class cannot be instantiated.
     * @throws IllegalAccessException If access to the constructor is illegal.
     */

    protected Object getConstructorInstance(Constructor<?> constructor)
        throws InvocationTargetException, InstantiationException, IllegalAccessException {
        return constructor.newInstance(getInputParametersValues());
    }

    /**
     * Initializes the test setup before each test method is executed.
     */

    @BeforeEach
    public void initializeTest(){
        super.classTestBaseSetUp();
    }

       /**
     * Tests the existence of constructors and their ability to initialize a new instance of the class.
     * @throws InvocationTargetException If the constructor throws an exception.
     * @throws InstantiationException If the class cannot be instantiated.
     * @throws IllegalAccessException If access to the constructor is illegal.
     */

    @Test
    @DisplayName("Constructor Existence Test")
    public void testConstructorExistence() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<?>[] constructors = getSortedConstructors();
        for(Constructor<?> constructor : constructors){
            assertNotNull(getConstructorInstance(constructor),"New instance of constructor could not be initialized"); //test creation of new class instance
        }
    }

     /**
     * Tests the constructor for the expected modifications to class attributes.
     * @throws InvocationTargetException If the constructor throws an exception.
     * @throws InstantiationException If the class cannot be instantiated.
     * @throws IllegalAccessException If access to the constructor is illegal.
     */

    @Test
    @DisplayName("Constructor Modified Values Test")
    public void testConstructorEffects() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<?>[] constructors = getSortedConstructors();
        for(Constructor<?> constructor : constructors){
            assertTrue(handleConstructorTest(getConstructorInstance(constructor)),"Expected changes to class attributes not found"); //test value in attribute match expected
            index += getUniqueAttributesAffected();
        }
    }


}
