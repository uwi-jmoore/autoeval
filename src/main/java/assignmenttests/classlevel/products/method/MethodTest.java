package assignmenttests.classlevel.products.method;

import assignmenttests.classlevel.ClassTestBase;
import assignmenttests.classlevel.products.method.supports.*;

import java.lang.reflect.*;
import java.util.*;

import static assignmentevaluator.evaluationHelpers.EvalHelpers.getOuterClassAttribute;
import static assignmenttests.classlevel.ClassLevelHelpers.findMissingKeys;

public abstract class MethodTest extends ClassTestBase {
    protected static Method method;

    protected static String methodName;
    protected static MethodReturn methodReturn;
    protected static boolean isMethodStatic;
    protected static List<ClassTestAttributeTestValue> testModifiedClassAttributes;
    protected static List<ClassTestRunMethod> preTestMethods;

    protected Map<Field,Object> testModifiedAttributes;
    protected boolean setupTriggered;

    protected Object testResponse;
    protected Object classInstance;

    protected void setMethodName(String inputMethodName){
        MethodTest.methodName = inputMethodName;
    }

    protected void setMethodReturn (MethodReturn mr){
        MethodTest.methodReturn = mr;
    }

    protected void setIsMethodStatic (boolean isStatic){
        MethodTest.isMethodStatic = isStatic;
    }

    protected void setTestModifiedClassAttributes (List<ClassTestAttributeTestValue> tv){
        MethodTest.testModifiedClassAttributes = tv;
    }

    protected void setPreTestMethods(List<ClassTestRunMethod> testRunMethods){
        MethodTest.preTestMethods = testRunMethods;
    }

    @Override
    public void setUpTestDetails(Map<String, Object> setUpContent) {
        List<String> expectedSetupContents = Arrays.asList(
            "methodName",
            "methodReturn",
            "isStatic",
            "testModifiedClassAttributes",
            "preTestMethods"
        );
        List<String> missingKeys = findMissingKeys(setUpContent,expectedSetupContents);
        if(missingKeys.isEmpty()){
            setMethodTestDetails(setUpContent);
        }else{
            System.err.println("Missing keys in setup Map: "+missingKeys);
        }
    }

    protected void setMethodTestDetails(Map<String, Object> setUpContent){
        setMethodName((String) setUpContent.get("methodName"));
        setMethodReturn((MethodReturn) setUpContent.get("methodReturn"));
        setIsMethodStatic((boolean) setUpContent.get("isStatic"));
        testModifiedClassAttributes = null;
        preTestMethods = null;
        handleLoadingAttributeTestValues(setUpContent.get("testModifiedClassAttributes"));
        handleLoadingPreTestMethods(setUpContent.get("preTestMethods"));
    }
//
    protected void stdMethodTestInit(){
        super.classTestBaseSetUp();
        classInstance = getInitClassInstance(loadedClass);
        if(testModifiedClassAttributes != null){
            handleClassAttributeValueSetup();
        }
        if(preTestMethods != null){
            handleMethodPreRunning();
        }
    }

    protected boolean testMethodReturnSimilarity(Object response){
        String[] substrings = methodReturn.returnValue().toString().split("@");
        for (String substring : substrings) {
            if (!response.toString().contains(substring)) {
                return false;
            }
        }
        return true;
    }

    protected Object getInitClassInstance(Class<?> sourceClass){
        Object instance = null;
        try {
            ClassTestRunMethod constructorPreLoad = getConstructorPreLoadMethod();
            if(constructorPreLoad != null && constructorPreLoad.getMethodParams() != null){
                Class<?>[] consParamTypes = constructorPreLoad
                    .getMethodParams()
                    .stream()
                    .map(ClassTestParameter::datatype)
                    .toArray(Class<?>[]::new);

                Object[] consParamValues = constructorPreLoad
                    .getMethodParams()
                    .stream()
                    .map(ClassTestParameter::value)
                    .toArray(Object[]::new);
                instance = sourceClass.getDeclaredConstructor(consParamTypes).newInstance(consParamValues);
            }else{
                instance = sourceClass.getDeclaredConstructor().newInstance();
            }

        }catch (NoSuchMethodException noSuchMethodException){
            System.err.println("Trying to get class instance caused NoSuchMethodException. Reason: "
                +noSuchMethodException.getMessage());
        }catch (InvocationTargetException invocationTargetException) {
            System.err.println("Trying to create new instance caused InvocationTargetException. Reason: "
                + invocationTargetException.getMessage());
        } catch (InstantiationException instantiationException) {
            System.err.println("Trying to create new instance caused InstantiationException. Reason: "
                + instantiationException.getMessage());
        } catch (IllegalAccessException illegalAccessException) {
            System.err.println("Trying to create new instance caused IllegalAccessException. Reason: "
                + illegalAccessException.getMessage());
        }
        return instance;
    }

    protected Object getTestResponse(){
        try {
            return method.invoke(classInstance);
        } catch (IllegalAccessException illegalAccessException) {
            System.err.println("Trying to invoke method using class instance caused IllegalAccessException. Reason: "
                + illegalAccessException.getMessage());
        } catch (InvocationTargetException invocationTargetException) {
            System.err.println("Trying to invoke method using class instance caused InvocationTargetException. Reason: "
                + invocationTargetException.getMessage());
        }
        return null;
    }

    public void handleLoadingAttributeTestValues(Object testValues){
        if(testValues instanceof List<?> tempList && tempList.getFirst() instanceof ClassTestAttributeTestValue){
            @SuppressWarnings("unchecked")
            List<ClassTestAttributeTestValue> list = (List<ClassTestAttributeTestValue>) testValues;
            setTestModifiedClassAttributes(list);
        }
    }

    public void handleLoadingPreTestMethods(Object testMethods){
        if(testMethods instanceof List<?> tempList && tempList.getFirst() instanceof ClassTestRunMethod){
            @SuppressWarnings("unchecked")
            List<ClassTestRunMethod> list = (List<ClassTestRunMethod>) testMethods;
            setPreTestMethods(list);
        }
    }

    protected void handleMethodPreRunning(){
        for(ClassTestRunMethod preLoadMethod : preTestMethods){
            try {
                Class<?> classToLoad = testLoader.loadClass(preLoadMethod.getClassName());
                try{
                    Class<?>[] loadMethodParamsTypes = preLoadMethod.getMethodParams() == null ?
                        null :
                        preLoadMethod.getMethodParams()
                        .stream()
                        .map(ClassTestParameter::datatype)
                        .toArray(Class<?>[]::new);

                    Method loadedMethod = classToLoad.getDeclaredMethod(
                        preLoadMethod.getMethodName(), loadMethodParamsTypes
                    );
                    loadedMethod.setAccessible(true);
                    Object preloadClassInstance = classInstance;
                    try{
                        Object[] loadMethodParamValues = preLoadMethod.getMethodParams() == null ?
                            null :
                            preLoadMethod.getMethodParams()
                            .stream()
                            .map(ClassTestParameter::value)
                            .toArray(Object[]::new);

                        loadedMethod.invoke(preloadClassInstance,loadMethodParamValues);


                    } catch (InvocationTargetException invocationTargetException) {
                        System.err.println("Failed to invoke pre load method "
                            + preLoadMethod.getMethodName()
                            +", InvocationTargetException Occurred. Reason: "
                            +invocationTargetException.getMessage());

                    } catch (IllegalAccessException illegalAccessException) {
                        System.err.println("Failed to invoke pre load method "
                            + preLoadMethod.getMethodName()
                            +", IllegalAccessException Occurred. Reason: "
                            +illegalAccessException.getMessage());
                    }
                }catch (NoSuchMethodException noSuchMethodException){
                    if(!isMethodConstructor(preLoadMethod)){
                        System.err.println("Failed to load method "+ preLoadMethod.getMethodName()
                            + "for pre test run, NoSuchMethodException occurred. Reason: "
                            + noSuchMethodException.getMessage());
                    }
                }
            } catch (ClassNotFoundException classNotFoundExceptionA) {
                System.err.println("Failed to load class " +preLoadMethod.getClassName()+
                    " for pre test run method, ClassNotFoundException occurred. Reason: "
                    + classNotFoundExceptionA.getMessage());
            }
        }
    }

    protected void handleClassAttributeValueSetup(){
        setupTriggered = true;
        testModifiedAttributes = new HashMap<>();
        for(ClassTestAttributeTestValue testModifiedClassAttribute : testModifiedClassAttributes){
            if(testModifiedClassAttribute.getTestSetAttributeValue()!=null){
                Field setUpField = null;
                try {
                    setUpField = loadedClass.getDeclaredField(testModifiedClassAttribute.getName());
                } catch (NoSuchFieldException noSuchFieldException) {
                    if(Objects.equals(testModifiedClassAttribute.getName(), "messageNumber")){
                        setUpField = getOuterClassAttribute("messageNumber",testLoader,"ChatBot");
                    }else{
                        System.err.println("Could not load field "+ testModifiedClassAttribute.getName()
                            +" for test setup, NoSuchFieldException occurred. Reason: " + noSuchFieldException.getMessage());
                    }
                }
                assert setUpField != null;
                setUpField.setAccessible(true);
                try {
                    Object preSetValue = setUpField.get(
                        Modifier.isStatic(setUpField.getModifiers()) ?
                            null :
                            classInstance
                    );
                    setUpField.set(null, testModifiedClassAttribute.getTestSetAttributeValue());
                    testModifiedAttributes.put(setUpField,preSetValue);
                } catch (IllegalAccessException illegalAccessException) {
                    System.err.println("Could not set value for field "+ testModifiedClassAttribute.getName()
                        +" for test setup, IllegalAccessException occurred. Reason: " + illegalAccessException.getMessage());
                }
            }
        }
    }

    public void loadMethod(Class<?> loadedSourceClass, String methodName){
        method = null;
        try {
            method = loadedSourceClass.getDeclaredMethod(methodName);
            method.setAccessible(true);
        }catch (NoSuchMethodException noSuchMethodException) {
            System.err.println("Could not load method "+ methodName +
                ". Reason: "+ noSuchMethodException.getMessage());
        }
    }

    protected boolean isMethodConstructor(ClassTestRunMethod preLoadMethod){
        return preLoadMethod.getMethodName().equals(preLoadMethod.getClassName());
    }
    protected ClassTestRunMethod getConstructorPreLoadMethod(){
        for(ClassTestRunMethod classTestRunMethod : preTestMethods){
            if(isMethodConstructor(classTestRunMethod)){
                return classTestRunMethod;
            }
        }
        return null;
    }

    protected void resetClassAttribute(){
        if(setupTriggered){
            for(Map.Entry<Field,Object> changedAttribute: testModifiedAttributes.entrySet()){
                Field setUpField = null;
                String changedAttributeName = changedAttribute.getKey().getName();
                try {
                    setUpField = loadedClass.getDeclaredField(changedAttributeName);
                } catch (NoSuchFieldException noSuchFieldException) {
                    if(Objects.equals(changedAttribute.getKey().getName(), "messageNumber")){
                        try {
                            Class<?> otherClass = testLoader.loadClass("ChatBot");
                            setUpField = otherClass.getDeclaredField(changedAttribute.getKey().getName());

                        } catch (NoSuchFieldException | ClassNotFoundException e) {
                            System.err.println("After attempt Could not load field "+ changedAttribute.getKey().getName()
                                +" for test reset, NoSuchFieldException occurred. Reason: " + e.getMessage());
                        }
                    }else{
                        System.err.println("Could not load field "+ changedAttribute.getKey().getName()
                            +" for test reset, NoSuchFieldException occurred. Reason: " + noSuchFieldException.getMessage());
                    }
                }
                assert setUpField!=null;
                setUpField.setAccessible(true);
                try {
                    setUpField.set(null,changedAttribute.getValue());

                } catch (IllegalAccessException illegalAccessException) {
                    System.err.println("Could not set value for field "+changedAttributeName
                        +" for test setup, IllegalAccessException occurred. Reason: " + illegalAccessException.getMessage());
                }
            }
            setupTriggered = false;
        }
    }



}
