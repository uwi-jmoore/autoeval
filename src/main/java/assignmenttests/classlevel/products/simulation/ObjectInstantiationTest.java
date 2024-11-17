package assignmenttests.classlevel.products.simulation;


import assignmentevaluator.evaluationHelpers.AssignmentRunner;
import assignmenttests.classlevel.ClassTestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.*;
import java.util.List;
import java.util.Map;

import static assignmenttests.classlevel.ClassLevelHelpers.findMissingKeys;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ObjectInstantiationTest extends ClassTestBase implements ProgrammaticAction{
    protected static InstantiationAgent instantiationAgent;
    protected static File directory;

    public ObjectInstantiationTest(){
        instantiationAgent = new InstantiationAgent();
    }

    @Override
    public String toString() {
        return "Object Instantiation Test";
    }

    @Override
    public void setUpTestDetails(Map<String, Object> setUpContent) {
        List<String> expectedSetupContents = List.of(
            "object",
            "target",
            "file"
        );
        List<String> missingKeys = findMissingKeys(setUpContent,expectedSetupContents);
        if(missingKeys.isEmpty()){
            instantiationAgent.setTargetClassName(setUpContent.get("object").toString());
            ObjectInstantiationTest.directory = (File) setUpContent.get("file");


        }else{
            System.err.println("Missing keys in setup Map: "+missingKeys);
        }
    }

    @BeforeEach
    public void initInstantiationTest(){
        super.classTestBaseSetUp();
        instantiationAgent.setFile(directory);
    }
    @Test
    public void obTest(){
        assertTrue(executeAction(),"ChatBotPlatform instance should have been detected.");
    }


    @Override
    public boolean executeAction() {
        return instantiationAgent.findInstance(loadedClass);
    }
}
