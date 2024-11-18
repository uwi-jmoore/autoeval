package MethodTest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.grp10.*;
import pdfgeneration.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MethodChecker {

    private static final List<TestResult> testResults = new ArrayList<>();
    private static int totalPoints = 0;
    private final ReportGeneratorInterface reportGenerator = new PdfReportGenerator();

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

    @AfterEach
    public void tearDown() {
//        reportGenerator.generateReport(testResults, totalPoints, "TestReport.pdf");
    }
}
