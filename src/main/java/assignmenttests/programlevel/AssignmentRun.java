package assignmenttests.programlevel;

import java.io.*;

import static assignmenttests.programlevel.ProgramLevelTestHelpers.compileAssignment;

public class AssignmentRun extends AssignmentOperational{

    private int assignmentExitCode;

    @Override
    public boolean evaluateProgramLevelTest() {
        if (compileAssignment(getAssignmentDirectory())){
            String directory = getAssignmentDirectory().getAbsolutePath();
            String className = getMainFile().getName().replace(".java", "");
            ProcessBuilder processBuilder = new ProcessBuilder(
                "java",
                "-cp",
                directory,
                className
            );
            try{
                Process process = processBuilder.start();
                runInputs(process);
                int exitCode = process.waitFor();
                assignmentExitCode = exitCode;
                return exitCode == 0;
            }
            catch (IOException ioException){
                System.err.println("Issue in starting thread for Assignment, IOException occurred: "
                    + ioException.getMessage());
                ioException.printStackTrace();
            }catch (InterruptedException interruptedException){
                System.err.println("Thread running Assignment interrupted, InterruptedException occurred: "
                    + interruptedException.getMessage());
                interruptedException.printStackTrace();
            }
        }
        return false;
    }
    private void runInputs(Process runningProcess){
        try (OutputStream os = runningProcess.getOutputStream()) {
            Thread.sleep(100);//100 milliseconds seems to be the magic number to get this work, DO NOT CHANGE
            for(int i = 0; i<15;i++){
                os.write(("test" + i +"\n").getBytes());
                os.flush();
                Thread.sleep(100);//100 milliseconds seems to be the magic number to get this work, DO NOT CHANGE
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public int getAssignmentExitCode(){
        return assignmentExitCode;
    }

}
