package assignmenttests.classlevel.products.method.supports;

import java.util.List;

/**
 * An interface that defines methods for managing method parameters during testing.
 * Implementations of this interface should provide functionality for:
 * 1. Setting method parameters.
 * 2. Loading method parameters from a provided value.
 * 3. Retrieving the values and types of input parameters.
 */
public interface ParameterComposed {

    /**
     * Sets the input parameters for the method being tested.
     * 
     * @param inputParams A list of method parameters, each represented by a 
     *                    ClassTestParameter object that contains the parameter's 
     *                    data type and value.
     */
    void setParameterInput(List<ClassTestParameter> inputParams);

    /**
     * Loads the method parameters from the provided value.
     * 
     * @param mapListValue The value containing the method parameters (e.g., a map or list).
     */
    void loadMethodParameters(Object mapListValue);

    /**
     * Retrieves the values of the input parameters for the method.
     * 
     * @return An array of objects representing the values of the input parameters.
     */
    Object[] getInputParametersValues();

    /**
     * Retrieves the data types of the input parameters for the method.
     * 
     * @return An array of Class objects representing the data types of the input parameters.
     */
    Class<?>[] getInputParameterTypes();
}
