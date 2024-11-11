package assignmentevaluator;


import assignmentevaluator.evaluationHelpers.ClassTestExecutor;
import assignmentevaluator.feedback.types.ConcreteTestFeedback;

import assignmenttests.classlevel.factory.AttributeSignatureTestFactory;
import assignmenttests.programlevel.AssignmentCompile;
import assignmenttests.programlevel.AssignmentFilesAllPresent;
import assignmenttests.programlevel.AssignmentRun;
import filehandler.traversal.DirectoryIterator;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static assignmentevaluator.evaluationHelpers.EvalHelpers.*;
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
        String[] studentInfo = assignment.getName().split("_");
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
                    break;
                case "ChatBotSimulation":
                    System.out.println("Running class tests for class " + getFileName(f));
                    break;
            }


        }
    }

    private void handleChatBot(AssignmentFeedBack assignmentFeedBack, File chatBot){
        ClassTestExecutor messageLimit = new ClassTestExecutor();
        messageLimit.setClassTest(AttributeSignatureTestFactory.createClassTest());
        messageLimit.setEvaluatingFile(chatBot);
        messageLimit.setAssignmentFeedBack(assignmentFeedBack);
        messageLimit.setMarks(3);
        messageLimit.setTestSetupDetailMap(createAttributeTestSetupMap(
            "messageLimit",
            "int",
            "true",
            "10",
            "false",
            "true"
        ));

        ClassTestExecutor messageNumber = new ClassTestExecutor();
        messageNumber.setClassTest(AttributeSignatureTestFactory.createClassTest());
        messageNumber.setEvaluatingFile(chatBot);
        messageNumber.setAssignmentFeedBack(assignmentFeedBack);
        messageNumber.setMarks(2);
        messageNumber.setTestSetupDetailMap(createAttributeTestSetupMap(
            "messageNumber",
            "int",
            "false",
            "int_min",
            "false",
            "true"
        ));

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

        //Method Signature Tests

        //Method Logic Tests
    }

    private void handleChatBotPlatform(AssignmentFeedBack assignmentFeedBack, File chatBotPlatform){
        ClassTestExecutor bots = new ClassTestExecutor();
        bots.setClassTest(AttributeSignatureTestFactory.createClassTest());
        bots.setEvaluatingFile(chatBotPlatform);
        bots.setAssignmentFeedBack(assignmentFeedBack);
        bots.setMarks(2);
        bots.setTestSetupDetailMap(createAttributeTestSetupMap(
            "bots",
            "ArrayList<ChatBot>",
            "false",
            "int_min",
            "false",
            "false"
        ));
    }


}
