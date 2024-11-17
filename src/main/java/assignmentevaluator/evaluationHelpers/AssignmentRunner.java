package assignmentevaluator.evaluationHelpers;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static filehandler.filehelperservice.FileOperationHelpers.getDirectoryFilesOfExt;

public class AssignmentRunner {
    protected File assignmentDirectory;
    protected File mainMethodFile;
    protected List<Object> testInputs;
    protected int assignmentExitCode;
    public AssignmentRunner(){
        testInputs = new ArrayList<>();
    }

    public void setRunnerAssignmentDirectory(File assignmentDirectory) {
        this.assignmentDirectory = assignmentDirectory;
    }

    public void setRunnerMainMethodFile(File mainMethodFile){
        this.mainMethodFile = mainMethodFile;
    }
    public void addTestInput(Object input){
        testInputs.add(input);
    }
    public void addTestInputList(List<Object> inputList){
        testInputs.addAll(inputList);
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
                runInputList(process);
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

    /**
     * Sends test inputs to the running process simulating user input.
     *
     * @param runningProcess the process to which inputs are sent
     */
    protected void runInputList(Process runningProcess) {
        try{
            Thread.sleep(100); // 100 milliseconds delay for process readiness
            for(Object testInput: testInputs){
                runInputCustom(runningProcess,testInput);
                Thread.sleep(100); // Delay for each input to be processed
            }
//            for (int i = 0; i < 15; i++) {
//                os.write(("test" + i + "\n").getBytes());
//                os.flush();
//                Thread.sleep(100); // Delay for each input to be processed
//            }
        }catch (InterruptedException interruptedException){
            System.err.println("InterruptedException occurred. Reason: "+ interruptedException.getMessage());
        }
    }

    public void runInputCustom(Process runningProcess, Object customIn){
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
