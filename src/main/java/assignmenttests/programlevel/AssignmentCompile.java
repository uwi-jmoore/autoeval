package assignmenttests.programlevel;

import static assignmenttests.programlevel.ProgramLevelTestHelpers.compileAssignment;

public class AssignmentCompile extends AssignmentOperational{
    @Override
    public boolean evaluateProgramLevelTest() {
        return compileAssignment(getAssignmentDirectory());
    }
}
