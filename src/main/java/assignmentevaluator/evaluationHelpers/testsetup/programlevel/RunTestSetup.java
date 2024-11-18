package assignmentevaluator.evaluationHelpers.testsetup.programlevel;

import java.io.File;
import java.util.List;

/**
 * Class responsible for setting up the necessary details for running program-level tests.
 * It extends {@link ProgramTestSetup} and specifically handles setting up the main file and inputs 
 * that will be used during the execution of the program-level tests.
 */
public class RunTestSetup extends ProgramTestSetup {

    /**
     * Constructor for the RunTestSetup class. 
     * It initializes the test setup with default values, setting the "mainFile" and "inputs" to null initially.
     */
    public RunTestSetup(){
        super();
        map.put("mainFile", null);
        map.put("inputs", null);
    }

    /**
     * Adds the main file to the test setup.
     * The main file represents the primary entry point for the program that will be tested.
     * 
     * @param mainFile The main file that contains the entry point of the program
     */
    public void addMainFile(File mainFile){
        map.put("mainFile", mainFile);
    }

    /**
     * Adds a list of inputs to the test setup.
     * The inputs are the data that will be passed to the program for testing purposes.
     * 
     * @param inputs A list of input objects to be used by the program during its execution
     */
    public void addInput(List<Object> inputs){
        map.put("inputs", inputs);
    }
}
