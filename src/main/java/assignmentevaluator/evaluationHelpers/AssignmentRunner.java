package assignmentevaluator.evaluationHelpers;

import assignmentevaluator.classloader.AssignmentClassLoader;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static filehandler.filehelperservice.FileOperationHelpers.getDirectoryFilesOfExt;

public class AssignmentRunner {
    protected static File assignmentDirectory;
    protected static File mainMethodFile;
    protected static List<Object> testInputs;
    protected static int assignmentExitCode;

    protected static Field[] mainClassFields;

    protected static String runnerOutputStream;
//    public AssignmentRunner(){
//        AssignmentRunner.testInputs = new ArrayList<>();
//    }

    public void setRunnerAssignmentDirectory(File assignmentDirectory) {
        AssignmentRunner.assignmentDirectory = assignmentDirectory;
    }

    public void setRunnerMainMethodFile(File mainMethodFile){
        AssignmentRunner.mainMethodFile = mainMethodFile;
    }

    public void addTestInputList(List<Object> inputList){
        AssignmentRunner.testInputs = inputList;
    }

    public Field[] getMainClassFields(){
        return mainClassFields;
    }

    public String getRunnerOutputStream() {
        return runnerOutputStream;
    }

    public int getAssignmentExitCode() {
        return assignmentExitCode;
    }

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

                if(testInputs==null || testInputs.isEmpty()){
                    runStandardInput(process);
                }else{
                    runInputList(process);
                }
                int exitCode = process.waitFor();
                outputThread.join();
                assignmentExitCode = exitCode;
                runnerOutputStream = outputStream.toString();
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
    /**
     * Captures the output from an InputStream (either standard or error stream) into a ByteArrayOutputStream.
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

    protected void runStandardInput(Process process){
        try{
            try (OutputStream os = process.getOutputStream()){
                for(int i = 0; i<15; i++){
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
        }catch (InterruptedException interruptedException){
            System.err.println("InterruptedException occurred. Reason: "+ interruptedException.getMessage());
        }
    }

    /**
     * Sends test inputs to the running process simulating user input.
     *
     * @param runningProcess the process to which inputs are sent
     */
    public void runInputList(Process runningProcess) {
        try{
            try (OutputStream os = runningProcess.getOutputStream()){
                for(Object testInput: testInputs){
                    Thread.sleep(100);
                    os.write((testInput.toString()+"\n").getBytes());
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
        }catch (InterruptedException interruptedException){
            System.err.println("InterruptedException occurred. Reason: "+ interruptedException.getMessage());
        }
    }

    public void runInputCustom(Process runningProcess, Object customIn){
        if (!runningProcess.isAlive()) {

            System.err.println("Process is no longer alive.");

            return;

        }
        try (OutputStream os = runningProcess.getOutputStream()){
            os.write((customIn.toString()+"\n").getBytes());
            os.flush();
        } catch (IOException ioException) {
            System.err.println("IOException occurred while trying to write input to running process: "
                + runningProcess.pid()
                + ". Reason: "
                + ioException.getMessage()
            );
        }
    }

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
