package assignmentevaluator.evaluationHelpers.testsetup.classlevel;

import assignmentevaluator.evaluationHelpers.testsetup.TestSetupService;
import assignmenttests.classlevel.products.attribute.supports.AttributeDataType;
import assignmenttests.classlevel.products.attribute.supports.AttributeDefaultValue;

import java.util.HashMap;

public class AttributeTestSetup extends TestSetupService {
    public AttributeTestSetup(){
        map = new HashMap<>();
        map.put("attributeName",null);
        map.put("dataType",null);
        map.put("default",null);
        map.put("isFinal",null);
        map.put("isStatic",null);
    }
    public void addAttributeName(String attrName){
        map.put("attributeName",attrName);
    }
    public void addDataType(AttributeDataType attrDataType){
        map.put("dataType",attrDataType);
    }
    public void addDefault(AttributeDefaultValue defaultValue){
        map.put("default",defaultValue);
    }
    public void addIsFinal(boolean isAttrFinal){
        map.put("isFinal", isAttrFinal);
    }
    public void addIsStatic(boolean isAttrFinal){
        map.put("isStatic", isAttrFinal);
    }
}
