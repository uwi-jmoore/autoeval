package assignmenttests.classlevel.products.attribute;


import assignmenttests.classlevel.ClassTestBase;
import assignmenttests.classlevel.products.attribute.supports.AttributeDataType;
import assignmenttests.classlevel.products.attribute.supports.AttributeDefaultValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.api.Test;
import sun.misc.Unsafe;
import org.junit.jupiter.api.TestInstance;

import java.lang.reflect.*;
import java.util.*;

import static assignmenttests.classlevel.ClassLevelHelpers.findMissingKeys;
import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AttributeSignatureTest extends ClassTestBase {
    protected static Field field;

    protected static String attributeName;
    protected static AttributeDataType dataType;
    protected static AttributeDefaultValue defaultValue;
    protected static boolean isFinal;
    protected static boolean isStatic;

    public void setAttributeName(String attributeName) {
        AttributeSignatureTest.attributeName = attributeName;
    }

    public void setDataType(AttributeDataType dataType) {
        AttributeSignatureTest.dataType = dataType;
    }

    public void setDefaultValue(AttributeDefaultValue defaultValue) {
        AttributeSignatureTest.defaultValue = defaultValue;
    }

    public void setIsStatic(boolean isStatic){
        AttributeSignatureTest.isStatic = isStatic;
    }
    public void setIsFinal(boolean isFinal){
        AttributeSignatureTest.isFinal = isFinal;
    }

    @Override
    public void setUpTestDetails(Map<String, Object> setUpContent) {
        List<String> expectedSetupContents = Arrays.asList(
            "attributeName",
            "dataType",
            "default",
            "isFinal",
            "isStatic"
        );
        List<String> missingKeys = findMissingKeys(setUpContent,expectedSetupContents);
        if(missingKeys.isEmpty()){
            setAttributeTestDetails(setUpContent);
        }else {
            System.err.println("Missing keys in setup Map: "+missingKeys);
        }
    }
    protected void setAttributeTestDetails(Map<String, Object> setUpContent){
        setAttributeName((String) setUpContent.get("attributeName"));
        setDataType((AttributeDataType) setUpContent.get("dataType"));
        setDefaultValue((AttributeDefaultValue) setUpContent.get("default"));
        setIsStatic((Boolean) setUpContent.get("isStatic"));
        setIsFinal((Boolean) setUpContent.get("isFinal"));
    }
    protected void stdAttrTestInit(){
        super.classTestBaseSetUp();
        loadField();
    }

    protected void loadField(){
        try{
            AttributeSignatureTest.field = loadedClass.getDeclaredField(attributeName);
            field.setAccessible(true);
        }catch (NoSuchFieldException noSuchFieldException){
            System.err.println("NoSuchFieldException occurred while trying to load field: "
                + attributeName
                +". Reason: "
                +noSuchFieldException.getMessage()
            );
        }
    }

    @Override
    public String toString(){
        return "Attribute Signature Test";
    }

    @BeforeEach
    public void initializeLoadedClass(){
        stdAttrTestInit();

    }

    private int getFieldModifiers(){
        return field.getModifiers();
    }

    @Test
    @DisplayName("Attribute Existence Test")
    public void testAttributeExists(){
        assertNotNull(field,"Attribute not found in class");
    }

    @Test
    @DisplayName("Attribute Datatype Test")
    public void testAttributeDataType(){
        assertTrue((testDataType()),
            "Incorrect Datatype: "+ dataType.getDataType().getName()
                + " expected, got "+  field.getType().getName());
    }

    @Test
    @DisplayName("Attribute Default Value Test")
    public void testAttributeDefaultValue() throws InstantiationException {
        Object attrDefaultValue = getDefaultValueOfField(field,loadedClass,getFieldModifiers());
        if(defaultValue==null){
            assertTrue(isJavaSetDefaultValue(attrDefaultValue,field.getType()));
        }else{
            assertSame(attrDefaultValue, defaultValue.getAttrDefaultValue());
            assert attrDefaultValue != null;
            assertSame(attrDefaultValue.getClass(),primitiveToWrapper(defaultValue.getAttrDefaultValueDataType()));
        }
    }

    @Test
    @DisplayName("Attribute Final Keyword Test")
    public void testAttributeIsFinal(){
        assertEquals(Modifier.isFinal(getFieldModifiers()),isFinal,
            isFinal ? "'final' keyword expected in attribute signature, not found" :
                "unneeded 'final' keyword encountered in attribute signature"
        );
    }

    @Test
    @DisplayName("Attribute Static Keyword Test")
    public void testAttributeIsStatic(){
        assertEquals(Modifier.isStatic(getFieldModifiers()),isStatic,
            isStatic ? "'static' keyword expected in attribute signature, not found" :
                "unneeded 'static' keyword encountered in attribute signature"
        );
    }

    protected boolean testDataType(){
        if(dataType.getGeneric() != null){
            boolean isCollection = Collection.class.isAssignableFrom(field.getType());
            boolean isCorrectGeneric = testGeneric();
            return isCollection && isCorrectGeneric;
        }else{
            return dataType.getDataType().equals(field.getType());
        }
    }

    protected boolean testGeneric(){
        Type fieldGeneric = field.getGenericType();
        if(fieldGeneric instanceof ParameterizedType parameterizedType){
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            return actualTypeArguments.length > 0 && Arrays.stream(actualTypeArguments)
                .toList()
                .stream()
                .map(Type::getTypeName)
                .toList()
                .contains(dataType.getGeneric().toString());
        }else{
            return false;
        }
    }

    private Object getDefaultValueOfField(Field field, Class<?> loaded, int modifiers) throws InstantiationException {
        if(!Modifier.isStatic(modifiers)){
            return getDefaultValue(field,loaded);
        }else{
            try {
                return field.get(null);
            } catch (IllegalAccessException illegalAccessException) {
                System.err.println("IllegalAccessException occurred while trying to get default value of static field "
                    + field.getName()
                    + ". Reason: "
                    + illegalAccessException.getMessage());
                return null;
            }
        }
    }


    private Object getDefaultValue(Field field, Class<?> loaded) throws InstantiationException {
        Field unsafeField = null;
        try {
            unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
            unsafeField.setAccessible(true);
            try {
                Unsafe unsafe = (Unsafe) unsafeField.get(null);
            } catch (IllegalAccessException illegalAccessException) {
                System.err.println("IllegalAccessException occurred while trying to get default value of non static field "
                    + field.getName()
                    + ". Reason: "
                    + illegalAccessException.getMessage());
            }
        } catch (NoSuchFieldException noSuchFieldException) {
            System.err.println("NoSuchFieldException occurred while trying to load non static field: "
                + field.getName()
                + ". Reason: "
                + noSuchFieldException.getMessage());
        }
        try {
            assert unsafeField != null;
            Unsafe unsafe = (Unsafe) unsafeField.get(null);
            return field.get(unsafe.allocateInstance(loaded));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isJavaSetDefaultValue(Object attrDefaultValue, Class<?> fieldType){
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
