package assignmentevaluator;


import java.io.File;
import java.io.IOException;
import java.util.*;

import assignmentevaluator.evaluationHelpers.CustomCase;
import assignmentevaluator.evaluationHelpers.executors.classlevel.MultiCaseClassTestExecutor;
import assignmentevaluator.evaluationHelpers.executors.classlevel.SingleCaseClassTestExecutor;

import assignmentevaluator.evaluationHelpers.executors.programlevel.CompileTestExecutor;
import assignmentevaluator.evaluationHelpers.executors.programlevel.RunTestExecutor;
import assignmentevaluator.evaluationHelpers.testsetup.classlevel.AttributeTestSetup;
import assignmentevaluator.evaluationHelpers.testsetup.classlevel.ConstructorTestSetup;
import assignmentevaluator.evaluationHelpers.testsetup.classlevel.ModifierMethodTestSetup;
import assignmentevaluator.evaluationHelpers.testsetup.programlevel.CompileTestSetup;
import assignmentevaluator.evaluationHelpers.testsetup.programlevel.RunTestSetup;
import assignmentevaluator.feedback.types.ConcreteTestFeedback;
import assignmenttests.classlevel.ClassTest;
import assignmenttests.classlevel.factory.programlevel.CompileTestFactory;
import assignmenttests.classlevel.factory.programlevel.RunTestFactory;
import assignmenttests.classlevel.products.attribute.supports.AttributeDataType;
import assignmenttests.classlevel.products.attribute.supports.AttributeDefaultValue;
import assignmenttests.classlevel.products.method.supports.*;
import assignmenttests.classlevel.factory.classlevel.AttributeSignatureTestFactory;
import assignmenttests.classlevel.factory.classlevel.MethodTestFactory;
import assignmenttests.programlevel.AssignmentFilesAllPresent;

import static assignmentevaluator.evaluationHelpers.EvalHelpers.*;
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


    private List<ClassTestAttributeExpectedValue> modifiedClassAttributes;
    private List<ClassTestAttributeTestValue> testModifiedClassAttributes;
    private List<ClassTestParameter> methodInputParameters;
    private ModifierMethodTestSetup modifierMethodTestSetup;
    private AttributeTestSetup attributeTestSetup;
    private AttributeDefaultValue attributeDefaultValue;
    public AssignmentEvaluator(){
        modifierMethodTestSetup = new ModifierMethodTestSetup();
        attributeTestSetup = new AttributeTestSetup();
        attributeDefaultValue = new AttributeDefaultValue();
    }
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
//                    assignmentCompileTest(assignmentFeedBack,studentAssignment);
//
//                    assignmentRunTest(assignmentFeedBack,studentAssignment);

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
        CompileTestSetup programTestSetup;
        CompileTestExecutor compileTestExecutor = new CompileTestExecutor();
        compileTestExecutor.setMarks(5);
        compileTestExecutor.setTest(CompileTestFactory.createTest());
        compileTestExecutor.setAssignmentFeedBack(assignmentFeedBack);
        programTestSetup = new CompileTestSetup();
        programTestSetup.addAssignmentDirectory(studentAssignmentDirectory);
        compileTestExecutor.setTestSetupDetailMap(programTestSetup.getMap());
        compileTestExecutor.executeTest();
    }


    /**
     * Runs the run test on the student's assignment.
     * Checks whether the student's assignment runs successfully and returns an exit code of 0.
     * @param assignmentFeedBack the feedback object for the assignment
     * @param studentAssignmentDirectory the directory containing the student's assignment
     */


    private void assignmentRunTest(AssignmentFeedBack assignmentFeedBack, File studentAssignmentDirectory){
        RunTestSetup programTestSetup;
        RunTestExecutor runTestExecutor = new RunTestExecutor();
        runTestExecutor.setTest(RunTestFactory.createTest());
        runTestExecutor.setMarks(10);
        runTestExecutor.setAssignmentFeedBack(assignmentFeedBack);
        programTestSetup = new RunTestSetup();
        programTestSetup.addAssignmentDirectory(studentAssignmentDirectory);
        programTestSetup.addMainFile(getFile(studentAssignmentDirectory,"ChatBotSimulation",".java"));

        runTestExecutor.setTestSetupDetailMap(programTestSetup.getMap());
        runTestExecutor.executeTest();
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
//                    ChatBotAttributes(assignmentFeedBack,f);
//                    chatBotMethods(assignmentFeedBack,f);
//                    handleChatBotRetrievalMethods(assignmentFeedBack,f);
                    break;
                case "ChatBotPlatform":
                    System.out.println("Running class tests for class " + getFileName(f));
//                    chatBotPlatformAttributes(assignmentFeedBack,f);
//                    chatBotPlatformMethods(assignmentFeedBack,f);
                    handleChatBotPlatformRetrievalMethods(assignmentFeedBack,f);
                    break;
                case "ChatBotGenerator":
                    System.out.println("Running class tests for class " + getFileName(f));
//                    chatBotGeneratorMethods(assignmentFeedBack,f);
                    break;
                case "ChatBotSimulation":
                    System.out.println("Running class tests for class " + getFileName(f));
//                    chatBotSimulation(assignmentFeedBack,f);
                    break;
            }
        }
    }

    /**
     * Handles the attribute tests for the ChatBot class in the student's assignment.
     * @param assignmentFeedBack the feedback object for the assignment
     * @param chatBot the ChatBot class file to be tested
     */

    private void ChatBotAttributes(AssignmentFeedBack assignmentFeedBack, File chatBot){
        //chatBotName
        handleAttrChatBotName(assignmentFeedBack, chatBot);

        //numResponsesGenerated
        handleAttrNumResponsesGenerated(assignmentFeedBack, chatBot);

        //messageLimit
        handleAttrMessageLimit(assignmentFeedBack, chatBot);

        //messageNumber
        handleAttrMessageNumber(assignmentFeedBack, chatBot);

    }
    private void handleAttrChatBotName(AssignmentFeedBack assignmentFeedBack, File chatBot) {
        SingleCaseClassTestExecutor chatBotSig = new SingleCaseClassTestExecutor();
        chatBotSig.setClassTest((ClassTest) AttributeSignatureTestFactory.createTest());
        chatBotSig.setEvaluatingFile(chatBot);
        chatBotSig.setAssignmentFeedBack(assignmentFeedBack);
        chatBotSig.setMarks(1);
        attributeTestSetup.addAttributeName("chatBotName");
        attributeTestSetup.addDataType(new AttributeDataType(
            String.class,
            null
        ));
        attributeTestSetup.addDefault(null);
        attributeTestSetup.addIsFinal(false);
        attributeTestSetup.addIsStatic(false);

        chatBotSig.setTestSetupDetailMap(attributeTestSetup.getMap());
        chatBotSig.executeTest();
    }
    private void handleAttrNumResponsesGenerated(AssignmentFeedBack assignmentFeedBack, File chatBot) {
        SingleCaseClassTestExecutor numResponsesGeneratedSig = new SingleCaseClassTestExecutor();
        numResponsesGeneratedSig.setClassTest((ClassTest) AttributeSignatureTestFactory.createTest());
        numResponsesGeneratedSig.setEvaluatingFile(chatBot);
        numResponsesGeneratedSig.setAssignmentFeedBack(assignmentFeedBack);
        numResponsesGeneratedSig.setMarks(1);
        attributeTestSetup.addAttributeName("numResponsesGenerated");
        attributeTestSetup.addDataType(new AttributeDataType(
            int.class,
            null
        ));
        attributeTestSetup.addDefault(null);
        attributeTestSetup.addIsFinal(false);
        attributeTestSetup.addIsStatic(false);

        numResponsesGeneratedSig.setTestSetupDetailMap(attributeTestSetup.getMap());
        numResponsesGeneratedSig.executeTest();
    }

    private void handleAttrMessageLimit(AssignmentFeedBack assignmentFeedBack, File chatBot) {
        SingleCaseClassTestExecutor messageLimitSig = new SingleCaseClassTestExecutor();
        messageLimitSig.setClassTest((ClassTest) AttributeSignatureTestFactory.createTest());
        messageLimitSig.setEvaluatingFile(chatBot);
        messageLimitSig.setAssignmentFeedBack(assignmentFeedBack);
        messageLimitSig.setMarks(3);
        attributeTestSetup.addAttributeName("messageLimit");
        attributeTestSetup.addDataType(new AttributeDataType(
            int.class,
            null
        ));
        attributeDefaultValue.setAttrDefaultValue(10);
        attributeDefaultValue.setDefaultValueDataType(int.class);
        attributeTestSetup.addDefault(attributeDefaultValue);
        attributeTestSetup.addIsFinal(false);
        attributeTestSetup.addIsStatic(true);

        messageLimitSig.setTestSetupDetailMap(attributeTestSetup.getMap());
        messageLimitSig.executeTest();
    }

    private void handleAttrMessageNumber(AssignmentFeedBack assignmentFeedBack, File chatBot) {
        SingleCaseClassTestExecutor messageNumberSig = new SingleCaseClassTestExecutor();
        messageNumberSig.setClassTest((ClassTest) AttributeSignatureTestFactory.createTest());
        messageNumberSig.setEvaluatingFile(chatBot);
        messageNumberSig.setAssignmentFeedBack(assignmentFeedBack);
        messageNumberSig.setMarks(2);
        attributeTestSetup.addAttributeName("messageNumber");
        attributeTestSetup.addDataType(new AttributeDataType(
            int.class,
            null
        ));
        attributeTestSetup.addDefault(null);
        attributeTestSetup.addIsFinal(false);
        attributeTestSetup.addIsStatic(true);
        messageNumberSig.setTestSetupDetailMap(attributeTestSetup.getMap());
        messageNumberSig.executeTest();
    }

    /**
     * Handles the method tests for the ChatBot class in the student's assignment.
     * @param assignmentFeedBack the feedback object for the assignment
     * @param chatBot the ChatBot class file to be tested
     */
    private void chatBotMethods(AssignmentFeedBack assignmentFeedBack, File chatBot){
        handleChatBotConstructor(assignmentFeedBack, chatBot);
        handleGenerateResponse(assignmentFeedBack, chatBot);
        handleMethodPrompt(assignmentFeedBack,chatBot);
    }


    private void handleChatBotConstructor(AssignmentFeedBack assignmentFeedBack, File chatBot) {
        SingleCaseClassTestExecutor chatBotConstructor = new SingleCaseClassTestExecutor();
        chatBotConstructor.setClassTest((ClassTest) MethodTestFactory.createTest("constructor"));
        chatBotConstructor.setEvaluatingFile(chatBot);
        chatBotConstructor.setAssignmentFeedBack(assignmentFeedBack);
        chatBotConstructor.setMarks(3);

        //Requires 2 sets of modified ClassAttribute checks, 1 per constructor
        modifiedClassAttributes = List.of(
            new ClassTestAttributeExpectedValue(
                "chatBotName",
                String.class,
                "ChatGPT-3.5"
            ),
            new ClassTestAttributeExpectedValue(
                "numResponsesGenerated",
                int.class,
                0
            ),
            new ClassTestAttributeExpectedValue(
                "chatBotName",
                String.class,
                "Bard"
            ),
            new ClassTestAttributeExpectedValue(
                "numResponsesGenerated",
                int.class,
                0
            )

        );
        methodInputParameters = List.of(
            new ClassTestParameter(
                int.class,
                3
            )
        );

        chatBotConstructor.setTestSetupDetailMap(
            new ConstructorTestSetup(
                methodInputParameters,
                modifiedClassAttributes
            ).getMap()
        );
        chatBotConstructor.executeTest();
    }

    private void handleGenerateResponse(AssignmentFeedBack assignmentFeedBack, File chatBot) {
        SingleCaseClassTestExecutor generateResponse = new SingleCaseClassTestExecutor();
        generateResponse.setClassTest((ClassTest) MethodTestFactory.createTest("modifier"));
        generateResponse.setEvaluatingFile(chatBot);
        generateResponse.setAssignmentFeedBack(assignmentFeedBack);
        generateResponse.setMarks(5);
        methodInputParameters = null;
        modifiedClassAttributes = List.of(
            new ClassTestAttributeExpectedValue(
                "numResponsesGenerated",
                int.class,
                1
            )
        );
        testModifiedClassAttributes = null;

        modifierMethodTestSetup.addMethodName("generateResponse");
        modifierMethodTestSetup.addMethodReturn(new MethodReturn(true,String.class,"1@ChatGPT-3.5"));
        modifierMethodTestSetup.addIsStatic(false);
        modifierMethodTestSetup.addTestModAttrs(testModifiedClassAttributes);
        modifierMethodTestSetup.addMethodInputParams(methodInputParameters);
        modifierMethodTestSetup.addMethodExpectedAttrChanges(modifiedClassAttributes);

        generateResponse.setTestSetupDetailMap(
            modifierMethodTestSetup.getMap()
        );


        generateResponse.executeTest();
    }

    private void handleMethodPrompt(AssignmentFeedBack assignmentFeedBack, File chatBot){
        MultiCaseClassTestExecutor promptTest = new MultiCaseClassTestExecutor();
        promptTest.setClassTest((ClassTest) MethodTestFactory.createTest("modifier"));
        promptTest.setEvaluatingFile(chatBot);
        promptTest.setAssignmentFeedBack(assignmentFeedBack);
        promptTest.setMarks(4);
        //stays the same
        methodInputParameters = List.of(
            new ClassTestParameter(
                String.class,
                "testmsg"
            )
        );
        modifiedClassAttributes = List.of(
            new ClassTestAttributeExpectedValue(
                "messageNumber",
                int.class,
                1
            )
        );
        CustomCase customCase1 = getCustomPromptTestCase(0, "1@ChatGPT-3.5");
        promptTest.addTestCase(customCase1);

        CustomCase customCase2 = getCustomPromptTestCase(10, "Daily Limit Reached. Wait 24 hours to resume chatbot usage");
        promptTest.addTestCase(customCase2);
        modifierMethodTestSetup.addMethodName("prompt");
        modifierMethodTestSetup.addMethodReturn(null);
        modifierMethodTestSetup.addIsStatic(false);
        modifierMethodTestSetup.addTestModAttrs(null);
        modifierMethodTestSetup.addMethodInputParams(null);
        modifierMethodTestSetup.addMethodExpectedAttrChanges(null);

        promptTest.setTestSetupDetailMap(modifierMethodTestSetup.getMap());

        promptTest.executeTest();
    }

    private CustomCase getCustomPromptTestCase(int testSetValue, String returnValue) {
        CustomCase customCase2 = new CustomCase();
        customCase2.setCustomTestAttributeValue(List.of(
            new ClassTestAttributeTestValue(
                "messageNumber",
                int.class,
                testSetValue
            )
        ));
        customCase2.setCustomReturn(new MethodReturn(true, String.class, returnValue));
        customCase2.setCustomExpectedAttributeValue(modifiedClassAttributes);
        customCase2.setCustomParams(methodInputParameters);
        return customCase2;
    }

    /**
     * Handles the attribute tests for the ChatBotPlatform class in the student's assignment.
     * @param assignmentFeedBack the feedback object for the assignment
     * @param chatBotPlatform the ChatBotPlatform class file to be tested
     */

    private void chatBotPlatformAttributes(AssignmentFeedBack assignmentFeedBack, File chatBotPlatform){
        SingleCaseClassTestExecutor botsSig = new SingleCaseClassTestExecutor();
        botsSig.setClassTest((ClassTest) AttributeSignatureTestFactory.createTest());
        botsSig.setEvaluatingFile(chatBotPlatform);
        botsSig.setAssignmentFeedBack(assignmentFeedBack);
        botsSig.setMarks(2);
        attributeTestSetup.addAttributeName("bots");
        attributeTestSetup.addDataType(new AttributeDataType(
            ArrayList.class,
            "ChatBot"
        ));
        attributeTestSetup.addDefault(null);
        attributeTestSetup.addIsFinal(false);
        attributeTestSetup.addIsStatic(false);
        botsSig.setTestSetupDetailMap(attributeTestSetup.getMap());
        botsSig.executeTest();
    }

    /**
     * Handles the method tests for the ChatBotPlatform class in the student's assignment.
     * @param assignmentFeedBack the feedback object for the assignment
     * @param chatBotPlatform the ChatBotPlatform class file to be tested
     */
    private void chatBotPlatformMethods(AssignmentFeedBack assignmentFeedBack, File chatBotPlatform){
        handleChatBotPlatformConstructor(assignmentFeedBack,chatBotPlatform);
        handleMethodAddChatBot(assignmentFeedBack,chatBotPlatform);
        handleMethodInteractWithBot(assignmentFeedBack,chatBotPlatform);

    }

    private void handleChatBotPlatformConstructor(AssignmentFeedBack assignmentFeedBack, File chatBotPlatform){
        SingleCaseClassTestExecutor chatBotPlatformConstructor = new SingleCaseClassTestExecutor();
        chatBotPlatformConstructor.setClassTest((ClassTest) MethodTestFactory.createTest("constructor"));
        chatBotPlatformConstructor.setEvaluatingFile(chatBotPlatform);
        chatBotPlatformConstructor.setAssignmentFeedBack(assignmentFeedBack);
        chatBotPlatformConstructor.setMarks(2);

        modifiedClassAttributes = List.of(
            new ClassTestAttributeExpectedValue(
                "bots",
                ArrayList.class,
                null                //if null then an empty Collection is expected
            )
        );
        methodInputParameters = null;
        chatBotPlatformConstructor.setTestSetupDetailMap(new ConstructorTestSetup(
            methodInputParameters,
            modifiedClassAttributes
        ).getMap());
        chatBotPlatformConstructor.executeTest();
    }

    private void handleMethodAddChatBot(AssignmentFeedBack assignmentFeedBack, File chatBotPlatform){
        MultiCaseClassTestExecutor addChatBot = new MultiCaseClassTestExecutor();

        addChatBot.setClassTest((ClassTest) MethodTestFactory.createTest("modifier"));
        addChatBot.setEvaluatingFile(chatBotPlatform);
        addChatBot.setAssignmentFeedBack(assignmentFeedBack);
        addChatBot.setMarks(5);

        modifiedClassAttributes = null;

        List<CustomCase> cases = new ArrayList<>();
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 2; j++){
                CustomCase case1 = getAddChatBotCustomCase(i, j);
                cases.add(case1);
            }
        }
        addChatBot.setTestCases(cases);

        modifierMethodTestSetup.addMethodName("addChatBot");
        modifierMethodTestSetup.addMethodReturn(null);
        modifierMethodTestSetup.addIsStatic(false);
        modifierMethodTestSetup.addTestModAttrs(null);
        modifierMethodTestSetup.addMethodInputParams(null);
        modifierMethodTestSetup.addMethodExpectedAttrChanges(null);
        addChatBot.setTestSetupDetailMap(modifierMethodTestSetup.getMap());

        addChatBot.executeTest();
    }

    private void handleMethodInteractWithBot(AssignmentFeedBack assignmentFeedBack, File chatBotPlatform){
        MultiCaseClassTestExecutor interactWithBot = new MultiCaseClassTestExecutor();
        interactWithBot.setClassTest((ClassTest) MethodTestFactory.createTest("modifier"));
        interactWithBot.setEvaluatingFile(chatBotPlatform);
        interactWithBot.setAssignmentFeedBack(assignmentFeedBack);
        interactWithBot.setMarks(5);

        ClassTestRunMethod preAddChatBot1 = new ClassTestRunMethod();
        preAddChatBot1.setMethodName("addChatBot");
        preAddChatBot1.setClassName("ChatBotPlatform");
        preAddChatBot1.setMethodParams(List.of(new ClassTestParameter(int.class,1)));

        CustomCase customCase1 = new CustomCase();
        customCase1.setCustomParams(List.of(
            new ClassTestParameter(
                int.class,
                0
            ),
            new ClassTestParameter(
                String.class,
                "testmsg"
            )
        ));
        customCase1.setCustomReturn(new MethodReturn(true,String.class,"Message@LLaMa"));
        interactWithBot.addTestCase(customCase1);

        CustomCase customCase2 = new CustomCase();
        customCase2.setCustomParams(List.of(
            new ClassTestParameter(
                int.class,
                7
            ),
            new ClassTestParameter(
                String.class,
                "testmsg"
            )
        ));
        customCase2.setCustomReturn(new MethodReturn(true,String.class,"Incorrect@7"));
        interactWithBot.addTestCase(customCase2);

        modifierMethodTestSetup.addMethodName("interactWithBot");
        modifierMethodTestSetup.addMethodReturn(null);
        modifierMethodTestSetup.addIsStatic(false);
        modifierMethodTestSetup.addTestModAttrs(null);
        modifierMethodTestSetup.addMethodInputParams(null);
        modifierMethodTestSetup.addMethodExpectedAttrChanges(null);
        modifierMethodTestSetup.addPreTestMethods(List.of(preAddChatBot1));
        interactWithBot.setTestSetupDetailMap(modifierMethodTestSetup.getMap());

        interactWithBot.executeTest();

    }

    private static CustomCase getAddChatBotCustomCase(int i, int j) {
        CustomCase case1 = new CustomCase();
        case1.setCustomParams(List.of(
            new ClassTestParameter(
                int.class,
                i
            )
        ));
        case1.setCustomTestAttributeValue(List.of(
            new ClassTestAttributeTestValue(
                "messageNumber",
                int.class,
                j == 0 ? 0 : 10
            )));
        case1.setCustomExpectedAttributeValue(List.of(
            new ClassTestAttributeExpectedValue(
                "messageNumber",
                int.class,
                j == 0 ? 0 : 10
        )));
        case1.setCustomReturn(new MethodReturn(
            true,
            Boolean.class,
            j == 0
        ));
        return case1;
    }

    private void handleChatBotRetrievalMethods(AssignmentFeedBack assignmentFeedBack, File ChatBot){
        getChatBotName(assignmentFeedBack,ChatBot);
        getNumResponses(assignmentFeedBack,ChatBot);
        getTotalNumResponses(assignmentFeedBack,ChatBot);
        getTotalNumRemaining(assignmentFeedBack,ChatBot);
        limitReached(assignmentFeedBack,ChatBot);
        cbtoString(assignmentFeedBack,ChatBot);
    }

    private void getChatBotName(AssignmentFeedBack assignmentFeedBack, File ChatBot){
        SingleCaseClassTestExecutor getChatBotName = new SingleCaseClassTestExecutor();
        getChatBotName.setClassTest((ClassTest) MethodTestFactory.createTest("retriever"));
        getChatBotName.setAssignmentFeedBack(assignmentFeedBack);
        getChatBotName.setEvaluatingFile(ChatBot);
        getChatBotName.setMarks(1);

        ClassTestRunMethod preGetChatBotName = new ClassTestRunMethod();
        preGetChatBotName.setConstructor("ChatBot");
        preGetChatBotName.setMethodParams(List.of(new ClassTestParameter(int.class,2)));

        modifierMethodTestSetup.addMethodName("getChatBotName");
        modifierMethodTestSetup.addMethodReturn(new MethodReturn(true,String.class,"Mistral7B"));
        modifierMethodTestSetup.addIsStatic(false);
        modifierMethodTestSetup.addTestModAttrs(null);
        modifierMethodTestSetup.addMethodInputParams(null);
        modifierMethodTestSetup.addMethodExpectedAttrChanges(null);
        modifierMethodTestSetup.addPreTestMethods(List.of(preGetChatBotName));
        getChatBotName.setTestSetupDetailMap(modifierMethodTestSetup.getMap());
        getChatBotName.executeTest();
    }

    private void getNumResponses(AssignmentFeedBack assignmentFeedBack, File ChatBot){
        SingleCaseClassTestExecutor numResp = new SingleCaseClassTestExecutor();
        numResp.setClassTest((ClassTest) MethodTestFactory.createTest("retriever"));
        numResp.setAssignmentFeedBack(assignmentFeedBack);
        numResp.setEvaluatingFile(ChatBot);
        numResp.setMarks(1);

        String testClass = "ChatBot";


        ClassTestRunMethod genResp1 = new ClassTestRunMethod();
        genResp1.setMethodName("generateResponse");
        genResp1.setClassName(testClass);

        modifierMethodTestSetup.addMethodName("getNumResponsesGenerated");
        modifierMethodTestSetup.addMethodReturn(new MethodReturn(true,int.class,1));
        modifierMethodTestSetup.addIsStatic(false);
        modifierMethodTestSetup.addTestModAttrs(null);
        modifierMethodTestSetup.addMethodInputParams(null);
        modifierMethodTestSetup.addMethodExpectedAttrChanges(null);
        modifierMethodTestSetup.addPreTestMethods(List.of(genResp1));

        numResp.setTestSetupDetailMap(modifierMethodTestSetup.getMap());
        numResp.executeTest();

    }

    private void getTotalNumResponses(AssignmentFeedBack assignmentFeedBack, File ChatBot){
        SingleCaseClassTestExecutor totResp = new SingleCaseClassTestExecutor();
        totResp.setClassTest((ClassTest) MethodTestFactory.createTest("retriever"));
        totResp.setAssignmentFeedBack(assignmentFeedBack);
        totResp.setEvaluatingFile(ChatBot);
        totResp.setMarks(2);

        String testClass = "ChatBot";


        ClassTestRunMethod genResp1 = new ClassTestRunMethod();
        genResp1.setMethodName("generateResponse");
        genResp1.setClassName(testClass);

        modifierMethodTestSetup.addMethodName("getTotalNumResponsesGenerated");
        modifierMethodTestSetup.addMethodReturn(new MethodReturn(true,int.class,1));
        modifierMethodTestSetup.addIsStatic(true);
        modifierMethodTestSetup.addTestModAttrs(null);
        modifierMethodTestSetup.addMethodInputParams(null);
        modifierMethodTestSetup.addMethodExpectedAttrChanges(null);
        modifierMethodTestSetup.addPreTestMethods(List.of(genResp1));

        totResp.setTestSetupDetailMap(modifierMethodTestSetup.getMap());
        totResp.executeTest();

    }

    private void getTotalNumRemaining(AssignmentFeedBack assignmentFeedBack, File ChatBot){
        SingleCaseClassTestExecutor totRem = new SingleCaseClassTestExecutor();
        totRem.setClassTest((ClassTest) MethodTestFactory.createTest("retriever"));
        totRem.setAssignmentFeedBack(assignmentFeedBack);
        totRem.setEvaluatingFile(ChatBot);
        totRem.setMarks(3);

        String testClass = "ChatBot";


        ClassTestRunMethod genResp1 = new ClassTestRunMethod();
        genResp1.setMethodName("generateResponse");
        genResp1.setClassName(testClass);

        modifierMethodTestSetup.addMethodName("getTotalNumMessagesRemaining");
        modifierMethodTestSetup.addMethodReturn(new MethodReturn(true,int.class,9));
        modifierMethodTestSetup.addIsStatic(true);
        modifierMethodTestSetup.addTestModAttrs(null);
        modifierMethodTestSetup.addMethodInputParams(null);
        modifierMethodTestSetup.addMethodExpectedAttrChanges(null);
        modifierMethodTestSetup.addPreTestMethods(List.of(genResp1));

        totRem.setTestSetupDetailMap(modifierMethodTestSetup.getMap());
        totRem.executeTest();

    }

    private void limitReached(AssignmentFeedBack assignmentFeedBack, File ChatBot){
        SingleCaseClassTestExecutor limReach = new SingleCaseClassTestExecutor();
        limReach.setClassTest((ClassTest) MethodTestFactory.createTest("retriever"));
        limReach.setAssignmentFeedBack(assignmentFeedBack);
        limReach.setEvaluatingFile(ChatBot);
        limReach.setMarks(3);

        String testClass = "ChatBot";


        ClassTestRunMethod genResp1 = new ClassTestRunMethod();
        genResp1.setMethodName("generateResponse");
        genResp1.setClassName(testClass);

        modifierMethodTestSetup.addMethodName("limitReached");
        modifierMethodTestSetup.addMethodReturn(new MethodReturn(true,boolean.class,false));
        modifierMethodTestSetup.addIsStatic(true);
        modifierMethodTestSetup.addTestModAttrs(null);
        modifierMethodTestSetup.addMethodInputParams(null);
        modifierMethodTestSetup.addMethodExpectedAttrChanges(null);
        modifierMethodTestSetup.addPreTestMethods(List.of(genResp1));

        limReach.setTestSetupDetailMap(modifierMethodTestSetup.getMap());
        limReach.executeTest();

    }

    private void cbtoString(AssignmentFeedBack assignmentFeedBack, File ChatBot){
        SingleCaseClassTestExecutor botToString = new SingleCaseClassTestExecutor();
        botToString.setClassTest((ClassTest) MethodTestFactory.createTest("retriever"));
        botToString.setAssignmentFeedBack(assignmentFeedBack);
        botToString.setEvaluatingFile(ChatBot);
        botToString.setMarks(1);

        String testClass = "ChatBot";


        ClassTestRunMethod cbCons = new ClassTestRunMethod();
        cbCons.setConstructor(testClass);
        cbCons.setMethodParams(List.of(new ClassTestParameter(int.class,4)));

        modifierMethodTestSetup.addMethodName("toString");
        modifierMethodTestSetup.addMethodReturn(new MethodReturn(true,String.class,"ChatBot Name: Claude@Number Messages Used: 0"));
        modifierMethodTestSetup.addIsStatic(false);
        modifierMethodTestSetup.addTestModAttrs(null);
        modifierMethodTestSetup.addMethodInputParams(null);
        modifierMethodTestSetup.addMethodExpectedAttrChanges(null);
        modifierMethodTestSetup.addPreTestMethods(List.of(cbCons));

        botToString.setTestSetupDetailMap(modifierMethodTestSetup.getMap());
        botToString.executeTest();

    }

    private void handleChatBotPlatformRetrievalMethods(AssignmentFeedBack assignmentFeedBack, File ChatBotPlatform){
        SingleCaseClassTestExecutor botToString = new SingleCaseClassTestExecutor();
        botToString.setClassTest((ClassTest) MethodTestFactory.createTest("retriever"));
        botToString.setAssignmentFeedBack(assignmentFeedBack);
        botToString.setEvaluatingFile(ChatBotPlatform);
        botToString.setMarks(6);


        ClassTestRunMethod addChatBot1 = new ClassTestRunMethod();
        addChatBot1.setMethodName("addChatBot");
        addChatBot1.setClassName("ChatBotPlatform");
        addChatBot1.setMethodParams(List.of(new ClassTestParameter(int.class,2)));

        ClassTestRunMethod addChatBot2 = new ClassTestRunMethod();
        addChatBot2.setMethodName("addChatBot");
        addChatBot2.setClassName("ChatBotPlatform");
        addChatBot2.setMethodParams(List.of(new ClassTestParameter(int.class,5)));


        modifierMethodTestSetup.addMethodName("getChatBotList");
        modifierMethodTestSetup.addMethodReturn(new MethodReturn(true,String.class,"ChatBot Name: Mistral7B@ChatBot Name: Solar"));
        modifierMethodTestSetup.addIsStatic(false);
        modifierMethodTestSetup.addTestModAttrs(null);
        modifierMethodTestSetup.addMethodInputParams(null);
        modifierMethodTestSetup.addMethodExpectedAttrChanges(null);
        modifierMethodTestSetup.addPreTestMethods(List.of(addChatBot1,addChatBot2));

        botToString.setTestSetupDetailMap(modifierMethodTestSetup.getMap());
        botToString.executeTest();
    }

    /**
     * Handles the method tests for the chatBotGenerator class in the student's assignment.
     * @param assignmentFeedBack the feedback object for the assignment
     * @param chatBotGenerator the chatBotGenerator class file to be tested
     */
    private void chatBotGeneratorMethods(AssignmentFeedBack assignmentFeedBack, File chatBotGenerator){
        MultiCaseClassTestExecutor cbGenerator = new MultiCaseClassTestExecutor();

        cbGenerator.setClassTest((ClassTest) MethodTestFactory.createTest("modifier"));
        cbGenerator.setEvaluatingFile(chatBotGenerator);
        cbGenerator.setAssignmentFeedBack(assignmentFeedBack);
        cbGenerator.setMarks(7);

        List<CustomCase> cases = new ArrayList<>();
        for (int i = 1; i < 7; i++){
            CustomCase c = getChatBotGeneratorCase(i);
            cases.add(c);
        }
        cbGenerator.setTestCases(cases);
        modifierMethodTestSetup.addMethodName("generateChatBotLLM");
        modifierMethodTestSetup.addMethodReturn(null);
        modifierMethodTestSetup.addIsStatic(false);
        modifierMethodTestSetup.addTestModAttrs(null);
        modifierMethodTestSetup.addMethodInputParams(null);
        modifierMethodTestSetup.addMethodExpectedAttrChanges(null);

        cbGenerator.setTestSetupDetailMap(modifierMethodTestSetup.getMap());
        cbGenerator.executeTest();



    }
    private static CustomCase getChatBotGeneratorCase(int i){
        CustomCase customCase = new CustomCase();
        customCase.setCustomParams(
            List.of(
                new ClassTestParameter(
                    int.class,
                    i
                )
        ));
        customCase.setCustomReturn(
            new MethodReturn(true,String.class,getBot(i))
        );
        customCase.setCustomExpectedAttributeValue(null);
        customCase.setCustomTestAttributeValue(null);
        return customCase;
    }
    private static String getBot(int botcode){
        return switch (botcode){
            case 1 -> "LLaMa";
            case 2 -> "Mistral7B";
            case 3 -> "Bard";
            case 4 -> "Claude";
            case 5 -> "Solar";
            default -> "ChatGPT-3.5";
        };
    }
    private void chatBotSimulation(AssignmentFeedBack assignmentFeedBack, File ChatBotSimulation){

    }

}
