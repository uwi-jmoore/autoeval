package pdfgeneration;

import java.util.List;

public interface ReportGeneratorInterface {
    void generateReport(List<TestResult> testResults, int totalPoints, String outputFileName);
}

