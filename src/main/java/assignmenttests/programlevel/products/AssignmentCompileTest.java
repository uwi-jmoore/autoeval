package assignmenttests.programlevel.products;


import assignmenttests.programlevel.ProgramTestBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;


import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AssignmentCompileTest extends ProgramTestBase {
    @Override
    public String toString() {
        return "Assignment Compile Test";
    }

    @Test
    @DisplayName("Assignment Compile Test")
    public void testAssignmentCompile(){
        assertTrue(compileAssignmentActual(),"Assignment Does Not Compile");
    }
}
