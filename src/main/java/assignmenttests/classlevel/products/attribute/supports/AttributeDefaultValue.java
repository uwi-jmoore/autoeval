package assignmenttests.classlevel.products.attribute.supports;

/**
 * Represents the default value of an attribute along with its associated data type.
 *
 * <p>This class encapsulates the default value of an attribute as an {@link Object}
 * and its corresponding data type as a {@link Class}. It provides getter and setter methods
 * for both fields and a {@code toString} method for representing the object as a string.</p>
 */
public class AttributeDefaultValue {

    /**
     * The default value of the attribute.
     */
    private Object defaultValue;

    /**
     * The data type of the default value.
     */
    private Class<?> defaultValueDataType;

    /**
     * Sets the default value of the attribute.
     *
     * @param defaultValue An {@link Object} representing the default value.
     */
    public void setAttrDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * Sets the data type of the default value.
     *
     * @param defaultValueDataType A {@link Class} object representing the data type of the default value.
     */
    public void setDefaultValueDataType(Class<?> defaultValueDataType) {
        this.defaultValueDataType = defaultValueDataType;
    }

    /**
     * Gets the default value of the attribute.
     *
     * @return An {@link Object} representing the default value.
     */
    public Object getAttrDefaultValue() {
        return defaultValue;
    }

    /**
     * Gets the data type of the default value.
     *
     * @return A {@link Class} object representing the data type of the default value.
     */
    public Class<?> getAttrDefaultValueDataType() {
        return defaultValueDataType;
    }

    /**
     * Returns a string representation of the {@code AttributeDefaultValue} object.
     *
     * @return A string containing the default value and its data type.
     */
    @Override
    public String toString() {
        return "AttributeDefaultValue{" +
            "defaultValue=" + defaultValue +
            ", defaultValueDataType=" + defaultValueDataType +
            '}';
    }
}
