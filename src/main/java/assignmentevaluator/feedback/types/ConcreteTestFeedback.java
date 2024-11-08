package assignmentevaluator.feedback.types;

import assignmentevaluator.feedback.TestFeedback;

public class ConcreteTestFeedback implements TestFeedback {
    private String FeedbackMsg;
    private int marksToAddToTotal;

    private String testType;

    public String getFeedbackMsg() {
        return FeedbackMsg;
    }
    public int getTestMarks(){
        return marksToAddToTotal;
    }

    public String getTestType() {
        return testType;
    }

    public void setFeedbackMsg(String feedbackMsg) {
        FeedbackMsg = feedbackMsg;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }
    public void setMarks(int marks){
        this.marksToAddToTotal = marks;
    }
}
