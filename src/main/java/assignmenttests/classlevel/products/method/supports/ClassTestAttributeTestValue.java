package assignmenttests.classlevel.products.method.supports;

/**
 * Represents a test value for class attributes in the context of class testing.
 * This class extends ClassTestAttribute and holds a specific test value
 * associated with an attribute, along with its name and data type.
 */
public class ClassTestAttributeTestValue extends ClassTestAttribute {
    protected Object testSetAttributeValue;

    /**
     * Constructs a ClassTestAttributeTestValue with the specified name, data type, and test value.
     *
     * @param name        the name of the attribute
     * @param datatype    the data type of the attribute
     * @param testSetValue the test value to be set for the attribute
     */
    public ClassTestAttributeTestValue(String name, Class<?> datatype, Object testSetValue) {
        super(name, datatype);
        this.testSetAttributeValue = testSetValue;
    }

    /**
     * Returns a string representation of the ClassTestAttributeTestValue object,
     * including its test set attribute value, name, and data type.
     *
     * @return a string representation of this object
     */
    @Override
    public String toString() {
        return "ClassTestAttributeTestValue{" +
            "testSetAttributeValue=" + testSetAttributeValue +
            ", name='" + name + '\'' +
            ", datatype=" + datatype +
            '}';
    }

    /**
     * Gets the test set attribute value.
     *
     * @return the value used for testing the attribute
     */
    public Object getTestSetAttributeValue() {
        return testSetAttributeValue;
    }
}
