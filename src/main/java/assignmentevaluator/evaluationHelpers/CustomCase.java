package assignmentevaluator.evaluationHelpers;

import java.util.List;

import assignmenttests.classlevel.products.method.supports.ClassTestAttributeExpectedValue;
import assignmenttests.classlevel.products.method.supports.ClassTestAttributeTestValue;
import assignmenttests.classlevel.products.method.supports.ClassTestParameter;
import assignmenttests.classlevel.products.method.supports.MethodReturn;

/**
 * This class represents a custom test case for evaluating assignments.
 * It encapsulates the input parameters, test attribute values, expected attribute values,
 * and the method return that defines the expected behavior of the assignment.
 */
public class CustomCase {
    // Inputs
    private List<ClassTestParameter> customParams;
    private List<ClassTestAttributeTestValue> customTestAttributeValue;

    // Expected
    private MethodReturn customReturn;
    private List<ClassTestAttributeExpectedValue> customExpectedAttributeValue;

    /**
     * Gets the expected attribute values for the test case.
     * 
     * @return a list of expected attribute values
     */
    public List<ClassTestAttributeExpectedValue> getCustomExpectedAttributeValue() {
        return customExpectedAttributeValue;
    }

    /**
     * Gets the test attribute values for the test case.
     * 
     * @return a list of test attribute values
     */
    public List<ClassTestAttributeTestValue> getCustomTestAttributeValue() {
        return customTestAttributeValue;
    }

    /**
     * Gets the input parameters for the test case.
     * 
     * @return a list of test parameters
     */
    public List<ClassTestParameter> getCustomParams() {
        return customParams;
    }

    /**
     * Gets the expected method return value for the test case.
     * 
     * @return the expected method return value
     */
    public MethodReturn getCustomReturn() {
        return customReturn;
    }

    /**
     * Sets the expected attribute values for the test case.
     * 
     * @param customExpectedAttributeValue a list of expected attribute values
     */
    public void setCustomExpectedAttributeValue(List<ClassTestAttributeExpectedValue> customExpectedAttributeValue) {
        this.customExpectedAttributeValue = customExpectedAttributeValue;
    }

    /**
     * Sets the input parameters for the test case.
     * 
     * @param customParams a list of test parameters
     */
    public void setCustomParams(List<ClassTestParameter> customParams) {
        this.customParams = customParams;
    }

    /**
     * Sets the expected method return value for the test case.
     * 
     * @param customReturn the expected method return value
     */
    public void setCustomReturn(MethodReturn customReturn) {
        this.customReturn = customReturn;
    }

    /**
     * Sets the test attribute values for the test case.
     * 
     * @param customTestAttributeValue a list of test attribute values
     */
    public void setCustomTestAttributeValue(List<ClassTestAttributeTestValue> customTestAttributeValue) {
        this.customTestAttributeValue = customTestAttributeValue;
    }

    /**
     * Provides a string representation of the custom test case.
     * It includes the input parameters, test attribute values, expected attribute values,
     * and the expected method return.
     *
     * @return a string representation of the custom test case
     */
    @Override
    public String toString() {
        return "parameters= <"+getCustomParams()+
            ">; Set Attribute values= <"+ getCustomTestAttributeValue()+
            ">; Expected Modified Attributes= <"+getCustomExpectedAttributeValue()+
            ">; Method Return= <"+getCustomReturn()+
            ">";
    }
}
