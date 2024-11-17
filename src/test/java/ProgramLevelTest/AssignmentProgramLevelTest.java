//package ProgramLevelTest;
//
//import org.junit.jupiter.api.Test;
//import assignmenttests.programlevel.AssignmentCompile;
//import assignmenttests.programlevel.AssignmentRun;
//
//import java.io.File;
//
//import static filehandler.filehelperservice.FileOperationHelpers.pathToFile;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//public class AssignmentProgramLevelTest {
//    @Test
//    public void testCompileAssignment(){
//        AssignmentCompile compileTest = new AssignmentCompile();
//
//        compileTest.setAssignmentDirectory(pathToFile("C:\\Users\\Chimera\\Desktop\\Project_Testbed\\Unzipping\\" +
//            "extraction_test\\Assignments-main_assign\\Isabella_Augustus_816031354_A1\\Assignment_1"));
//        compileTest.setMarks(5);
//        assertAll("",
//            () -> assertTrue(compileTest.evaluateProgramLevelTest()),
//            () -> assertEquals(5,compileTest.getMarks())
//        );
//    }
//
//    @Test
//    public void testRunAssignment(){
//        AssignmentRun runTest = new AssignmentRun();
//
//        File mainClass = pathToFile("C:\\Users\\Chimera\\Desktop\\Project_Testbed\\Unzipping\\" +
//            "extraction_test\\Assignments-main_assign\\Isabella_Augustus_816031354_A1\\" +
//            "Assignment_1\\ChatBotSimulation.java");
////        File assignmentDirectory = pathToFile("C:\\Users\\Chimera\\Desktop\\Project_Testbed\\Unzipping\\" +
////            "extraction_test\\Assignments-main_assign\\Assignment_1");
//
//        File assignmentDirectory = mainClass.getParentFile();
//        runTest.setAssignmentDirectory(assignmentDirectory);
//
//        runTest.setMainFile(mainClass);
//        runTest.setMarks(10);
//        assertAll("Testing running of assignment",
//            () -> assertThat(assignmentDirectory.listFiles()).contains(mainClass),
//            () -> assertTrue(runTest.evaluateProgramLevelTest()),
//            () -> assertEquals(10, runTest.getMarks())
//
//        );
//    }
//
//
//}
