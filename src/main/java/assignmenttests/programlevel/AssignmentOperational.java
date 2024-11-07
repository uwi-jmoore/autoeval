package assignmenttests.programlevel;

import java.io.File;

public abstract class AssignmentOperational implements ProgramTest{
    private File mainFile;
    private File assignmentDirectory;
    private int marks;

    @Override
    public abstract boolean evaluateProgramLevelTest();

    @Override
    public int getMarks() {
        return evaluateProgramLevelTest()? marks : 0;
    }

    public void setMainFile(File mainFile) {
        this.mainFile = mainFile;
    }

    public File getAssignmentDirectory() {
        return assignmentDirectory;
    }

    public void setAssignmentDirectory(File assignmentDirectory){
        this.assignmentDirectory = assignmentDirectory;
    }
    public File getMainFile() {
        return mainFile;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }
}
