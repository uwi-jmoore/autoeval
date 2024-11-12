package pdfgeneration;

import java.util.List;

/**
 * The ReportGeneratorInterface defines the contract for generating a report based on test results.
 * <p>
 * Implementing classes must provide a method for generating a report in a specific format (e.g., PDF),
 * using the provided test results, total points, and output file name.
 * </p>
 * 
 * @see PdfReportGenerator
 */
public interface ReportGeneratorInterface {

    /**
     * Generates a report based on the provided test results and total points.
     * 
     * @param testResults A list of TestResult objects containing the test names and their pass/fail status.
     * @param totalPoints The total number of points scored in the tests.
     * @param outputFileName The name of the output file where the report will be saved.
     */
    void generateReport(List<TestResult> testResults, int totalPoints, String outputFileName);
}


