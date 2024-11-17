package assignmenttests.classlevel.products.simulation;

import assignmentevaluator.evaluationHelpers.AssignmentRunner;
import assignmenttests.classlevel.ClassTestBase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static assignmenttests.classlevel.ClassLevelHelpers.findMissingKeys;
import static assignmenttests.classlevel.products.simulation.MethodCallCounter.countMethodCalls;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CBSIM extends ClassTestBase {
    protected static File assignmentDirectory;
    protected AssignmentRunner runner;

    protected static List<String> expectedConsoleOutputs;
    protected static List<MethodCalls> methodCalls;
    protected static String filePath;
    public CBSIM(){
        runner = new AssignmentRunner();
    }

    protected void setMethodCalls(List<MethodCalls> methodCalls){
        CBSIM.methodCalls = methodCalls;
    }
    protected void setExpectedConsoleOutputs(List<String> expectedConsoleOutputs){
        CBSIM.expectedConsoleOutputs = expectedConsoleOutputs;
    }

    protected void setFilePath(String filePath){
        CBSIM.filePath = filePath;
    }

    protected boolean streamOutputMatch(String component){
        String[] broken = component.split("[@]");
        for(String s: broken){
            if(!runner.getRunnerOutputStream().contains(s)){
                return false;
            }
        }
        return true;
    }

    protected boolean testMethodCalls() throws IOException {
        for(MethodCalls methodCall : methodCalls){
            if(countMethodCalls(filePath,methodCall.methodName()) < methodCall.expectedCalls()){
                return false;
            }
        }
        return true;
    }
    @Test
    public void testHelloWorld(){
        assertTrue(runner.getRunnerOutputStream().contains(expectedConsoleOutputs.getFirst()));//Testing against "Hello World"
    }

    @Test
    public void testChatBotStats(){
        assertTrue(streamOutputMatch(expectedConsoleOutputs.get(1)));
    }

    @Test
    public void createdChatBots() throws IOException {
        assertTrue(testMethodCalls());
    }

    @Override
    public void setUpTestDetails(Map<String, Object> setUpContent) {
        List<String> expectedSetupContents = List.of(
            "consoleOut",
            "methodCall",
            "file"
        );
        List<String> missingKeys = findMissingKeys(setUpContent,expectedSetupContents);
        if(missingKeys.isEmpty()){
            setUpSimTest(setUpContent);
        }else{
            System.err.println("Missing keys in setup Map: "+missingKeys);
        }
    }
    protected void setUpSimTest(Map<String, Object> setUpContent){
        super.classTestBaseSetUp();
    }
}
