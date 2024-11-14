package MethodTest;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import com.grp10.App;

import pdfgeneration.PdfReportGenerator;
import pdfgeneration.ReportGeneratorInterface;
import pdfgeneration.TestResult;

/**
 * The MethodChecker class is a test class that verifies the presence of the main method
 * in the App class. It also generates a PDF report of test results after each test
 * execution.
 * 
 * <p>This class utilizes JUnit 5 for testing, and it uses a {@link ReportGeneratorInterface}
 * implementation to create a report. The report generation is handled in the {@link #tearDown()}
 * method using an instance of {@link PdfReportGenerator}.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MethodChecker {

    /**
     * A list to hold test results for each verification performed within the class.
     */
    private static final List<TestResult> testResults = new ArrayList<>();

    /**
     * Total points awarded for successful test cases.
     */
    private static int totalPoints = 0;

    /**
     * An instance of ReportGeneratorInterface to handle report generation for test results.
     */
    private final ReportGeneratorInterface reportGenerator = new PdfReportGenerator();

    /**
     * Tests whether the main method exists in the App class. If the method is found,
     * a TestResult is added indicating success, and points are awarded. If the method
     * does not exist, an AssertionError is thrown, and a failure result is recorded.
     */
    @Test
    public void testMainMethodExists() {
        try {
            Method method = App.class.getDeclaredMethod("main", String[].class);
            testResults.add(new TestResult("Main method exists", true));
            totalPoints += 10;
        } catch (NoSuchMethodException e) {
            testResults.add(new TestResult("Main method exists", false));
            throw new AssertionError("Method main() does not exist in App.");
        }
    }

    /**
     * Generates a PDF report containing the results of the test case and total points.
     * This method is executed after each test case, ensuring the report is updated
     * after each verification.
     */
    @AfterEach
    public void tearDown() {
        reportGenerator.generateReport(testResults, totalPoints, "TestReport.pdf");
    }
}
