package assignmentevaluator.evaluationHelpers.testsetup.programlevel;

import java.io.File;
import java.util.HashMap;

import assignmentevaluator.evaluationHelpers.testsetup.TestSetupService;

/**
 * Abstract class that provides a setup service for program-level tests.
 * This class extends {@link TestSetupService} and is responsible for initializing 
 * the setup map and allowing the addition of the assignment directory to the test setup.
 */
public abstract class ProgramTestSetup extends TestSetupService {

    /**
     * Constructor for the ProgramTestSetup class.
     * It initializes the setup map and sets the "assignmentDirectory" entry to null.
     * The assignment directory will later be provided through the {@link #addAssignmentDirectory(File)} method.
     */
    public ProgramTestSetup(){
        map = new HashMap<>();
        map.put("assignmentDirectory", null);
    }

    /**
     * Adds the assignment directory to the test setup.
     * The assignment directory is where the student's assignment files are stored and is needed for running the test.
     *
     * @param assignmentDirectory The directory where the assignment files are located.
     */
    public void addAssignmentDirectory(File assignmentDirectory){
        map.put("assignmentDirectory", assignmentDirectory);
    }
}
