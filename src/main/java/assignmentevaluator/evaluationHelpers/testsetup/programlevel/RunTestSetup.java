package assignmentevaluator.evaluationHelpers.testsetup.programlevel;

import java.io.File;
import java.util.List;

public class RunTestSetup extends ProgramTestSetup{
    public RunTestSetup(){
        super();
        map.put("mainFile",null);
        map.put("inputs",null);
    }
    public void addMainFile(File mainFile){
        map.put("mainFile",mainFile);
    }

    public void addInput(List<Object> inputs){
        map.put("inputs",inputs);
    }
}
