package assignmentevaluator.evaluationHelpers.testsetup.classlevel;

import assignmentevaluator.evaluationHelpers.testsetup.TestSetupService;
import assignmenttests.classlevel.products.simulation.InstantiationMethodTarget;

import java.io.File;
import java.util.HashMap;

public class ObjectInstantiationTestSetup extends TestSetupService {
    public ObjectInstantiationTestSetup(){
        map = new HashMap<>();
        map.put("object",null);
        map.put("target",null);
        map.put("file",null);
    }
    public void setObject(Object inObj){
        map.put("object",inObj);
    }
    public void setMethodTarget(InstantiationMethodTarget inMethod){
        map.put("target",inMethod);
    }
    public void setFile(File directory){
        map.put("file",directory);
    }
}
