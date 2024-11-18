package assignmentevaluator.evaluationHelpers.testsetup.classlevel;

import assignmentevaluator.evaluationHelpers.testsetup.TestSetupService;
import assignmenttests.classlevel.products.simulation.InstantiationMethodTarget;
import assignmenttests.classlevel.products.simulation.MethodCalls;

import java.util.HashMap;
import java.util.List;

public class CBSIMTestSetup extends TestSetupService {
    public CBSIMTestSetup(){
        map = new HashMap<>();
        map.put("consoleOut",null);
        map.put("methodCall",null);
        map.put("filepath",null);
    }
    public void setConsoleInputs(List<String> ConsoleInputs){
        map.put("consoleOut", ConsoleInputs);
    }
    public void setMethodCalls(List<MethodCalls> methodCalls){
        map.put("methodCall",methodCalls);
    }
    public void setFilePath(String filePath){
        map.put("filepath",filePath);
    }
}
