package assignmenttests.classlevel.concreteproducts;

import assignmentevaluator.classloader.AssignmentClassLoader;

import assignmenttests.classlevel.ClassTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AttributeSignatureTest implements ClassTest {

    private Class<?> loadedClass;

    private String classFilePath;
    private String attributeName;
    private String dataType;
    private boolean hasDefaultValue;
    private int defaultValue;//if is integer minimum, count as no default value
    private boolean isFinal;
    private boolean isStatic;
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
            if(hasDef){
                String defVal = setUpContent.get("defaultValue");
                try{
                    setDefaultValue(Integer.parseInt(defVal));
                }catch (NumberFormatException numberFormatException){
                    if(Objects.equals(defVal, "int_min")){
                        setDefaultValue(Integer.MIN_VALUE);
                    }
                    else if (Objects.equals(defVal, "int_max")){
                        setDefaultValue(Integer.MAX_VALUE);
                    }
                    else{
                        System.err.println("Invalid number format, Integer Expected");
                    }
                }
            }
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

    @BeforeEach
    public void initializeLoadedClass() throws IOException {
        AssignmentClassLoader testLoader = new AssignmentClassLoader();
        loadedClass = testLoader.loadClassFromFile(classFilePath);
    }

    @Test
    public void testClassExistenceNoParams(){
        assertNotNull(loadedClass);
    }


    public Stream<Arguments> provideAttributeElements() {
        if (classFilePath != null && attributeName != null && dataType!=null) {
            return Stream.of(
               Arguments.of(
                   classFilePath,
                   attributeName,
                   dataType)
            );
        } else {
            throw new IllegalStateException(
                "classFilePath or attributeName or dataType is not set. " +
                    "Ensure setClassFilePath(), setAttributeName(), and setDataType() are called before running the test.");
        }
    }

    @ParameterizedTest
    @MethodSource("provideAttributeElements")
    public void testAttributeSignature(String attributeName, String dataType) throws NoSuchFieldException {
        Field field = loadedClass.getDeclaredField(attributeName);
        field.setAccessible(true);
        String testDatatype = "java.lang."+dataType;
        int attrModifiers = field.getModifiers();
        assertAll("heading",
            () -> assertNotNull(field), //attribute exists
            () -> {
            if(dataType.contains("<")){
                Type genericType = field.getGenericType();
                ParameterizedType parameterizedType = (ParameterizedType) genericType;
                Type[] typeArgs = parameterizedType.getActualTypeArguments();
                //handle if Object type is User created or Part of the Java language library
                assertTrue(Collection.class.isAssignableFrom(field.getType()) && typeArgs.length > 0 && ((Class<?>) typeArgs[0]).getSimpleName().equals(getTypeFromListParameter(dataType)));
            }else{
                assertEquals(field.getType().getName(), testDatatype);
            }
            },
            () -> assertEquals(Modifier.isFinal(attrModifiers),isFinal),
            () -> assertEquals(Modifier.isStatic(attrModifiers),isStatic),
            () -> {
                if(hasDefaultValue){
                    Object attrDefaultValue = null;
                    if(!Modifier.isStatic(attrModifiers)){
                        Object inst = loadedClass.getDeclaredConstructor().newInstance();
                        attrDefaultValue = field.get(inst);
                    }
                    assertTrue(attrDefaultValue!=null && attrDefaultValue.equals(defaultValue));
                }else{
                    assertEquals(field.get(null)==null,hasDefaultValue);
                }
            }


        );
    }
    private String getTypeFromListParameter(String rawDatatype){
        return rawDatatype.split("[<>]")[1];

    }


}

