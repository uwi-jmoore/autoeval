package assignmentevaluator;

import assignmentevaluator.feedback.types.ConcreteTestFeedback;
import assignmenttests.programlevel.AssignmentCompile;
import assignmenttests.programlevel.AssignmentFilesAllPresent;
import assignmenttests.programlevel.AssignmentRun;
import filehandler.traversal.DirectoryIterator;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import static filehandler.filehelperservice.FileOperationHelpers.*;


//facade for evaluating all assignments
public class AssignmentEvaluator {
    private File studentAssignmentDirectory;

    public void setStudentAssignmentDirectory(File studentAssignmentDirectory) {
        this.studentAssignmentDirectory = studentAssignmentDirectory;
    }
    public void evaluateAssignments() throws IOException {
        DirectoryIterator assignmentIterator = createAssignmentIterator(studentAssignmentDirectory.getAbsolutePath());

        while(assignmentIterator.hasNext()){
            AssignmentFeedBack assignmentFeedBack = new AssignmentFeedBack();
            addAssignmentExpectedFiles(assignmentFeedBack);
            File studentAssignment = Objects.requireNonNull(assignmentIterator.next().listFiles())[0];


            File[] actualAssignmentFiles = studentAssignment.listFiles();
            if(actualAssignmentFiles != null){
                loadStudentInfo(assignmentFeedBack,studentAssignment.getParentFile());

                if(!checkAssignmentFiles(assignmentFeedBack,studentAssignment)){
                    assignmentCompileTest(assignmentFeedBack,studentAssignment);
                    assignmentRunTest(assignmentFeedBack,studentAssignment);
                }else{
                    System.out.println("Files Missing In Assignment, cannot continue run");
                }



                Arrays.stream(actualAssignmentFiles)
                    .toList()
                    .forEach(System.out::println);
            }

        }
    }
    private void addAssignmentExpectedFiles(AssignmentFeedBack assignmentFeedBack){
        assignmentFeedBack.addFileToExpected("ChatBot");
        assignmentFeedBack.addFileToExpected("ChatBotGenerator");
        assignmentFeedBack.addFileToExpected("ChatBotPlatform");
        assignmentFeedBack.addFileToExpected("ChatBotSimulation");
    }

    private boolean checkAssignmentFiles(AssignmentFeedBack assignmentFeedBack, File studentAssignmentDirectory){
        AssignmentFilesAllPresent presentCheck = new AssignmentFilesAllPresent();
        presentCheck.setAssignmentDirectory(studentAssignmentDirectory);
        int marks = 0;
        presentCheck.setMarks(marks);
        presentCheck.setAssignmentFeedBack(assignmentFeedBack);

        presentCheck.evaluateProgramLevelTest();

        ConcreteTestFeedback testFeedback = new ConcreteTestFeedback();
        testFeedback.setMarks(marks);
        testFeedback.setTestType("All Files Present Test");

        boolean filesMissing = presentCheck.getMissingFiles().isEmpty();
        testFeedback.setFeedbackMsg(filesMissing? ("Assignment Files missing: "+ presentCheck.getMissingFiles() ) : "All Files Present");
        assignmentFeedBack.addTestResults(testFeedback);
        return filesMissing;
    }

    private void loadStudentInfo(AssignmentFeedBack assignmentFeedBack, File assignment){
        String[] studentInfo = assignment.getName().split("[_]");
        assignmentFeedBack.setStudentID(studentInfo[2]);
        assignmentFeedBack.setStudentName(studentInfo[0] + " " + studentInfo[1]);
        assignmentFeedBack.setAssignmentTitle(studentInfo[3]);
    }

    private void assignmentCompileTest(AssignmentFeedBack assignmentFeedBack, File studentAssignmentDirectory){
        AssignmentCompile compileTest = new AssignmentCompile();
        int marks = 5;
        compileTest.setMarks(marks);
        compileTest.setAssignmentDirectory(studentAssignmentDirectory);

        int testResult = compileTest.getMarks();

        ConcreteTestFeedback testFeedback = new ConcreteTestFeedback();
        testFeedback.setMarks(testResult);
        testFeedback.setTestType("Compile Test");
        testFeedback.setFeedbackMsg(testResult == marks? "Assignment Compiles": "Assignment failed to compile");
        assignmentFeedBack.addTestResults(testFeedback);
    }

    private void assignmentRunTest(AssignmentFeedBack assignmentFeedBack, File studentAssignmentDirectory){
        AssignmentRun runtTest = new AssignmentRun();
        ConcreteTestFeedback testFeedback = new ConcreteTestFeedback();
        int marks = 10;
        testFeedback.setTestType("Run Test");
        File mainClassFile = getMainClass(studentAssignmentDirectory);

        if(mainClassFile==null){
            marks = 0;
            testFeedback.setMarks(marks);
            testFeedback.setFeedbackMsg("Main file not present in assignment, cannot run assignment");
            return;
        }

        runtTest.setMarks(marks);

        runtTest.setAssignmentDirectory(studentAssignmentDirectory);
        runtTest.setMainFile(mainClassFile);

        int testResult = runtTest.getMarks();

        testFeedback.setMarks(testResult);

        testFeedback.setFeedbackMsg(testResult == marks? "Assignment Runs successfully, Exit Code 0 Returned": ("Assignment failed to run, exitcode: "+ runtTest.getAssignmentExitCode()));
        assignmentFeedBack.addTestResults(testFeedback);
    }

    private File getMainClass(File studentAssignmentDirectory){
        File[] assignmentJavaFiles = getDirectoryFilesOfExt(studentAssignmentDirectory,".java");
        for(File f: assignmentJavaFiles){
            if(Objects.equals(getFileName(f), "ChatBotSimulation")){
                return f;
            }
        }
        return null;
    }


}
