package assignmentevaluator.evaluationHelpers;

import assignmenttests.classlevel.products.method.supports.ClassTestAttributeExpectedValue;
import assignmenttests.classlevel.products.method.supports.ClassTestAttributeTestValue;
import assignmenttests.classlevel.products.method.supports.ClassTestParameter;
import assignmenttests.classlevel.products.method.supports.MethodReturn;

import java.util.List;

public class CustomCase {
    //Inputs
    private List<ClassTestParameter> customParams;
    private List<ClassTestAttributeTestValue> customTestAttributeValue;

    //Expected
    private MethodReturn customReturn;
    private List<ClassTestAttributeExpectedValue> customExpectedAttributeValue;

    public List<ClassTestAttributeExpectedValue> getCustomExpectedAttributeValue() {
        return customExpectedAttributeValue;
    }

    public List<ClassTestAttributeTestValue> getCustomTestAttributeValue() {
        return customTestAttributeValue;
    }

    public List<ClassTestParameter> getCustomParams() {
        return customParams;
    }

    public MethodReturn getCustomReturn() {
        return customReturn;
    }

    public void setCustomExpectedAttributeValue(List<ClassTestAttributeExpectedValue> customExpectedAttributeValue) {
        this.customExpectedAttributeValue = customExpectedAttributeValue;
    }

    public void setCustomParams(List<ClassTestParameter> customParams) {
        this.customParams = customParams;
    }

    public void setCustomReturn(MethodReturn customReturn) {
        this.customReturn = customReturn;
    }

    public void setCustomTestAttributeValue(List<ClassTestAttributeTestValue> customTestAttributeValue) {
        this.customTestAttributeValue = customTestAttributeValue;
    }

    @Override
    public String toString() {
        return "parameters= <"+getCustomParams()+
            ">; Set Attribute values= <"+ getCustomTestAttributeValue()+
            ">; Expected Modified Attributes= <"+getCustomExpectedAttributeValue()+
            ">; Method Return= <"+getCustomReturn()+
            ">";
    }
}
