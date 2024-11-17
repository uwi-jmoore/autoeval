package assignmenttests.programlevel;

import static assignmenttests.programlevel.ProgramLevelTestHelpers.compileAssignment;

/**
 * Class for compiling an assignment and evaluating if it meets the required program-level tests.
 */
public class AssignmentCompile extends AssignmentOperational {

    /**
     * Evaluates the program-level test by attempting to compile the assignment.
     *
     * @return true if the compilation succeeds, false otherwise
     */
    @Override
    public boolean evaluateProgramLevelTest() {
        return compileAssignment(getAssignmentDirectory());
    }
}
