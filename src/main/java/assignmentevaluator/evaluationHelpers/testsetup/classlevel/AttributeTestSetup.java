package assignmentevaluator.evaluationHelpers.testsetup.classlevel;

import java.util.HashMap;

import assignmentevaluator.evaluationHelpers.testsetup.TestSetupService;
import assignmenttests.classlevel.products.attribute.supports.AttributeDataType;
import assignmenttests.classlevel.products.attribute.supports.AttributeDefaultValue;

/**
 * Configures the setup for testing class attributes.
 * This class enables defining the properties of an attribute, such as name, data type,
 * default value, and whether it is marked as `static` or `final`.
 */
public class AttributeTestSetup extends TestSetupService {

    /**
     * Constructor that initializes the attribute test setup with default values.
     * The configuration map includes keys for commonly tested properties of attributes.
     */
    public AttributeTestSetup() {
        map = new HashMap<>();
        map.put("attributeName", null);
        map.put("dataType", null);
        map.put("default", null);
        map.put("isFinal", null);
        map.put("isStatic", null);
    }

    /**
     * Sets the name of the attribute to be tested.
     *
     * @param attrName the name of the attribute.
     */
    public void addAttributeName(String attrName) {
        map.put("attributeName", attrName);
    }

    /**
     * Sets the data type of the attribute to be tested.
     *
     * @param attrDataType the data type of the attribute (e.g., int, String, etc.).
     */
    public void addDataType(AttributeDataType attrDataType) {
        map.put("dataType", attrDataType);
    }

    /**
     * Sets the default value of the attribute to be tested.
     *
     * @param defaultValue the default value of the attribute.
     */
    public void addDefault(AttributeDefaultValue defaultValue) {
        map.put("default", defaultValue);
    }

    /**
     * Specifies whether the attribute is marked as `final`.
     *
     * @param isAttrFinal `true` if the attribute is final; otherwise, `false`.
     */
    public void addIsFinal(boolean isAttrFinal) {
        map.put("isFinal", isAttrFinal);
    }

    /**
     * Specifies whether the attribute is marked as `static`.
     *
     * @param isAttrStatic `true` if the attribute is static; otherwise, `false`.
     */
    public void addIsStatic(boolean isAttrStatic) {
        map.put("isStatic", isAttrStatic);
    }
}
