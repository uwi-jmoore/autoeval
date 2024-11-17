package assignmenttests.programlevel.products;

import assignmenttests.programlevel.ProgramTestBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static assignmenttests.classlevel.ClassLevelHelpers.findMissingKeys;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AssignmentRunTest extends ProgramTestBase {
    @Override
    public String toString() {
        return "Assignment Run Test";
    }

    protected static File assignmentMainFile;
    private int assignmentExitCode;

    public int getAssignmentExitCode() {
        return assignmentExitCode;
    }

    public void setAssignmentMainFile(File mainFile){
        AssignmentRunTest.assignmentMainFile = mainFile;
    }

    protected void setRunTestDetails(Map<String, Object> setUpContent){
        super.setCompileTestDetails(setUpContent);
        setAssignmentMainFile((File) setUpContent.get("mainFile"));
    }

    @Override
    public void setUpTestDetails(Map<String, Object> setUpContent) {
        List<String> expectedSetupContents = Arrays.asList(
            "assignmentDirectory",
            "mainFile"
        );
        List<String> missingKeys = findMissingKeys(setUpContent,expectedSetupContents);
        if(missingKeys.isEmpty()){
            setRunTestDetails(setUpContent);
        }else {
            System.err.println("Missing keys in setup Map: "+missingKeys);
        }
    }

    /**
     * Sends test inputs to the running process simulating user input.
     *
     * @param runningProcess the process to which inputs are sent
     */
    protected void runInputs(Process runningProcess) {
        try (OutputStream os = runningProcess.getOutputStream()) {
            Thread.sleep(100); // 100 milliseconds delay for process readiness
            for (int i = 0; i < 15; i++) {
                os.write(("test" + i + "\n").getBytes());
                os.flush();
                Thread.sleep(100); // Delay for each input to be processed
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Evaluates the program-level test by compiling and running the assignment.
     *
     * @return true if the program runs successfully with an exit code of 0, false otherwise
     */
    protected boolean runAssignment(){
        if(compileAssignmentActual()){
            String directory = assignmentDirectory.getAbsolutePath();
            String className = assignmentMainFile.getName().replace(".java", "");
            ProcessBuilder processBuilder = new ProcessBuilder(
                "java",
                "-cp",
                directory,
                className
            );
            try {
                Process process = processBuilder.start();
                runInputs(process);
                int exitCode = process.waitFor();
                assignmentExitCode = exitCode;
                return exitCode == 0;
            } catch (IOException ioException) {
                System.err.println("Issue in starting thread for Assignment, IOException occurred: "
                    + ioException.getMessage());
            } catch (InterruptedException interruptedException) {
                System.err.println("Thread running Assignment interrupted, InterruptedException occurred: "
                    + interruptedException.getMessage());
            }
        }
        return false;
    }
    @Test
    @DisplayName("Test Assignment Compiles")
    public void testCompiling(){
        assertTrue(compileAssignmentActual(),"Assignment Does Not Compile");
    }

    @Test
    @DisplayName("Test Assignment Runs")
    public void testRunning(){
        assertTrue(runAssignment(),"Assignment Failed to run successfully");
    }
}
