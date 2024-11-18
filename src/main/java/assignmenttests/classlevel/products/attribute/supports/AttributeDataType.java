package assignmenttests.classlevel.products.attribute.supports;

/**
 * Represents the data type of an attribute, including its main type and optional generic type.
 *
 * <p>This class encapsulates a {@link Class} object for the main data type and an
 * {@link Object} for the generic type. It provides getter and setter methods for both fields.</p>
 */
public class AttributeDataType {

    /**
     * The main data type of the attribute.
     */
    private Class<?> dataType;

    /**
     * The generic type associated with the attribute (if applicable).
     */
    private Object generic;

    /**
     * Constructs an {@code AttributeDataType} instance with the specified main data type and generic type.
     *
     * @param inputDataType The main {@link Class} type of the attribute.
     * @param inputGeneric The generic {@link Object} type associated with the attribute.
     */
    public AttributeDataType(Class<?> inputDataType, Object inputGeneric) {
        this.dataType = inputDataType;
        this.generic = inputGeneric;
    }

    /**
     * Sets the main data type of the attribute.
     *
     * @param dataType A {@link Class} object representing the main data type.
     */
    public void setDataType(Class<?> dataType) {
        this.dataType = dataType;
    }

    /**
     * Gets the main data type of the attribute.
     *
     * @return A {@link Class} object representing the main data type.
     */
    public Class<?> getDataType() {
        return dataType;
    }

    /**
     * Sets the generic type associated with the attribute.
     *
     * @param generic An {@link Object} representing the generic type.
     */
    public void setGeneric(Object generic) {
        this.generic = generic;
    }

    /**
     * Gets the generic type associated with the attribute.
     *
     * @return An {@link Object} representing the generic type.
     */
    public Object getGeneric() {
        return generic;
    }
}
