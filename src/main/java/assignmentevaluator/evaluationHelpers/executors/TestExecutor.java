package assignmentevaluator.evaluationHelpers.executors;

import assignmentevaluator.AssignmentFeedBack;
import assignmentevaluator.feedback.types.ConcreteTestFeedback;
import assignmenttests.AssignmentTest;

public abstract class TestExecutor {
    protected AssignmentTest test;

    /** The feedback object that keeps track of the student's results. */
    protected AssignmentFeedBack assignmentFeedBack;

    /** The marks the student will receive on passing the test case(s). */
    protected int marks;

    /** Type of test being executed. */
    protected String testType;

    /** Stores feedback for the specific test case. */
    protected ConcreteTestFeedback testFeedback;


    public void setTest(AssignmentTest test) {
        this.test = test;
    }

    /**
     * Sets the assignment feedback object to store results.
     *
     * @param assignmentFeedBack the feedback object that stores test results.
     */
    public void setAssignmentFeedBack(AssignmentFeedBack assignmentFeedBack) {
        this.assignmentFeedBack = assignmentFeedBack;
    }

    /**
     * Gets the assignment feedback containing the results.
     *
     * @return the assignment feedback.
     */
    public AssignmentFeedBack getAssignmentFeedBack() {
        return assignmentFeedBack;
    }

    /**
     * Sets the marks for passing the test cases.
     *
     * @param marks the score awarded upon passing the tests.
     */
    public void setMarks(int marks) {
        this.marks = marks;
    }

    public abstract void executeTest();
}
