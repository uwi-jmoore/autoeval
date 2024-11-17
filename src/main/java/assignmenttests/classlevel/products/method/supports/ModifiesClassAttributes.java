package assignmenttests.classlevel.products.method.supports;

import java.util.List;

public interface ModifiesClassAttributes {
    void setModifiedClassAttributes(List<ClassTestAttributeExpectedValue> inputAttributes);
    void loadModifiedClassAttributes(Object mapListValue);
    boolean matchModifiedClassAttributes(Object classInstance, List<ClassTestAttributeExpectedValue> modifiedAttributes);
}
