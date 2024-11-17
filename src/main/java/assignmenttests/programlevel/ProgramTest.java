package assignmenttests.programlevel;

import assignmenttests.AssignmentTest;

import java.io.File;

/**
 * Interface for a program-level test on an assignment.
 * Extends {@link AssignmentTest} to include evaluation functionality specific to program-level criteria.
 * Implementing classes should define how program-level tests are evaluated and scored.
 */
public interface ProgramTest extends AssignmentTest {

//    boolean evaluateProgramLevelTest();
    void setAssignmentDirectory(File assignmentDirectory);
}
