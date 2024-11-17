package assignmentevaluator.feedback.types;

import assignmentevaluator.feedback.TestFeedback;

/**
 * A concrete implementation of the {@link TestFeedback} interface, representing the feedback
 * for an individual test case with details such as feedback message, marks, and test type.
 */
public class ConcreteTestFeedback implements TestFeedback {
    private String FeedbackMsg; // The feedback message for the test
    private int marksToAddToTotal; // The marks to be added to the total score for passing the test
    private String testType; // The type of test that was executed

    /**
     * Gets the feedback message for this test.
     *
     * @return The feedback message as a {@link String}.
     */
    public String getFeedbackMsg() {
        return FeedbackMsg;
    }

    /**
     * Gets the marks awarded for this test.
     *
     * @return The marks awarded as an {@code int}.
     */
    public int getTestMarks() {
        return marksToAddToTotal;
    }

    /**
     * Gets the type of test executed.
     *
     * @return The type of test as a {@link String}.
     */
    public String getTestType() {
        return testType;
    }

    /**
     * Sets the feedback message for this test.
     *
     * @param feedbackMsg The feedback message to set.
     */
    public void setFeedbackMsg(String feedbackMsg) {
        FeedbackMsg = feedbackMsg;
    }

    /**
     * Sets the type of test executed.
     *
     * @param testType The test type to set.
     */
    public void setTestType(String testType) {
        this.testType = testType;
    }

    /**
     * Sets the marks to be awarded for this test.
     *
     * @param marks The marks to set for this test.
     */
    public void setMarks(int marks) {
        this.marksToAddToTotal = marks;
    }

    /**
     * Returns a string representation of the ConcreteTestFeedback object, including
     * the feedback message, marks, and test type.
     *
     * @return A string representation of this feedback.
     */
    @Override
    public String toString() {
        return "ConcreteTestFeedback{" +
            "FeedbackMsg='" + FeedbackMsg + '\'' +
            ", marks=" + marksToAddToTotal +
            ", testType='" + testType + '\'' +
            '}';
    }
}
