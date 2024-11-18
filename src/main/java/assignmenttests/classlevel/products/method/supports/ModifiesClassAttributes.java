package assignmenttests.classlevel.products.method.supports;

import java.util.List;

/**
 * An interface that defines methods for modifying class attributes during testing.
 * Implementations of this interface should provide the functionality to:
 * 1. Set modified class attributes.
 * 2. Load modified class attributes from a provided value.
 * 3. Match modified attributes against a class instance to validate changes.
 */
public interface ModifiesClassAttributes {

    /**
     * Sets the modified class attributes for testing.
     * 
     * @param inputAttributes A list of modified class attributes (expected values).
     */
    void setModifiedClassAttributes(List<ClassTestAttributeExpectedValue> inputAttributes);

    /**
     * Loads the modified class attributes from the provided value.
     * The value may be a map or any other representation of the attributes.
     * 
     * @param mapListValue The value containing the modified class attributes.
     */
    void loadModifiedClassAttributes(Object mapListValue);

    /**
     * Matches the modified class attributes against the given class instance
     * to validate if the attributes have been correctly applied.
     * 
     * @param classInstance The class instance to check the modified attributes against.
     * @param modifiedAttributes The list of modified attributes to validate.
     * @return true if the modified attributes match the class instance, false otherwise.
     */
    boolean matchModifiedClassAttributes(Object classInstance, List<ClassTestAttributeExpectedValue> modifiedAttributes);
}
