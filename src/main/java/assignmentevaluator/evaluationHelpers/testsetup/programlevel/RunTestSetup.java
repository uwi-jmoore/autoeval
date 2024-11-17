package assignmentevaluator.evaluationHelpers.testsetup.programlevel;

import java.io.File;

public class RunTestSetup extends ProgramTestSetup{
    public RunTestSetup(){
        super();
        map.put("mainFile",null);
    }
    public void addMainFile(File mainFile){
        map.put("mainFile",mainFile);
    }
}
