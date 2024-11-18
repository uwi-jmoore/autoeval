package assignmenttests.classlevel.products.method.concrete;
import assignmenttests.classlevel.products.method.MethodTest;
import assignmenttests.classlevel.products.method.supports.*;
import org.junit.jupiter.api.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static assignmentevaluator.evaluationHelpers.EvalHelpers.getOuterClassAttribute;
import static assignmenttests.classlevel.ClassLevelHelpers.findMissingKeys;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ModifierTest extends MethodTest implements ParameterComposed, ModifiesClassAttributes {
    private static List<ClassTestAttributeExpectedValue> methodModifiedClassAttributes;
    private static List<ClassTestParameter> methodParameterInput;


    @Override
    public String toString() {
        return "Modifier(Setter, etc) Method Test";
    }

    @Override
    public void setUpTestDetails(Map<String, Object> setUpContent) {
        List<String> expectedSetupContents = Arrays.asList(
            "methodName",
            "methodReturn",
            "isStatic",
            "methodParameterInputs",
            "methodModifiedClassAttributes",
            "testModifiedClassAttributes"
        );
        List<String> missingKeys = findMissingKeys(setUpContent,expectedSetupContents);
        if(missingKeys.isEmpty()){
            setMethodTestDetails(setUpContent);
        }else{
            System.err.println("Missing keys in setup Map: "+missingKeys);
        }

    }
    public void setParameterInput(List<ClassTestParameter> inputParams){
        ModifierTest.methodParameterInput = inputParams;
    }

    public void setModifiedClassAttributes(List<ClassTestAttributeExpectedValue> inputAttributes){
        ModifierTest.methodModifiedClassAttributes = inputAttributes;
    }


    protected void setMethodTestDetails(Map<String, Object> setUpContent){
        super.setMethodTestDetails(setUpContent);
        Object methodParams = setUpContent.get("methodParameterInputs");
        Object methodAffectedAttrs = setUpContent.get("methodModifiedClassAttributes");
        methodModifiedClassAttributes = null;
        methodParameterInput = null;
        loadMethodParameters(methodParams);
        loadModifiedClassAttributes(methodAffectedAttrs);
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

    protected void stdMethodTestInit(){
        super.stdMethodTestInit();
        loadMethod();
    }

    protected void loadMethod(){
        try {
            MethodTest.method = loadedClass.getDeclaredMethod(methodName,getInputParameterTypes());
        } catch (NoSuchMethodException noSuchMethodException) {
            System.err.println("Could not load method "+ methodName +
                ". Reason: "+ noSuchMethodException.getMessage());
        }
        method.setAccessible(true);
    }


    private boolean expectedClassChanges(Object classInstance){
        return matchModifiedClassAttributes(classInstance, methodModifiedClassAttributes);
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
                if (modAttr.getName().equals("messageNumber")){
                    Field otherLoaded = getOuterClassAttribute(modAttr.getName(),testLoader,"ChatBot"); //Hardcoding here
                    otherLoaded.setAccessible(true);
                    return recheckField(otherLoaded,modAttr);
                }else{
                    System.err.println("NoSuchFieldException. Attribute: "
                        + modAttr.getName() + " not found in class." +
                        "Reason: " + noSuchFieldException.getMessage());
                    return false;
                }
            } catch (IllegalAccessException illegalAccessException) {
                System.err.println("Illegal Access Exception occurred loading instance of attribute "
                    + modAttr.getName()
                    + ". Reason: " + illegalAccessException.getMessage());
                return false;
            }
        }
        return true;
    }

    private boolean recheckField(Field field,ClassTestAttributeExpectedValue modAttr){
        if (!field.getType().equals(modAttr.getDatatype())) {
            return false;
        }
        if(modAttr.getExpectedAttributeValue() == null){
            String temp;
            try {
                temp = field.get(classInstance).toString();
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            return temp.substring(1, temp.length() - 1).trim().isEmpty();
        }else{
            try {
                Object fieldVal = field.get(classInstance);
                Object expectVal = modAttr.getExpectedAttributeValue();
                return fieldVal.equals(expectVal);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //gets a list of parametersValues
    public Object[] getInputParametersValues(){
        if(methodParameterInput == null){
            return null;
        }
        List<Object> paramArray = new ArrayList<>();
        for(ClassTestParameter classTestParameter: methodParameterInput){
            paramArray.add(classTestParameter.value());
        }

        return paramArray.toArray(new Object[0]);
    }

    //gets a list of parameters types
    public Class<?>[] getInputParameterTypes(){
        if(methodParameterInput==null){
            return null;
        }
        List<Class<?>> paramArray = new ArrayList<>();
        for(ClassTestParameter classTestParameter: methodParameterInput){
            paramArray.add(classTestParameter.datatype());
        }
        return paramArray.toArray(new Class<?>[0]);
    }

    @Override
    protected Object getTestResponse() {
        try {
            return method.invoke(classInstance,getInputParametersValues());
        } catch (IllegalAccessException illegalAccessException) {
            System.err.println("Trying to invoke method using class instance caused IllegalAccessException. Reason: "
                + illegalAccessException.getMessage());
        } catch (InvocationTargetException invocationTargetException) {
            System.err.println("Trying to invoke method using class instance caused InvocationTargetException. Reason: "
                + invocationTargetException.getMessage());
        }
        return null;
    }

    @BeforeEach
    public void initializeTest(){
        stdMethodTestInit();
        testResponse = getTestResponse();
    }

    @Test
    @DisplayName("Response Existence Test")
    public void testModifierMethodResponseExistence(){
        assertNotNull(testResponse,"Did not get a response");
    }

    @Test
    @DisplayName("Response Datatype Correctness Test")
    public void testModifierMethodDatatypeResponse(){
        assertEquals(testResponse.getClass(),methodReturn.datatype(),"Response Datatype is incorrect");
    }

    @Test
    @DisplayName("Response Data Match Test")
    public void testModifierMethodResponseContent(){
        assertTrue(testMethodReturnSimilarity(testResponse),
            "What was returned ["+testResponse +"] did not match expected ["+methodReturn.returnValue()+"]");
    }

    @Test
    @DisplayName("Class Attribute Modification Test")
    public void testModifierMethodClassAttributeChange(){
        assertTrue(methodModifiedClassAttributes == null || expectedClassChanges(classInstance),"Expected changes to class attributes not found");
    }

    @AfterAll
    public void resetAllInitializations(){
        resetClassAttribute();
    }

}
