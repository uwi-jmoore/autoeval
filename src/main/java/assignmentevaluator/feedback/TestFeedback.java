package assignmentevaluator.feedback;

public interface TestFeedback {
    String getFeedbackMsg();
    int getTestMarks();
    String getTestType();
    void setFeedbackMsg(String feedbackMsg);
    void setTestType(String testType);
    void setMarks(int marks);
}
