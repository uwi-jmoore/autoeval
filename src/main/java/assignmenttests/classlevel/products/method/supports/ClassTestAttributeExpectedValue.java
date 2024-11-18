package assignmenttests.classlevel.products.method.supports;

/**
 * This class extends the {@link ClassTestAttribute} and represents a class attribute 
 * with an expected value for testing purposes. It includes the expected value 
 * for the attribute, which is used in class-level testing to verify that the 
 * class attribute's value matches the expected value.
 */
public class ClassTestAttributeExpectedValue extends ClassTestAttribute {

    protected Object expectedAttributeValue;

    /**
     * Constructs a new ClassTestAttributeExpectedValue with the specified name, 
     * data type, and expected attribute value.
     * 
     * @param name The name of the class attribute.
     * @param datatype The data type of the class attribute.
     * @param expectedAttributeValue The expected value of the class attribute.
     */
    public ClassTestAttributeExpectedValue(String name, Class<?> datatype, Object expectedAttributeValue) {
        super(name, datatype);
        this.expectedAttributeValue = expectedAttributeValue;
    }

    /**
     * Returns a string representation of the ClassTestAttributeExpectedValue object,
     * including the expected attribute value, name, and data type.
     * 
     * @return A string representation of the object.
     */
    @Override
    public String toString() {
        return "ClassTestAttributeExpectedValue{" +
            "expectedAttributeValue=" + expectedAttributeValue +
            ", name='" + name + '\'' +
            ", datatype=" + datatype +
            '}';
    }

    /**
     * Gets the expected value of the class attribute.
     * 
     * @return The expected value of the class attribute.
     */
    public Object getExpectedAttributeValue() {
        return expectedAttributeValue;
    }
}
