package assignmenttests.classlevel.products.method;

import assignmenttests.classlevel.ClassTestBase;
import assignmenttests.classlevel.products.method.supports.*;
import java.lang.reflect.*;
import java.util.*;
import static assignmentevaluator.evaluationHelpers.EvalHelpers.getOuterClassAttribute;
import static assignmenttests.classlevel.ClassLevelHelpers.findMissingKeys;

/**
 * Abstract class that serves as a base for testing methods within a class.
 * It handles setup, execution, and validation of method tests.
 */
public abstract class MethodTest extends ClassTestBase {
    protected static Method method; // The method to be tested
    protected static String methodName; // Name of the method to be tested
    protected static MethodReturn methodReturn; // Expected return value of the method
    protected static boolean isMethodStatic; // Indicates if the method is static
    protected static List<ClassTestAttributeTestValue> testModifiedClassAttributes; // Attributes modified for testing
    protected static List<ClassTestRunMethod> preTestMethods; // List of methods to run before tests
    protected Map<Field,Object> testModifiedAttributes; // Map of attributes modified during tests
    protected boolean setupTriggered; // Indicates if setup has been triggered
    protected Object testResponse; // Response from the method under test
    protected Object classInstance; // Instance of the class being tested

    /**
     * Sets the name of the method to be tested.
     *
     * @param inputMethodName The name of the method.
     */
    protected void setMethodName(String inputMethodName) {
        MethodTest.methodName = inputMethodName;
    }

    /**
     * Sets the expected return value for the method being tested.
     *
     * @param mr The expected return value.
     */
    protected void setMethodReturn(MethodReturn mr) {
        MethodTest.methodReturn = mr;
    }

    /**
     * Sets whether the method being tested is static.
     *
     * @param isStatic true if the method is static, false otherwise.
     */
    protected void setIsMethodStatic(boolean isStatic) {
        MethodTest.isMethodStatic = isStatic;
    }

    /**
     * Sets modified class attributes for testing.
     *
     * @param tv List of modified class attributes.
     */
    protected void setTestModifiedClassAttributes(List<ClassTestAttributeTestValue> tv) {
        MethodTest.testModifiedClassAttributes = tv;
    }

    /**
     * Sets pre-test methods that should be executed before running tests.
     *
     * @param testRunMethods List of pre-test methods.
     */
    protected void setPreTestMethods(List<ClassTestRunMethod> testRunMethods) {
        MethodTest.preTestMethods = testRunMethods;
    }

    /**
     * Sets up test details based on provided content.
     *
     * @param setUpContent A map containing setup information.
     */
    @Override
    public void setUpTestDetails(Map<String, Object> setUpContent) {
        List<String> expectedSetupContents = Arrays.asList(
            "methodName", "methodReturn", "isStatic", "testModifiedClassAttributes", "preTestMethods"
        );
        List<String> missingKeys = findMissingKeys(setUpContent, expectedSetupContents);
        if (missingKeys.isEmpty()) {
            setMethodTestDetails(setUpContent);
        } else {
            System.err.println("Missing keys in setup Map: " + missingKeys);
        }
    }

    /**
     * Sets detailed properties for the method test based on provided content.
     *
     * @param setUpContent A map containing setup information.
     */
    protected void setMethodTestDetails(Map<String, Object> setUpContent) {
        setMethodName((String) setUpContent.get("methodName"));
        setMethodReturn((MethodReturn) setUpContent.get("methodReturn"));
        setIsMethodStatic((boolean) setUpContent.get("isStatic"));
        testModifiedClassAttributes = null;
        preTestMethods = null;
        handleLoadingAttributeTestValues(setUpContent.get("testModifiedClassAttributes"));
        handleLoadingPreTestMethods(setUpContent.get("preTestMethods"));
    }

    /**
     * Checks if the response from the method matches the expected return value.
     *
     * @param response The actual response from invoking the method.
     * @return true if the responses match, false otherwise.
     */
    protected boolean testMethodReturnSimilarity(Object response) {
        String[] substrings = methodReturn.returnValue().toString().split("@");
        for (String substring : substrings) {
            if (!response.toString().contains(substring)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Initializes an instance of the specified class using its constructor.
     *
     * @param sourceClass The class from which to create an instance.
     * @return An instance of the specified class or null if creation fails.
     */
    protected Object getInitClassInstance(Class<?> sourceClass) {
        Object instance = null;
        try {
            ClassTestRunMethod constructorPreLoad = getConstructorPreLoadMethod();
            if (constructorPreLoad != null && constructorPreLoad.getMethodParams() != null) {
                Class<?>[] consParamTypes = constructorPreLoad.getMethodParams()
                    .stream()
                    .map(ClassTestParameter::datatype)
                    .toArray(Class<?>[]::new);
                Object[] consParamValues = constructorPreLoad.getMethodParams()
                    .stream()
                    .map(ClassTestParameter::value)
                    .toArray(Object[]::new);
                instance = sourceClass.getDeclaredConstructor(consParamTypes).newInstance(consParamValues);
            } else {
                instance = sourceClass.getDeclaredConstructor().newInstance();
            }
        } catch (NoSuchMethodException noSuchMethodException) {
            System.err.println("Trying to get class instance caused NoSuchMethodException. Reason: " + noSuchMethodException.getMessage());
        } catch (InvocationTargetException invocationTargetException) {
            System.err.println("Trying to create new instance caused InvocationTargetException. Reason: " + invocationTargetException.getMessage());
        } catch (InstantiationException instantiationException) {
            System.err.println("Trying to create new instance caused InstantiationException. Reason: " + instantiationException.getMessage());
        } catch (IllegalAccessException illegalAccessException) {
            System.err.println("Trying to create new instance caused IllegalAccessException. Reason: " + illegalAccessException.getMessage());
        }
        return instance;
    }

    /**
     * Invokes the specified method on the class instance and returns its response.
     *
     * @return The response from invoking the method or null if invocation fails.
     */
    protected Object getTestResponse() {
        try {
            return method.invoke(classInstance);
        } catch (IllegalAccessException illegalAccessException) {
            System.err.println("Trying to invoke method using class instance caused IllegalAccessException. Reason: " + illegalAccessException.getMessage());
        } catch (InvocationTargetException invocationTargetException) {
            System.err.println("Trying to invoke method using class instance caused InvocationTargetException. Reason: " + invocationTargetException.getMessage());
        }
        return null;
    }

    /**
     * Handles loading attribute test values from a given object into a list.
     *
     * @param testValues The object containing test values to load.
     */
    public void handleLoadingAttributeTestValues(Object testValues) {
        if (testValues instanceof List<?> tempList && tempList.getFirst() instanceof ClassTestAttributeTestValue) {
            @SuppressWarnings("unchecked")
            List<ClassTestAttributeTestValue> list = (List<ClassTestAttributeTestValue>) testValues;
            setTestModifiedClassAttributes(list);
        }
    }

    /**
     * Handles loading pre-test methods from a given object into a list.
     *
     * @param testMethods The object containing pre-test methods to load.
     */
    public void handleLoadingPreTestMethods(Object testMethods) {
        if (testMethods instanceof List<?> tempList && tempList.getFirst() instanceof ClassTestRunMethod) {
            @SuppressWarnings("unchecked")
            List<ClassTestRunMethod> list = (List<ClassTestRunMethod>) testMethods;
            setPreTestMethods(list);
        }
    }

    /**
     * Executes all pre-test methods defined in preTestMethods list.
     */
    protected void handleMethodPreRunning() {
        for (ClassTestRunMethod preLoadMethod : preTestMethods) {
            try {
                Class<?> classToLoad = testLoader.loadClass(preLoadMethod.getClassName());
                try{
                    Class<?>[] loadMethodParamsTypes = preLoadMethod.getMethodParams() == null ? null : preLoadMethod.getMethodParams()
                        .stream()
                        .map(ClassTestParameter::datatype)
                        .toArray(Class<?>[]::new);
                    Method loadedMethod = classToLoad.getDeclaredMethod(preLoadMethod.getMethodName(), loadMethodParamsTypes);
                    loadedMethod.setAccessible(true);
                    Object preloadClassInstance = classInstance;
                    Object[] loadMethodParamValues = preLoadMethod.getMethodParams() == null ? null : preLoadMethod.getMethodParams()
                        .stream()
                        .map(ClassTestParameter::value)
                        .toArray(Object[]::new);
                    loadedMethod.invoke(preloadClassInstance, loadMethodParamValues);
                } catch (InvocationTargetException invocationTargetException) {
                    System.err.println("Failed to invoke pre load method " + preLoadMethod.getMethodName() + ", InvocationTargetException Occurred. Reason: " + invocationTargetException.getMessage());
                } catch (IllegalAccessException illegalAccessException) {
                    System.err.println("Failed to invoke pre load method " + preLoadMethod.getMethodName() + ", IllegalAccessException Occurred. Reason: " + illegalAccessException.getMessage());
                }
            } catch (NoSuchMethodException noSuchMethodException) {
                if (!isMethodConstructor(preLoadMethod)) {
                    System.err.println("Failed to load method "+ preLoadMethod.getMethodName() + "for pre test run, NoSuchMethodException occurred. Reason: " + noSuchMethodException.getMessage());
                }
            } catch (ClassNotFoundException classNotFoundExceptionA) {
                System.err.println("Failed to load class " +preLoadMethod.getClassName()+ " for pre test run method, ClassNotFoundException occurred. Reason: " + classNotFoundExceptionA.getMessage());
            }
        }
    }

    /**
     * Configures modified attributes for testing based on provided values.
     */
    protected void handleClassAttributeValueSetup() {
        setupTriggered = true;
        testModifiedAttributes = new HashMap<>();
        for (ClassTestAttributeTestValue testModifiedClassAttribute : testModifiedClassAttributes) {
            if (testModifiedClassAttribute.getTestSetAttributeValue() != null) {
                Field setUpField = null;
                try {
                    setUpField = loadedClass.getDeclaredField(testModifiedClassAttribute.getName());
                } catch (NoSuchFieldException noSuchFieldException) {
                    if (Objects.equals(testModifiedClassAttribute.getName(), "messageNumber")) {
                        setUpField = getOuterClassAttribute("messageNumber", testLoader, "ChatBot");
                    } else {
                        System.err.println("Could not load field "+ testModifiedClassAttribute.getName() +" for test setup, NoSuchFieldException occurred. Reason: " + noSuchFieldException.getMessage());
                    }
                }
                assert setUpField != null;
                setUpField.setAccessible(true);
                try {
                    Object preSetValue = setUpField.get(Modifier.isStatic(setUpField.getModifiers()) ? null : classInstance);
                    setUpField.set(null, testModifiedClassAttribute.getTestSetAttributeValue());
                    testModifiedAttributes.put(setUpField, preSetValue);
                } catch (IllegalAccessException illegalAccessException) {
                    System.err.println("Could not set value for field "+ testModifiedClassAttribute.getName() +" for test setup, IllegalAccessException occurred. Reason: " + illegalAccessException.getMessage());
                }
            }
        }
    }

    /**
     * Loads a specific method from a given class by its name.
     *
     * @param loadedSourceClass The class containing the desired method.
     * @param methodName The name of the method to be loaded.
     */
    public void loadMethod(Class<?> loadedSourceClass, String methodName) {
        method = null;
        try {
            method = loadedSourceClass.getDeclaredMethod(methodName);
            method.setAccessible(true);
        } catch (NoSuchMethodException noSuchMethodException) {
            System.err.println("Could not load method "+ methodName + ". Reason: "+ noSuchMethodException.getMessage());
        }
    }

    /**
     * Determines whether a given pre-load method is a constructor based on its name.
     *
     * @param preLoadMethod The pre-load method to check.
     * @return true if it is a constructor, false otherwise.
     */
    protected boolean isMethodConstructor(ClassTestRunMethod preLoadMethod) {
        return preLoadMethod.getMethodName().equals(preLoadMethod.getClassName());
    }

    /**
     * Retrieves any constructor that needs to be run before tests are executed from the list of pre-test methods.
     *
     * @return The constructor-pre-load-method or null if none exists.
     */
    protected ClassTestRunMethod getConstructorPreLoadMethod() {
        if (preTestMethods == null){
            return null;
        }
        for(ClassTestRunMethod classTestRunMethod : preTestMethods){
            if(isMethodConstructor(classTestRunMethod)){
                return classTestRunMethod;
            }
        }
        return null;
    }

    /**
     * Resets any modified attributes back to their original values after tests are completed.
     */
    protected void resetClassAttribute() {
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
                            System.err.println("After attempt Could not load field "+ changedAttribute.getKey().getName() +" for test reset, NoSuchFieldException occurred. Reason: " + e.getMessage());
                        }
                    } else {
                        System.err.println("Could not load field "+ changedAttribute.getKey().getName() +" for test reset, NoSuchFieldException occurred. Reason: " + noSuchFieldException.getMessage());
                    }
                }
                assert setUpField != null;
                setUpField.setAccessible(true);
                try {
                    setUpField.set(null, changedAttribute.getValue());
                } catch (IllegalAccessException illegalAccessException) {
                    System.err.println("Could not set value for field "+changedAttributeName +" for test setup, IllegalAccessException occurred. Reason: " + illegalAccessException.getMessage());
                }
            }
            setupTriggered = false;
        }
    }
}
