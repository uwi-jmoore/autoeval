package assignmenttests.classlevel.concreteproducts;

import assignmentevaluator.classloader.AssignmentClassLoader;

import assignmenttests.classlevel.ClassTest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AttributeSignatureBROKENTest implements ClassTest {

    private Class<?> loadedClass;
    private Field field;
    private int attrModifiers;


    private String classFilePath;

    private String attributeName;
    private String dataType;
    private boolean hasDefaultValue;
    private int defaultValue;//if is integer minimum, count as no default value
    private boolean isFinal;
    private boolean isStatic;


    @Override
    public String toString() {
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
        List<String> missingKeys = findMissingKeys(setUpContent, expectedSetupContents);
        if (missingKeys.isEmpty()) {
            setAttributeName(setUpContent.get("attributeName"));
            setDataType(setUpContent.get("dataType"));
            setFinal(Boolean.parseBoolean(setUpContent.get("isFinal")));
            setStatic(Boolean.parseBoolean(setUpContent.get("isStatic")));
            boolean hasDef = Boolean.parseBoolean(setUpContent.get("hasDefault"));
            setHasDefaultValue(hasDef);
            if (hasDef) {
                String defVal = setUpContent.get("defaultValue");
                try {
                    setDefaultValue(Integer.parseInt(defVal));
                } catch (NumberFormatException numberFormatException) {
                    if (Objects.equals(defVal, "int_min")) {
                        setDefaultValue(Integer.MIN_VALUE);
                    } else if (Objects.equals(defVal, "int_max")) {
                        setDefaultValue(Integer.MAX_VALUE);
                    } else {
                        System.err.println("Invalid number format, Integer Expected");
                    }
                }
            }
        } else {
            System.err.println("Missing keys in setup Map: " + missingKeys);
        }


    }

    private List<String> findMissingKeys(Map<String, String> map, List<String> requiredKeys) {
        List<String> missingKeys = new ArrayList<>();
        for (String k : requiredKeys) {
            if (!map.containsKey(k)) {
                missingKeys.add(map.get(k));
            }
        }
        return missingKeys;
    }

    public void setClassFilePath(String classFilePath) {
        this.classFilePath = classFilePath;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public void setHasDefaultValue(boolean hasDefaultValue) {
        this.hasDefaultValue = hasDefaultValue;
    }

    public void setDefaultValue(int defaultValue) {
        this.defaultValue = defaultValue;
    }

    public void setFinal(boolean aFinal) {
        isFinal = aFinal;
    }

    public void setStatic(boolean aStatic) {
        isStatic = aStatic;
    }


    public Stream<String> provideAttributeName() {
        if (attributeName != null) {
            return Stream.of(
                attributeName
            );
        } else {
            throw new IllegalStateException(
                "attributeName is not set. " +
                    "Ensure setattributeName() is called before running the test.");
        }
    }

    public Stream<String> provideAttributeDataType() {
        if (dataType != null) {
            return Stream.of(
                dataType
            );
        } else {
            throw new IllegalStateException(
                "dataType is not set. " +
                    "Ensure setDataType() is called before running the test.");
        }
    }

    public Stream<Arguments> provideAttributeIsFinal() {
        System.out.println("Providing isFinal: " + isFinal);
        return Stream.of(Arguments.of(isFinal));
    }

    public static Stream<Boolean> provideAttributeIsStatic() {
        System.out.println("Providing isStatic: " + false);
        return Stream.of(false);
    }

    public Stream<Arguments> provideAttributeDefaultValue() {
        return Stream.of(Arguments.of(
            hasDefaultValue, defaultValue
        ));
    }

    @BeforeEach
    public void initializeLoadedClass() throws IOException, NoSuchFieldException {
        AssignmentClassLoader testLoader = new AssignmentClassLoader();
        loadedClass = testLoader.loadClassFromFile(classFilePath);
        field = loadedClass.getDeclaredField(attributeName);
        field.setAccessible(true);
//        attrModifiers = field.getModifiers();

    }
    @BeforeEach
    public void validateTestSetup() {
        if (attributeName == null || dataType == null || !isFinal || !isStatic) {
            throw new IllegalStateException("Test setup details are missing. Ensure setUpTestDetails() was called.");
        }
    }

    @ParameterizedTest
    @MethodSource("provideAttributeName")
    public void testAttributeName(String attributeNamei) {
        System.out.println("Running attribute name test");
        assertAll("Testing attribute name",
            () -> assertNotNull(loadedClass.getDeclaredField(attributeNamei), "Attribute not found in Class")
        );

    }


    @ParameterizedTest
    @MethodSource("provideAttributeDataType")
    public void testAttributeDataType(String dataType){
        String testDatatype = "java.lang."+dataType;
        String failmsg = "Datatype mismatch";
        assertAll("heading",
            () -> {
                if(dataType.contains("<")){
                    Type[] typeArgs = getTypeArgs(field);
                    assertTrue(Collection.class.isAssignableFrom(field.getType())
                        && typeArgs.length > 0
                        && ((Class<?>) typeArgs[0]).getSimpleName().equals(getTypeFromListParameter(dataType)),
                        failmsg
                    );
                }else{
                    assertEquals(field.getType().getName(), testDatatype,failmsg);
                }
            }
        );
    }

    private Type[] getTypeArgs(Field field){
        Type genericType = field.getGenericType();
        ParameterizedType parameterizedType = (ParameterizedType) genericType;
        return parameterizedType.getActualTypeArguments();
    }




    //may fail


//    @ParameterizedTest
//    @MethodSource("provideAttributeIsStatic")
//    @DisplayName("Test static attribute modifier")
//    public void testZAttributeIsStatic(boolean isAttrStatic) {
//        try {
//            Field f = loadedClass.getDeclaredField(attributeName);
//            int aModifiers = f.getModifiers();
//            System.out.println("HERE Running test with isAttrStatic: " + isAttrStatic);
//            System.out.println("attribute modifier return: "+ Modifier.isFinal(aModifiers));
//            assertAll("ge",
//                () -> assertEquals(Modifier.isFinal(aModifiers), isAttrStatic,
//                    isAttrStatic ?
//                        "'static' keyword expected in attribute signature" :
//                        "unneeded 'static' keyword encountered in attribute signature"
//                )
//            );
//        } catch (Exception e) {
//            e.printStackTrace();  // Log any exceptions that occur during the test setup
//        }
//    }
//    @Test
//    public void testAttributeHasStatic(){
//        try {
//            Field f = loadedClass.getDeclaredField(attributeName);
//            int aModifiers = f.getModifiers();
//            System.out.println("attribute modifier return: "+ Modifier.isFinal(aModifiers));
//            assertAll("ge",
//                () -> assertEquals(Modifier.isFinal(aModifiers), false,
//                    Modifier.isFinal(aModifiers) ?
//                        "'static' keyword expected in attribute signature" :
//                        "unneeded 'static' keyword encountered in attribute signature"
//                )
//            );
//        } catch (Exception e) {
//            e.printStackTrace();  // Log any exceptions that occur during the test setup
//        }
//    }

////    //may fail
//    @ParameterizedTest
//    @MethodSource("provideAttributeIsFinal")
//    public void testAttributeIsFinal(boolean isAttrFinal){
//        assertEquals(Modifier.isStatic(attrModifiers),isAttrFinal,
//            isAttrFinal?
//                "'final' keyword expected in attribute signature":
//                "unneeded 'final' keyword encountered in attribute signature"
//        );
//    }
//
//    @ParameterizedTest
//    @MethodSource("provideAttributeDefaultValue")
//    public void testAttributeDefaultValue(boolean attrHasDef, int defValue){
//        assertAll("Testing Default value",
//            () -> {
//                if(hasDefaultValue){
//                    Object attrDefaultValue = null;
//                    if(!Modifier.isStatic(attrModifiers)){
//                        Object inst = loadedClass.getDeclaredConstructor().newInstance();
//                        attrDefaultValue = field.get(inst);
//                    }
//                    assertTrue(attrDefaultValue!=null && attrDefaultValue.equals(defaultValue),
//                        "no default value encountered or incorrect default value encountered"
//                    );
//                }else{
//                    assertNull(field.get(null),"default value for attribute encountered");
//                }
//            }
//        );
//    }

//    @ParameterizedTest
//    @MethodSource("provideAttributeElements")
//    public void testAttributeSignature(String attributeName, String dataType) throws NoSuchFieldException {
////        Field field = loadedClass.getDeclaredField(attributeName);
////        field.setAccessible(true);
//        String testDatatype = "java.lang."+dataType;
////        int attrModifiers = field.getModifiers();
//        assertAll("heading",
//            () -> assertNotNull(field), //attribute exists
//            () -> {
//                if(dataType.contains("<")){
//                    Type genericType = field.getGenericType();
//                    ParameterizedType parameterizedType = (ParameterizedType) genericType;
//                    Type[] typeArgs = parameterizedType.getActualTypeArguments();
//                    //handle if Object type is User created or Part of the Java language library
//                    assertTrue(Collection.class.isAssignableFrom(field.getType())
//                        && typeArgs.length > 0
//                        && ((Class<?>) typeArgs[0]).getSimpleName().equals(getTypeFromListParameter(dataType)));
//                }else{
//                    assertEquals(field.getType().getName(), testDatatype);
//                }
//            },
//            () -> assertEquals(Modifier.isFinal(attrModifiers),isFinal),
//            () -> assertEquals(Modifier.isStatic(attrModifiers),isStatic),
//            () -> {
//                if(hasDefaultValue){
//                    Object attrDefaultValue = null;
//                    if(!Modifier.isStatic(attrModifiers)){
//                        Object inst = loadedClass.getDeclaredConstructor().newInstance();
//                        attrDefaultValue = field.get(inst);
//                    }
//                    assertTrue(attrDefaultValue!=null && attrDefaultValue.equals(defaultValue));
//                }else{
//                    assertEquals(field.get(null)==null,hasDefaultValue);
//                }
//            }
//        );
//    }



    private String getTypeFromListParameter(String rawDatatype){
        return rawDatatype.split("[<>]")[1];

    }


}

