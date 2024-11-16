package pdfgeneration;

import java.util.List;

import assignmentevaluator.AssignmentFeedBack;

public interface FeedbackReportGeneratorInterface {
    void generateReportWithFeedback(List<TestResult> testResults, int totalPoints, String outputFileName, AssignmentFeedBack feedback);
}