package ProgramLevelTest;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import assignmenttests.programlevel.AssignmentCompile;
import assignmenttests.programlevel.AssignmentRun;
import static filehandler.filehelperservice.FileOperationHelpers.pathToFile;

/**
 * The AssignmentProgramLevelTest class is a JUnit test class that performs program-level
 * tests on assignments to verify successful compilation and execution of the main class file.
 *
 * <p>This class includes tests for compiling and running an assignment's main class file.
 * Each test verifies specific aspects of the program's functionality, including whether it compiles
 * correctly and runs as expected, with a focus on awarding appropriate marks for successful execution.
 */
public class AssignmentProgramLevelTest {

    /**
     * Tests if the assignment compiles successfully.
     * <p>
     * This test sets up the assignment directory and marks for the compilation process.
     * It then verifies that the compilation is successful and that the marks are awarded correctly.
     */
    @Test
    public void testCompileAssignment() {
        AssignmentCompile compileTest = new AssignmentCompile();

        compileTest.setAssignmentDirectory(pathToFile("C:\\Users\\Chimera\\Desktop\\Project_Testbed\\Unzipping\\" +
            "extraction_test\\Assignments-main_assign\\Isabella_Augustus_816031354_A1\\Assignment_1"));
        compileTest.setMarks(5);

        assertAll("",
            () -> assertTrue(compileTest.evaluateProgramLevelTest()),
            () -> assertEquals(5, compileTest.getMarks())
        );
    }

    /**
     * Tests if the assignment runs successfully.
     * <p>
     * This test sets the assignment directory and main class file for the execution process.
     * It then verifies that the main class file exists, that the program runs without issues,
     * and that the marks are awarded correctly.
     */
    @Test
    public void testRunAssignment() {
        AssignmentRun runTest = new AssignmentRun();

        File mainClass = pathToFile("C:\\Users\\Chimera\\Desktop\\Project_Testbed\\Unzipping\\" +
            "extraction_test\\Assignments-main_assign\\Isabella_Augustus_816031354_A1\\" +
            "Assignment_1\\ChatBotSimulation.java");

        File assignmentDirectory = mainClass.getParentFile();
        runTest.setAssignmentDirectory(assignmentDirectory);

        runTest.setMainFile(mainClass);
        runTest.setMarks(10);

        assertAll("Testing running of assignment",
            () -> assertThat(assignmentDirectory.listFiles()).contains(mainClass),
            () -> assertTrue(runTest.evaluateProgramLevelTest()),
            () -> assertEquals(10, runTest.getMarks())
        );
    }
}
