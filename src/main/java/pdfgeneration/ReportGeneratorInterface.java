package pdfgeneration;

import java.util.List;

import assignmentevaluator.AssignmentFeedBack;

public interface ReportGeneratorInterface {
    void generateReport(List<TestResult> testResults, int totalPoints, String outputFileName, AssignmentFeedBack feedback);
}

