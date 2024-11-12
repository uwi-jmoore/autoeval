package assignmentevaluator;


import java.io.File;
import java.util.Objects;

import assignmentevaluator.evaluationHelpers.ClassTestExecutor;
import static assignmentevaluator.evaluationHelpers.EvalHelpers.createAttributeTestSetupMap;
import static assignmentevaluator.evaluationHelpers.EvalHelpers.getAssignmentFiles;
import static assignmentevaluator.evaluationHelpers.EvalHelpers.getFile;
import assignmentevaluator.feedback.types.ConcreteTestFeedback;
import assignmenttests.classlevel.factory.AttributeSignatureTestFactory;
import assignmenttests.programlevel.AssignmentCompile;
import assignmenttests.programlevel.AssignmentFilesAllPresent;
import assignmenttests.programlevel.AssignmentRun;
import static filehandler.filehelperservice.FileOperationHelpers.createAssignmentIterator;
import static filehandler.filehelperservice.FileOperationHelpers.getFileName;
import filehandler.traversal.DirectoryIterator;

/**
 * Facade class for evaluating student assignments.
 * This class handles the evaluation process, which includes checking if the required files are present,
 * running program-level tests, and performing class-level tests for each student’s assignment.
 */


//facade for evaluating all assignments
public class AssignmentEvaluator {
    private File studentAssignmentDirectory;

    /**
     * Sets the directory containing student assignments.
     * @param studentAssignmentDirectory the directory containing the student assignments
     */

    public void setStudentAssignmentDirectory(File studentAssignmentDirectory) {
        this.studentAssignmentDirectory = studentAssignmentDirectory;
    }

     /**
     * Evaluates all assignments in the specified student assignment directory.
     * This method iterates through each assignment, loads student information,
     * and runs the necessary tests for each student's assignment.
     * @throws IOException if an I/O error occurs while reading the files
     */

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
                    //program level tests
                    assignmentCompileTest(assignmentFeedBack,studentAssignment);

                    assignmentRunTest(assignmentFeedBack,studentAssignment);

                    //class level tests
                    assignmentClassTestActual(assignmentFeedBack,studentAssignment);
                }else{
                    System.out.println("Files Missing In Assignment, cannot continue run");
                }

                System.out.println("Test Results for student: "+assignmentFeedBack.getStudentName()+" : "+assignmentFeedBack.getTestResults());




            }

        }
    }


    /**
     * Adds the expected files for the assignment to the feedback object.
     * These expected files must be present in the student's assignment.
     * @param assignmentFeedBack the feedback object for the assignment
     */

    private void addAssignmentExpectedFiles(AssignmentFeedBack assignmentFeedBack){
        assignmentFeedBack.addFileToExpected("ChatBot");
        assignmentFeedBack.addFileToExpected("ChatBotGenerator");
        assignmentFeedBack.addFileToExpected("ChatBotPlatform");
        assignmentFeedBack.addFileToExpected("ChatBotSimulation");
    }

    /**
     * Checks if all the required files are present in the student's assignment.
     * If any files are missing, it returns true, and no further tests are run.
     * @param assignmentFeedBack the feedback object for the assignment
     * @param studentAssignmentDirectory the directory containing the student's assignment
     * @return true if files are missing, false otherwise
     */


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

     /**
     * Loads the student information (ID, name, and assignment title) from the assignment directory name.
     * @param assignmentFeedBack the feedback object for the assignment
     * @param assignment the student's assignment directory
     */


    private void loadStudentInfo(AssignmentFeedBack assignmentFeedBack, File assignment){
        String[] studentInfo = assignment.getName().split("_");
        assignmentFeedBack.setStudentID(studentInfo[2]);
        assignmentFeedBack.setStudentName(studentInfo[0] + " " + studentInfo[1]);
        assignmentFeedBack.setAssignmentTitle(studentInfo[3]);
    }


    /**
     * Runs the compile test on the student's assignment.
     * Checks whether the student's assignment compiles successfully.
     * @param assignmentFeedBack the feedback object for the assignment
     * @param studentAssignmentDirectory the directory containing the student's assignment
     */


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


    /**
     * Runs the run test on the student's assignment.
     * Checks whether the student's assignment runs successfully and returns an exit code of 0.
     * @param assignmentFeedBack the feedback object for the assignment
     * @param studentAssignmentDirectory the directory containing the student's assignment
     */


    private void assignmentRunTest(AssignmentFeedBack assignmentFeedBack, File studentAssignmentDirectory){
        AssignmentRun runtTest = new AssignmentRun();
        ConcreteTestFeedback testFeedback = new ConcreteTestFeedback();
        int marks = 10;
        testFeedback.setTestType("Run Test");
        File mainClassFile = getFile(studentAssignmentDirectory,"ChatBotSimulation",".java");

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

    /**
     * Runs the class-level tests for each class in the student's assignment.
     * Tests include checking the attributes and methods of the assignment classes.
     * @param assignmentFeedBack the feedback object for the assignment
     * @param studentAssignmentDirectory the directory containing the student's assignment
     */


    private void assignmentClassTestActual(AssignmentFeedBack assignmentFeedBack, File studentAssignmentDirectory){
        //get list of all classes
        File[] assignmentClasses = getAssignmentFiles(studentAssignmentDirectory,".class");
        System.out.println("Running Class tests for student: "+ assignmentFeedBack.getStudentName());
        for(File f: assignmentClasses){
            String filename = getFileName(f);
            switch (filename){
                case "ChatBot":
                    System.out.println("Running class tests for class " + getFileName(f));
                    handleChatBot(assignmentFeedBack,f);
                    break;
                case "ChatBotPlatform":
                    System.out.println("Running class tests for class " + getFileName(f));
                    handleChatBotPlatform(assignmentFeedBack,f);
                    break;
                case "ChatBotGenerator":
                    System.out.println("Running class tests for class " + getFileName(f));
                    //handleChatBotGenerator(assignmentFeedBack,f);
                    break;
                case "ChatBotSimulation":
                    System.out.println("Running class tests for class " + getFileName(f));
                    //handleChatBotSimulation(assignmentFeedBack,f);
                    break;
            }


        }
    }


    /**
     * Handles the tests for the ChatBot class in the student's assignment.
     * @param assignmentFeedBack the feedback object for the assignment
     * @param chatBot the ChatBot class file to be tested
     */

    private void handleChatBot(AssignmentFeedBack assignmentFeedBack, File chatBot){



        //chatBotName
        ClassTestExecutor chatBotSig = new ClassTestExecutor();
        chatBotSig.setClassTest(AttributeSignatureTestFactory.createClassTest());
        chatBotSig.setEvaluatingFile(chatBot);
        chatBotSig.setAssignmentFeedBack(assignmentFeedBack);
        chatBotSig.setMarks(1);
        chatBotSig.setTestSetupDetailMap(createAttributeTestSetupMap(
            "chatBotName",
            "String",
            "false",
            "int_min",
            "false",
            "false"
        ));
        chatBotSig.execute();

        //numResponsesGenerated
        ClassTestExecutor numResponsesGeneratedSig = new ClassTestExecutor();
        numResponsesGeneratedSig.setClassTest(AttributeSignatureTestFactory.createClassTest());
        numResponsesGeneratedSig.setEvaluatingFile(chatBot);
        numResponsesGeneratedSig.setAssignmentFeedBack(assignmentFeedBack);
        numResponsesGeneratedSig.setMarks(1);
        numResponsesGeneratedSig.setTestSetupDetailMap(createAttributeTestSetupMap(
            "numResponsesGenerated",
            "int",
            "false",
            "int_min",
            "false",
            "false"
        ));
        numResponsesGeneratedSig.execute();

        //
        ClassTestExecutor messageLimitSig = new ClassTestExecutor();
        messageLimitSig.setClassTest(AttributeSignatureTestFactory.createClassTest());
        messageLimitSig.setEvaluatingFile(chatBot);
        messageLimitSig.setAssignmentFeedBack(assignmentFeedBack);
        messageLimitSig.setMarks(3);
        messageLimitSig.setTestSetupDetailMap(createAttributeTestSetupMap(
            "messageLimit",
            "int",
            "true",
            "10",
            "false",
            "true"
        ));
        messageLimitSig.execute();


        ClassTestExecutor messageNumberSig = new ClassTestExecutor();
        messageNumberSig.setClassTest(AttributeSignatureTestFactory.createClassTest());
        messageNumberSig.setEvaluatingFile(chatBot);
        messageNumberSig.setAssignmentFeedBack(assignmentFeedBack);
        messageNumberSig.setMarks(2);
        messageNumberSig.setTestSetupDetailMap(createAttributeTestSetupMap(
            "messageNumber",
            "int",
            "false",
            "int_min",
            "false",
            "true"
        ));
        messageNumberSig.execute();
        //Method Signature Tests

        //Method Logic Tests
    }

    /**
     * Handles the tests for the ChatBotPlatform class in the student's assignment.
     * @param assignmentFeedBack the feedback object for the assignment
     * @param chatBotPlatform the ChatBotPlatform class file to be tested
     */

    private void handleChatBotPlatform(AssignmentFeedBack assignmentFeedBack, File chatBotPlatform){
        ClassTestExecutor botsSig = new ClassTestExecutor();
        botsSig.setClassTest(AttributeSignatureTestFactory.createClassTest());
        botsSig.setEvaluatingFile(chatBotPlatform);
        botsSig.setAssignmentFeedBack(assignmentFeedBack);
        botsSig.setMarks(2);
        botsSig.setTestSetupDetailMap(createAttributeTestSetupMap(
            "bots",
            "ArrayList<ChatBot>",
            "false",
            "int_min",
            "false",
            "false"
        ));
        botsSig.execute();
    }

    private void handleChatBotGenerator(AssignmentFeedBack assignmentFeedBack, File chatBotGenerator){
        // method signature tests
    }

    private void handleChatBotSimulation(AssignmentFeedBack assignmentFeedBack, File ChatBotSimulation){
        // method signature tests
    }

}
