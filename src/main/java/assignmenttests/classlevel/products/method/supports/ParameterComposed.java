package assignmenttests.classlevel.products.method.supports;

import java.util.List;

public interface ParameterComposed {
    void setParameterInput(List<ClassTestParameter> inputParams);
    void loadMethodParameters(Object mapListValue);
    Object[] getInputParametersValues();
    Class<?>[] getInputParameterTypes();
}
