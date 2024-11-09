package assignmentevaluator.evaluationHelpers;

import assignmentevaluator.AssignmentFeedBack;
import assignmentevaluator.feedback.types.ConcreteTestFeedback;
import assignmenttests.classlevel.ClassTest;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

import java.io.File;
import java.util.Map;

import static assignmentevaluator.evaluationHelpers.EvalHelpers.getTestReturn;

public class ClassTestExecutor {
    private AssignmentFeedBack assignmentFeedBack; // the Feedback object that keeps track of the student's results
    private File evalutatingFile; // the class file being evaluated
    private int marks; // the marks the student will receive on passing the test case(s)
    private ClassTest test; // the specific test that will be executed

    private ConcreteTestFeedback testFeedback;
    private Map<String,String> testSetupDetailMap;

    private String testType;



    public ClassTestExecutor(){
        testFeedback = new ConcreteTestFeedback();
    }

    public void setAssignmentFeedBack(AssignmentFeedBack assignmentFeedBack) {
        this.assignmentFeedBack = assignmentFeedBack;
    }

    public AssignmentFeedBack getAssignmentFeedBack() {
        return assignmentFeedBack;
    }

    public void setEvaluatingFile(File evalutatingFile) {
        this.evalutatingFile = evalutatingFile;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    public void setClassTest(ClassTest test) {
        this.test = test;
    }

    public void setTestSetupDetailMap(Map<String, String> testSetupDetailMap) {
        this.testSetupDetailMap = testSetupDetailMap;
    }



    public void execute(){
        testType = test.toString();
        testFeedback.setTestType(testType);
        test.setClassFilePath(evalutatingFile.getAbsolutePath());
        test.setUpTestDetails(testSetupDetailMap);
        TestExecutionSummary testReturn = getTestReturn(test.getClass()).getSummary();
        if(testReturn.getTestsFailedCount() == 0){
            handleAllPass(testReturn);
        }else{
            handleFailure(testReturn);
        }
        assignmentFeedBack.addTestResults(testFeedback);
    }


    private void handleAllPass(TestExecutionSummary testReturn){
        testFeedback.setMarks(marks);
        testFeedback.setFeedbackMsg(testType + "Passed,");
    }

    private void handleFailure(TestExecutionSummary testReturn){
        testFeedback.setMarks(0);
        testFeedback.setFeedbackMsg(testType + "Failed,");
    }

}
