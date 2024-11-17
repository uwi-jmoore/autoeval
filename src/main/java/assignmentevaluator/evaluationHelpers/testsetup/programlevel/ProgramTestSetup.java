package assignmentevaluator.evaluationHelpers.testsetup.programlevel;

import assignmentevaluator.evaluationHelpers.testsetup.TestSetupService;

import java.io.File;
import java.util.HashMap;

public abstract class ProgramTestSetup extends TestSetupService {
    public ProgramTestSetup(){
        map = new HashMap<>();
        map.put("assignmentDirectory",null);
    }
    public void addAssignmentDirectory(File assignmentDirectory){
        map.put("assignmentDirectory",assignmentDirectory);
    }
}
