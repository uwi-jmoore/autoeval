package assignmenttests.classlevel.concreteproducts;

import assignmentevaluator.classloader.AssignmentClassLoader;

import assignmenttests.classlevel.ClassTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import sun.misc.Unsafe;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Stream;

import static filehandler.filehelperservice.FileOperationHelpers.getFileNameFromPathString;
import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AttributeSignatureTest implements ClassTest{

    private static Class<?> loadedClass;
    private static AssignmentClassLoader testLoader;
    private static Field field;

    private static String classFilePath;
    private static String attributeName;
    private static String dataType;
    private static String hasDefaultValue;
    private static String defaultValue;//if is integer minimum, count as no default value
    private static String isFinal;
    private static String isStatic;
    @Override
    public String toString(){
        return "Attribute Signature Test";
    }

    @Override
    public void setUpTestDetails(Map<String, String> setUpContent) {
        List<String> expectedSetupContents = Arrays.asList(
            "attributeName",
            "dataType",
            "hasDefault",
            "defaultValue",
            "isFinal",
            "isStatic"
        );
        List<String> missingKeys = findMissingKeys(setUpContent,expectedSetupContents);
        if(missingKeys.isEmpty()){
            setAttributeName(setUpContent.get("attributeName"));
            setDataType(setUpContent.get("dataType"));
            setFinal(Boolean.parseBoolean(setUpContent.get("isFinal")));
            setStatic(Boolean.parseBoolean(setUpContent.get("isStatic")));
            boolean hasDef = Boolean.parseBoolean(setUpContent.get("hasDefault"));
            setHasDefaultValue(hasDef);
            String defVal = setUpContent.get("defaultValue");
            setDefaultValueStr(defVal);
        }else{
            System.err.println("Missing keys in setup Map: "+missingKeys);
        }


    }

    private List<String> findMissingKeys(Map<String, String> map, List<String> requiredKeys){
        List<String> missingKeys = new ArrayList<>();
        for (String k: requiredKeys){
            if(!map.containsKey(k)){
                missingKeys.add(map.get(k));
            }
        }
        return missingKeys;
    }

    public void setClassFilePath(String classFilePath) {
        AttributeSignatureTest.classFilePath = classFilePath;
    }

    public void setAttributeName(String attributeName) {
        AttributeSignatureTest.attributeName = attributeName;
    }

    public void setDataType(String dataType) {
        AttributeSignatureTest.dataType = dataType;
    }

    public void setHasDefaultValue(boolean hasDefaultValue) {
        AttributeSignatureTest.hasDefaultValue = String.valueOf(hasDefaultValue);
    }
    public void setDefaultValueStr(String defVal){
        AttributeSignatureTest.defaultValue = defVal;
    }



    public void setFinal(boolean aFinal) {
        isFinal = String.valueOf(aFinal);
    }

    public void setStatic(boolean aStatic) {
        isStatic = String.valueOf(aStatic);
    }

    public static Stream<Arguments> provideAttributeElements() {
        if (classFilePath != null && attributeName != null && dataType!=null && defaultValue!=null && isFinal!=null && isStatic!=null&&hasDefaultValue!=null) {
            return Stream.of(
                Arguments.of(
                    dataType,
                    isFinal,
                    isStatic,
                    hasDefaultValue,
                    defaultValue
                )
            );
        } else {
            throw new IllegalStateException(
                "classFilePath or attributeName or dataType is not set. " +
                    "Ensure setClassFilePath(), setAttributeName(), and setDataType() are called before running the test.");
        }
    }

    @BeforeEach
    public void initializeLoadedClass() throws NoSuchFieldException, ClassNotFoundException {
        testLoader = new AssignmentClassLoader(new File(classFilePath).getParentFile());
        String className = getFileNameFromPathString(classFilePath);
        loadedClass = testLoader.loadClass(className);
        field = loadedClass.getDeclaredField(attributeName);

    }


    /**
     * DO NOT SEPARATE THESE, I DO NOT KNOW WHY, BUT SEPARATING THE INDIVIDUAL COMPONENT TESTS CAUSE THEM TO NOT WORK
     * -10:50 AM 10/11/2024
     * -Jarrod Moore
     * */
    @ParameterizedTest
    @MethodSource("provideAttributeElements")
    @DisplayName("Attribute Signature Test")
    public void testAttributeSignature(String dataType, String isFinal, String isStatic, String hasDefaultValue, String defaultValue){
        String testDatatype = Objects.equals(dataType, "String") ? "java.lang." + dataType : dataType;
        int attrModifiers = field.getModifiers();
        assertAll("heading",
            () -> assertNotNull(field,"Attribute not found in class"), //attribute exists using name call
            () -> {
                if(dataType.contains("<")){
                    try{
                        assertTrue(Collection.class.isAssignableFrom(field.getType()));
                        String fieldTypeDescription = getFieldTypeDescription(field,loadedClass,testDatatype);
                        assert fieldTypeDescription != null;
                        assertTrue(fieldTypeDescription.contains(getTypeFromListParameter(testDatatype)));
                    }catch (TypeNotPresentException notPresentException){
                        System.err.println("Could not get field genericType. Reason: "+ notPresentException.getMessage());
                    }
                }else{
                    assertEquals(field.getType().getName(), testDatatype,"Incorrect Datatype: "+ testDatatype + " expected, got "+ field.getType().getName());
                }
            },
            () -> assertEquals(Modifier.isFinal(attrModifiers),convToBoolean(isFinal),
                convToBoolean(isFinal) ? "'final' keyword expected in attribute signature, not found" :
                    "unneeded 'final' keyword encountered in attribute signature"
            ),
            () -> assertEquals(Modifier.isStatic(attrModifiers),convToBoolean(isStatic),
                convToBoolean(isStatic) ? "'static' keyword expected in attribute signature, not found" :
                    "unneeded 'static' keyword encountered in attribute signature"
            ),
            () -> {
                field.setAccessible(true);
                Object attrDefaultValue;
                if(convToBoolean(hasDefaultValue) ){//has default value
                    attrDefaultValue = getDefaultValueOfField(field,loadedClass,attrModifiers);
                    assertEquals(attrDefaultValue, convToInteger(defaultValue), "no default value encountered or incorrect default value encountered");
                }else{//has no default value
                    attrDefaultValue = getDefaultValueOfField(field,loadedClass,attrModifiers);
                    assertTrue(isDefaultValue(attrDefaultValue,field.getType()),"default value for attribute encountered");
                }
            }

        );
    }
    private String getFieldTypeDescription(Field field, Class<?> loaded, String attrDataType){
        if(!isJavaLibraryClass(getTypeFromListParameter(attrDataType))){
            try {
                //Loading non Java Library
                Class<?> OtherClass = Class.forName(
                    getTypeFromListParameter(attrDataType),
                    true,
                    loaded.getClassLoader()
                );
            } catch (ClassNotFoundException e) {
                System.err.println(getTypeFromListParameter(attrDataType)+" class not found: " + e.getMessage());
                return null;
            }

            Type genericType = field.getGenericType();
            if (genericType instanceof ParameterizedType paramType) {
                Type[] actualTypeArguments = paramType.getActualTypeArguments();
                return actualTypeArguments[0].getTypeName();
            } else {
                System.err.println("Field is not parameterized");
                return null;
            }
        }
        return field.getType().getName();
    }

    private boolean isJavaLibraryClass(String className){
        Set<String> primitiveTypes = Set.of(
            "byte", "short", "int", "long", "float", "double", "char", "boolean", "void"
        );
        try {
            Class<?> clazz = Class.forName(className);
            return clazz.getPackage().getName().startsWith("java.");
        } catch (ClassNotFoundException e) {
            return primitiveTypes.contains(className);
        }
    }

    private String getTypeFromListParameter(String rawDatatype){
        return rawDatatype.split("[<>]")[1];

    }

    private Object getDefaultValueOfField(Field field, Class<?> loaded, int modifiers)
        throws NoSuchFieldException, IllegalAccessException, InstantiationException {
        if(!Modifier.isStatic(modifiers)){
            return getDefaultValue(field,loaded);
        }
        return field.get(null);
    }

    private boolean convToBoolean(String booleanInput){
        return Boolean.parseBoolean(booleanInput);
    }

    private Integer convToInteger(String integerInput){
        if(Objects.equals(integerInput, "int_min")){
            return Integer.MIN_VALUE;
        } else if (Objects.equals(integerInput, "int_max")) {
            return Integer.MAX_VALUE;
        }
        return Integer.parseInt(integerInput);
    }

    private Object getDefaultValue(Field field, Class<?> loaded)
        throws NoSuchFieldException, IllegalAccessException, InstantiationException {
        Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
        unsafeField.setAccessible(true);
        Unsafe unsafe = (Unsafe) unsafeField.get(null);
        return field.get(unsafe.allocateInstance(loaded));
    }

    private boolean isDefaultValue(Object attrDefaultValue, Class<?> fieldType){
        if(attrDefaultValue == null){
            return !fieldType.isPrimitive();
        }
        return switch (fieldType.getName()) {
            case "byte" -> (Byte) attrDefaultValue == 0;
            case "short" -> (Short) attrDefaultValue == 0;
            case "int" -> (Integer) attrDefaultValue == 0;
            case "long" -> (Long) attrDefaultValue == 0L;
            case "float" -> (Float) attrDefaultValue == 0.0f;
            case "double" -> (Double) attrDefaultValue == 0.0d;
            case "char" -> (Character) attrDefaultValue == '\u0000';
            case "boolean" -> !(Boolean) attrDefaultValue;
            default ->
                // If the type is non-primitive and not null, it's not the default value
                false;
        };

    }

}
