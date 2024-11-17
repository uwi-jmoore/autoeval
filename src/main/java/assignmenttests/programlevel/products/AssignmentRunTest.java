package assignmenttests.programlevel.products;

import assignmentevaluator.evaluationHelpers.AssignmentRunner;
import assignmenttests.programlevel.ProgramTestBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static assignmenttests.classlevel.ClassLevelHelpers.findMissingKeys;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AssignmentRunTest extends ProgramTestBase {

    public AssignmentRunTest(){
        super();
    }

    @Override
    public String toString() {
        return "Assignment Run Test";
    }

    protected static File assignmentMainFile;


    public void setAssignmentMainFile(File mainFile){
        AssignmentRunTest.assignmentMainFile = mainFile;
    }

    protected void setRunTestDetails(Map<String, Object> setUpContent){
        super.setCompileTestDetails(setUpContent);
        setAssignmentMainFile((File) setUpContent.get("mainFile"));
        runner.setRunnerMainMethodFile(assignmentMainFile);
        @SuppressWarnings("unchecked")
        List<Object> list = (List<Object>) setUpContent.get("inputs");
        runner.addTestInputList(list);
    }

    @Override
    public void setUpTestDetails(Map<String, Object> setUpContent) {
        List<String> expectedSetupContents = Arrays.asList(
            "assignmentDirectory",
            "mainFile",
            "inputs"
        );
        List<String> missingKeys = findMissingKeys(setUpContent,expectedSetupContents);
        if(missingKeys.isEmpty()){
            setRunTestDetails(setUpContent);
        }else {
            System.err.println("Missing keys in setup Map: "+missingKeys);
        }
    }


    @Test
    @DisplayName("Test Assignment Compiles")
    public void testCompiling(){
        assertTrue(runner.compileAssignment(),"Assignment Does Not Compile");
    }

    @Test
    @DisplayName("Test Assignment Runs")
    public void testRunning(){
        assertTrue(runner.runAssignment(),"Assignment Failed to run successfully, exitcode "
            + runner.getAssignmentExitCode()
            + " returned");
    }
}
