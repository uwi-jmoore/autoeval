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

/**
 * Test class for verifying the attributes of a class.
 * This class extends ClassTestBase and performs various tests
 * on class attributes such as existence, data type, default value,
 * and modifiers (static and final).
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AttributeSignatureTest extends ClassTestBase {
    protected static Field field;
    protected static String attributeName;
    protected static AttributeDataType dataType;
    protected static AttributeDefaultValue defaultValue;
    protected static boolean isFinal;
    protected static boolean isStatic;

    /**
     * Sets the name of the attribute to be tested.
     *
     * @param attributeName the name of the attribute
     */
    public void setAttributeName(String attributeName) {
        AttributeSignatureTest.attributeName = attributeName;
    }

    /**
     * Sets the data type of the attribute to be tested.
     *
     * @param dataType the data type of the attribute
     */
    public void setDataType(AttributeDataType dataType) {
        AttributeSignatureTest.dataType = dataType;
    }

    /**
     * Sets the default value of the attribute to be tested.
     *
     * @param defaultValue the default value of the attribute
     */
    public void setDefaultValue(AttributeDefaultValue defaultValue) {
        AttributeSignatureTest.defaultValue = defaultValue;
    }

    /**
     * Sets whether the attribute is static.
     *
     * @param isStatic true if the attribute is static, false otherwise
     */
    public void setIsStatic(boolean isStatic) {
        AttributeSignatureTest.isStatic = isStatic;
    }

    /**
     * Sets whether the attribute is final.
     *
     * @param isFinal true if the attribute is final, false otherwise
     */
    public void setIsFinal(boolean isFinal) {
        AttributeSignatureTest.isFinal = isFinal;
    }

    /**
     * Sets up test details based on provided setup content.
     *
     * @param setUpContent a map containing setup values for testing
     */
    @Override
    public void setUpTestDetails(Map<String, Object> setUpContent) {
        List<String> expectedSetupContents = Arrays.asList(
            "attributeName", "dataType", "default", "isFinal", "isStatic"
        );
        List<String> missingKeys = findMissingKeys(setUpContent, expectedSetupContents);

        if (missingKeys.isEmpty()) {
            setAttributeTestDetails(setUpContent);
        } else {
            System.err.println("Missing keys in setup Map: " + missingKeys);
        }
    }

    /**
     * Sets attribute test details from setup content.
     *
     * @param setUpContent a map containing setup values for testing
     */
    protected void setAttributeTestDetails(Map<String, Object> setUpContent) {
        setAttributeName((String) setUpContent.get("attributeName"));
        setDataType((AttributeDataType) setUpContent.get("dataType"));
        setDefaultValue((AttributeDefaultValue) setUpContent.get("default"));
        setIsStatic((Boolean) setUpContent.get("isStatic"));
        setIsFinal((Boolean) setUpContent.get("isFinal"));
    }

    /**
     * Initializes standard attribute test setup.
     */
    protected void stdAttrTestInit() {
        super.classTestBaseSetUp();
        loadField();
    }

    /**
     * Loads the field to be tested based on the specified attribute name.
     */
    protected void loadField() {
        try {
            AttributeSignatureTest.field = loadedClass.getDeclaredField(attributeName);
            field.setAccessible(true);
        } catch (NoSuchFieldException noSuchFieldException) {
            System.err.println("NoSuchFieldException occurred while trying to load field: " + attributeName + ". Reason: " + noSuchFieldException.getMessage());
        }
    }

    /**
     * Returns a string representation of the test class.
     *
     * @return a string indicating this is an Attribute Signature Test
     */
    @Override
    public String toString() {
        return "Attribute Signature Test";
    }

    /**
     * Initializes loaded class before each test.
     */
    @BeforeEach
    public void initializeLoadedClass() {
        stdAttrTestInit();
    }

    /**
     * Gets modifiers of the field being tested.
     *
     * @return an integer representing field modifiers
     */
    private int getFieldModifiers() {
        return field.getModifiers();
    }

    /**
     * Tests if the specified attribute exists in the class.
     */
    @Test
    @DisplayName("Attribute Existence Test")
    public void testAttributeExists() {
        assertNotNull(field, "Attribute not found in class");
    }

    /**
     * Tests if the data type of the specified attribute matches expected data type.
     */
    @Test
    @DisplayName("Attribute Datatype Test")
    public void testAttributeDataType() {
        assertTrue(testDataType(), "Incorrect Datatype: " + dataType.getDataType().getName() + " expected, got " + field.getType().getName());
    }

    /**
     * Tests if the default value of the specified attribute matches expected default value.
     *
     * @throws InstantiationException if instantiation fails while getting default value
     */
    @Test
    @DisplayName("Attribute Default Value Test")
    public void testAttributeDefaultValue() throws InstantiationException {
        Object attrDefaultValue = getDefaultValueOfField(field, loadedClass, getFieldModifiers());
        if (defaultValue == null) {
            assertTrue(isJavaSetDefaultValue(attrDefaultValue, field.getType()));
        } else {
            assertSame(attrDefaultValue, defaultValue.getAttrDefaultValue());
            assert attrDefaultValue != null;
            assertSame(attrDefaultValue.getClass(), primitiveToWrapper(defaultValue.getAttrDefaultValueDataType()));
        }
    }

    /**
     * Tests if the specified attribute has 'final' modifier.
     */
    @Test
    @DisplayName("Attribute Final Keyword Test")
    public void testAttributeIsFinal() {
        assertEquals(Modifier.isFinal(getFieldModifiers()), isFinal,
            isFinal ? "'final' keyword expected in attribute signature, not found" : "unneeded 'final' keyword encountered in attribute signature");
    }

    /**
     * Tests if the specified attribute has 'static' modifier.
     */
    @Test
    @DisplayName("Attribute Static Keyword Test")
    public void testAttributeIsStatic() {
        assertEquals(Modifier.isStatic(getFieldModifiers()), isStatic,
            isStatic ? "'static' keyword expected in attribute signature, not found" : "unneeded 'static' keyword encountered in attribute signature");
    }

    /**
     * Tests if the data type matches with generic type if applicable.
     *
     * @return true if data types match; otherwise false
     */
    protected boolean testDataType() {
        if (dataType.getGeneric() != null) {
            boolean isCollection = Collection.class.isAssignableFrom(field.getType());
            boolean isCorrectGeneric = testGeneric();
            return isCollection && isCorrectGeneric;
        } else {
            return dataType.getDataType().equals(field.getType());
        }
    }

    /**
     * Tests if generic types match with expected generic types for collections.
     *
     * @return true if generic types match; otherwise false
     */
    protected boolean testGeneric() {
        Type fieldGeneric = field.getGenericType();
        if (fieldGeneric instanceof ParameterizedType parameterizedType) {
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            return actualTypeArguments.length > 0 && Arrays.stream(actualTypeArguments)
                .toList()
                .stream()
                .map(Type::getTypeName)
                .toList()
                .contains(dataType.getGeneric().toString());
        } else {
            return false;
        }
    }

    /**
     * Gets default value of a field based on its modifiers and type.
     *
     * @param field      The field to check for default value
     * @param loaded      The loaded class containing this field
     * @param modifiers  The modifiers of this field
     *
     * @return The default value of this field or null if unable to retrieve it
     *
     * @throws InstantiationException if instantiation fails while getting default value
     */
    private Object getDefaultValueOfField(Field field, Class<?> loaded, int modifiers) throws InstantiationException {
        if (!Modifier.isStatic(modifiers)) {
            return getDefaultValue(field, loaded);
        } else {
            try {
                return field.get(null);
            } catch (IllegalAccessException illegalAccessException) {
                System.err.println("IllegalAccessException occurred while trying to get default value of static field " + field.getName() + ". Reason: " + illegalAccessException.getMessage());
                return null;
            }
        }
    }

    /**
     * Gets default value for non-static fields using Unsafe class methods.
     *
     * @param field The non-static field to retrieve default value from
     * @param loaded The class containing this field
     *
     * @return The default value of this non-static field or throws an exception on failure.
     *
     * @throws InstantiationException if instantiation fails while getting default value
     */
    private Object getDefaultValue(Field field, Class<?> loaded) throws InstantiationException {
        Field unsafeField = null;
        try {
            unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
            unsafeField.setAccessible(true);
            try {
                Unsafe unsafe = (Unsafe) unsafeField.get(null);
            } catch (IllegalAccessException illegalAccessException) {
                System.err.println("IllegalAccessException occurred while trying to get default value of non-static field " + field.getName() + ". Reason: " + illegalAccessException.getMessage());
            }
        } catch (NoSuchFieldException noSuchFieldException) {
            System.err.println("NoSuchFieldException occurred while trying to load non-static field: " + field.getName() + ". Reason: " + noSuchFieldException.getMessage());
        }
        try {
            assert unsafeField != null;
            Unsafe unsafe = (Unsafe) unsafeField.get(null);
            return field.get(unsafe.allocateInstance(loaded));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Checks whether a given object's value matches Java's default values for primitives.
     *
     * @param attrDefaultValue The object's value to check against defaults
     * @param fieldType The type of the corresponding field being checked against defaults
     *
     * @return true if it matches Java's defaults; otherwise false
     */
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
            default -> false; // If non-primitive and not null, it's not a default value
        };
    }
}
