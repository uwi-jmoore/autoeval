package assignmenttests.classlevel.products.method.concrete;

import assignmenttests.classlevel.ClassTestBase;
import assignmenttests.classlevel.products.method.supports.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static assignmenttests.classlevel.ClassLevelHelpers.findMissingKeys;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ConstructorTest extends ClassTestBase implements ParameterComposed, ModifiesClassAttributes {
    private int index;
    private static List<ClassTestParameter> parameterInput;
    private static List<ClassTestAttributeExpectedValue> methodModifiedClassAttributes;


    @Override
    public String toString() {
        return "Constructor Test";
    }


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

    private void setConstructorTestDetails(Map<String, Object> setUpContent){
        Object constructorParams = setUpContent.get("methodParameterInputs");
        Object constructorAffectedAttrs = setUpContent.get("methodModifiedClassAttributes");
        parameterInput = null;
        methodModifiedClassAttributes = null;
        loadMethodParameters(constructorParams);
        loadModifiedClassAttributes(constructorAffectedAttrs);
    }

    @Override
    public void loadMethodParameters(Object mapListValue) {
        if(mapListValue instanceof List<?> tempList && tempList.getFirst() instanceof ClassTestParameter){
            @SuppressWarnings("unchecked")
            List<ClassTestParameter> list = (List<ClassTestParameter>) mapListValue;
            setParameterInput(list);
        }
    }

    @Override
    public void loadModifiedClassAttributes(Object mapListValue) {
        if(mapListValue instanceof List<?> tempList && tempList.getFirst() instanceof ClassTestAttributeExpectedValue){
            @SuppressWarnings("unchecked")
            List<ClassTestAttributeExpectedValue> list = (List<ClassTestAttributeExpectedValue>) mapListValue;
            setModifiedClassAttributes(list);
        }
    }

    @Override
    public void setParameterInput(List<ClassTestParameter> inputParams) {
        ConstructorTest.parameterInput = inputParams;
    }

    @Override
    public void setModifiedClassAttributes(List<ClassTestAttributeExpectedValue> inputAttributes) {
        ConstructorTest.methodModifiedClassAttributes = inputAttributes;
    }

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

    private Constructor<?>[] getSortedConstructors(){
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

    private int getUniqueAttributesAffected(){
        Set<ClassTestAttribute> uniqueAttributes = new HashSet<>(methodModifiedClassAttributes);
        return uniqueAttributes.size();
    }

    private boolean handleConstructorTest(Object classInstance){
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

    private Object getConstructorInstance(Constructor<?> constructor)
        throws InvocationTargetException, InstantiationException, IllegalAccessException {
        return constructor.newInstance(getInputParametersValues());
//        if(constructor.getParameterCount() == 0){//Parameterless Constructor
//            return constructor.newInstance();
//        }else{//Constructor with Parameter(s)
//            return constructor.newInstance(getInputParametersValues());
//        }
    }


    @BeforeEach
    public void initializeTest(){
        super.classTestBaseSetUp();
    }

    @Test
    @DisplayName("Constructor Existence Test")
    public void testConstructorExistence() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<?>[] constructors = getSortedConstructors();
        for(Constructor<?> constructor : constructors){
            assertNotNull(getConstructorInstance(constructor),"New instance of constructor could not be initialized"); //test creation of new class instance
        }
    }

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
