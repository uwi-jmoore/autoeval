package assignmentevaluator.evaluationHelpers.executors;

import assignmentevaluator.AssignmentFeedBack;
import assignmentevaluator.feedback.types.ConcreteTestFeedback;
import assignmenttests.AssignmentTest;

/**
 * Abstract base class for executing tests on a student's assignment.
 * Subclasses implement specific test execution logic for different test types.
 */
public abstract class TestExecutor {

    /** The test case being executed. */
    protected AssignmentTest test;

    /** The feedback object that tracks and stores results of the assignment evaluation. */
    protected AssignmentFeedBack assignmentFeedBack;

    /** The marks the student will receive upon passing the test case(s). */
    protected int marks;

    /** The type of test being executed (e.g., single test, multi-case test). */
    protected String testType;

    /** Stores feedback specific to the test case being executed. */
    protected ConcreteTestFeedback testFeedback;

    /**
     * Sets the test case to be executed.
     *
     * @param test the test case object.
     */
    public void setTest(AssignmentTest test) {
        this.test = test;
    }

    /**
     * Sets the assignment feedback object to store test results and other related feedback.
     *
     * @param assignmentFeedBack the feedback object for the assignment.
     */
    public void setAssignmentFeedBack(AssignmentFeedBack assignmentFeedBack) {
        this.assignmentFeedBack = assignmentFeedBack;
    }

    /**
     * Retrieves the assignment feedback containing the results of the executed tests.
     *
     * @return the feedback object for the assignment.
     */
    public AssignmentFeedBack getAssignmentFeedBack() {
        return assignmentFeedBack;
    }

    /**
     * Sets the marks to be awarded to the student upon passing the test case(s).
     *
     * @param marks the score to be awarded.
     */
    public void setMarks(int marks) {
        this.marks = marks;
    }

    /**
     * Executes the assigned test case(s) and updates the feedback object accordingly.
     * This method must be implemented by subclasses.
     */
    public abstract void executeTest();
}
