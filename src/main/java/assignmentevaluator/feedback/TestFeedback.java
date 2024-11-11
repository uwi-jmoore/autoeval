package assignmentevaluator.feedback;

/**
 * An interface representing feedback for individual test cases, 
 * providing methods to get and set feedback details such as message, marks, and type.
 */
public interface TestFeedback {

    /**
     * Gets the feedback message for this test.
     *
     * @return The feedback message as a {@link String}.
     */
    String getFeedbackMsg();

    /**
     * Gets the marks awarded for this test.
     *
     * @return The marks awarded as an {@code int}.
     */
    int getTestMarks();

    /**
     * Gets the type of test executed.
     *
     * @return The test type as a {@link String}.
     */
    String getTestType();

    /**
     * Sets the feedback message for this test.
     *
     * @param feedbackMsg The feedback message to set.
     */
    void setFeedbackMsg(String feedbackMsg);

    /**
     * Sets the type of test executed.
     *
     * @param testType The test type to set.
     */
    void setTestType(String testType);

    /**
     * Sets the marks to be awarded for this test.
     *
     * @param marks The marks to set.
     */
    void setMarks(int marks);
}
