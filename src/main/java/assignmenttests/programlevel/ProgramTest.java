package assignmenttests.programlevel;

import assignmenttests.AssignmentTest;

/**
 * Interface for a program-level test on an assignment.
 * Extends {@link AssignmentTest} to include evaluation functionality specific to program-level criteria.
 * Implementing classes should define how program-level tests are evaluated and scored.
 */
public interface ProgramTest extends AssignmentTest {

    /**
     * Evaluates the program-level test based on the criteria defined in the implementation.
     *
     * @return true if the program-level test passes, false otherwise
     */
    boolean evaluateProgramLevelTest();
}
