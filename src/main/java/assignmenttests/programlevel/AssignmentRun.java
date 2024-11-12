package assignmenttests.programlevel;

import java.io.*;

import static assignmenttests.programlevel.ProgramLevelTestHelpers.compileAssignment;

/**
 * Class for running a compiled Java assignment and evaluating if it executes successfully.
 * This class compiles the assignment, runs the main file, and checks the exit code to
 * determine success.
 */
public class AssignmentRun extends AssignmentOperational {

    private int assignmentExitCode;

    /**
     * Evaluates the program-level test by compiling and running the assignment.
     *
     * @return true if the program runs successfully with an exit code of 0, false otherwise
     */
    @Override
    public boolean evaluateProgramLevelTest() {
        if (compileAssignment(getAssignmentDirectory())) {
            String directory = getAssignmentDirectory().getAbsolutePath();
            String className = getMainFile().getName().replace(".java", "");
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
                ioException.printStackTrace();
            } catch (InterruptedException interruptedException) {
                System.err.println("Thread running Assignment interrupted, InterruptedException occurred: "
                    + interruptedException.getMessage());
                interruptedException.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Sends test inputs to the running process simulating user input.
     *
     * @param runningProcess the process to which inputs are sent
     */
    private void runInputs(Process runningProcess) {
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
     * Retrieves the exit code of the assignment execution process.
     *
     * @return the exit code of the executed assignment process
     */
    public int getAssignmentExitCode() {
        return assignmentExitCode;
    }
}
