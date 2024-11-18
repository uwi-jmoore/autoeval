package assignmentevaluator.evaluationHelpers;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import static filehandler.filehelperservice.FileOperationHelpers.getDirectoryFilesOfExt;

/**
 * The AssignmentRunner class is responsible for compiling, running, and managing the execution
 * of Java assignments. It provides methods to set up assignment directories, handle test inputs,
 * and capture output from the execution process. This class helps in running the assignments and
 * simulating the necessary inputs for testing purposes.
 */
public class AssignmentRunner {
    
    /** The directory containing the assignment files. */
    protected static File assignmentDirectory;

    /** The main method file of the assignment. */
    protected static File mainMethodFile;

    /** The list of inputs for the assignment's main method. */
    protected static List<Object> testInputs;

    /** The exit code from the assignment's execution process. */
    protected static int assignmentExitCode;

    /** The fields of the main class in the assignment. */
    protected static Field[] mainClassFields;

    /** The output generated during the execution of the assignment. */
    protected static String runnerOutputStream;

    // No-arg constructor for initializing testInputs.
    // public AssignmentRunner(){
    //     AssignmentRunner.testInputs = new ArrayList<>();
    // }

    /**
     * Sets the directory where the assignment files are located.
     *
     * @param assignmentDirectory the directory containing the assignment files.
     */
    public void setRunnerAssignmentDirectory(File assignmentDirectory) {
        AssignmentRunner.assignmentDirectory = assignmentDirectory;
    }

    /**
     * Sets the main method file to be executed from the assignment.
     *
     * @param mainMethodFile the file containing the main method of the assignment.
     */
    public void setRunnerMainMethodFile(File mainMethodFile){
        AssignmentRunner.mainMethodFile = mainMethodFile;
    }

    /**
     * Adds a list of test inputs to be sent to the running assignment process.
     *
     * @param inputList the list of inputs to be provided to the assignment.
     */
    public void addTestInputList(List<Object> inputList){
        AssignmentRunner.testInputs = inputList;
    }

    /**
     * Gets the fields of the main class in the assignment.
     *
     * @return an array of Field objects representing the fields of the main class.
     */
    public Field[] getMainClassFields(){
        return mainClassFields;
    }

    /**
     * Gets the captured output from the assignment's execution.
     *
     * @return the output from the assignment execution as a string.
     */
    public String getRunnerOutputStream() {
        return runnerOutputStream;
    }

    /**
     * Gets the exit code of the assignment process after execution.
     *
     * @return the exit code of the process.
     */
    public int getAssignmentExitCode() {
        return assignmentExitCode;
    }

    /**
     * Compiles the assignment Java files using the system's Java compiler.
     * If the compilation is successful, returns true; otherwise, false.
     *
     * @return true if compilation is successful, false otherwise.
     */
    public boolean compileAssignment(){
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            System.err.println("Could Not Compile, JDK missing");
            return false;
        }

        File[] assignmentFiles = getDirectoryFilesOfExt(assignmentDirectory, ".java");
        assert assignmentFiles != null;

        if (assignmentFiles.length == 0) {
            System.err.println("No Java Files found");
            return false;
        }

        int result = compiler.run(null, null, null,
            Arrays.stream(assignmentFiles)
                .map(File::getPath)
                .toArray(String[]::new)
        );

        if (result == 0) {
            return true;
        }

        System.err.println("Compilation failed");
        return false;
    }

    /**
     * Runs the compiled assignment by executing its main method and simulating input.
     * If successful, captures the output and exit code of the process.
     *
     * @return true if the assignment ran successfully, false otherwise.
     */
    public boolean runAssignment(){
        if(compileAssignment()){
            String directory = assignmentDirectory.getAbsolutePath();
            String className = mainMethodFile.getName().replace(".java", "");
            ProcessBuilder processBuilder = new ProcessBuilder(
                "java",
                "-cp",
                directory,
                className
            );
            try {
                Process process = processBuilder.start();
                // Capture the output of the process
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                Thread outputThread = new Thread(() -> captureProcessOutput(process.getInputStream(), outputStream));
                outputThread.start();

                if(testInputs == null || testInputs.isEmpty()){
                    runStandardInput(process);
                } else {
                    runInputList(process);
                }
                int exitCode = process.waitFor();
                outputThread.join();
                assignmentExitCode = exitCode;
                runnerOutputStream = outputStream.toString();
                return exitCode == 0;
            } catch (IOException | InterruptedException e) {
                System.err.println("Error in running assignment: " + e.getMessage());
            }
        }
        return false;
    }

    /**
     * Captures the output from an InputStream (either standard or error stream) into a ByteArrayOutputStream.
     * 
     * @param inputStream the InputStream to read the output from.
     * @param outputStream the ByteArrayOutputStream to store the captured output.
     */
    private void captureProcessOutput(InputStream inputStream, ByteArrayOutputStream outputStream) {
        try {
            int byteRead;
            while ((byteRead = inputStream.read()) != -1) {
                outputStream.write(byteRead);
            }
        } catch (IOException e) {
            System.err.println("Error reading process output: " + e.getMessage());
        }
    }

    /**
     * Sends simulated standard inputs to the running process.
     * Used when there are no specific test inputs to send.
     *
     * @param process the process that is running the assignment.
     */
    protected void runStandardInput(Process process){
        try{
            try (OutputStream os = process.getOutputStream()){
                for(int i = 0; i < 15; i++){
                    Thread.sleep(100);
                    os.write(("passOver" + "\n").getBytes());
                    os.flush();
                    Thread.sleep(100);
                }
            } catch (IOException ioException) {
                System.err.println("IOException occurred while trying to write input to running process: "
                    + process.pid()
                    + ". Reason: "
                    + ioException.getMessage()
                );
            }
        } catch (InterruptedException interruptedException){
            System.err.println("InterruptedException occurred. Reason: "+ interruptedException.getMessage());
        }
    }

    /**
     * Sends a list of test inputs to the running process simulating user input.
     *
     * @param runningProcess the process to which inputs are sent.
     */
    public void runInputList(Process runningProcess) {
        try{
            try (OutputStream os = runningProcess.getOutputStream()){
                for(Object testInput: testInputs){
                    Thread.sleep(100);
                    os.write((testInput.toString() + "\n").getBytes());
                    os.flush();
                    Thread.sleep(100);
                }
            } catch (IOException ioException) {
                System.err.println("IOException occurred while trying to write input to running process: "
                    + runningProcess.pid()
                    + ". Reason: "
                    + ioException.getMessage()
                );
            }
        } catch (InterruptedException interruptedException){
            System.err.println("InterruptedException occurred. Reason: "+ interruptedException.getMessage());
        }
    }

    /**
     * Sends a custom input to the running process if it is alive.
     *
     * @param runningProcess the process to which the custom input is sent.
     * @param customIn the custom input to send to the process.
     */
    public void runInputCustom(Process runningProcess, Object customIn){
        if (!runningProcess.isAlive()) {
            System.err.println("Process is no longer alive.");
            return;
        }
        try (OutputStream os = runningProcess.getOutputStream()){
            os.write((customIn.toString() + "\n").getBytes());
            os.flush();
        } catch (IOException ioException) {
            System.err.println("IOException occurred while trying to write input to running process: "
                + runningProcess.pid()
                + ". Reason: "
                + ioException.getMessage()
            );
        }
    }

    /**
     * Reads the output from a running process.
     *
     * @param runningProcess the process whose output is to be read.
     * @return the output of the process as a string.
     */
    public String readProcessOutput(Process runningProcess){
        try (InputStream is = runningProcess.getInputStream()){
            byte[] processBytes = is.readAllBytes();
            return new String(processBytes);
        } catch (IOException ioException) {
            System.err.println("IOException occurred while trying to read output from running process: "
                + runningProcess.pid()
                + ". Reason: "
                + ioException.getMessage()
            );
        }
        return null;
    }
}
