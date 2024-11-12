package pdfgeneration;

/**
 * The TestResult class represents the result of a single test, including its name and whether it passed or failed.
 * <p>
 * Instances of this class store the test name and its status (pass or fail). This class is typically used
 * in conjunction with report generation classes to document the results of multiple tests.
 * </p>
 */
public class TestResult {
    
    private final String testName;
    private final boolean passed;

    /**
     * Constructs a new TestResult with the specified test name and status.
     * 
     * @param testName The name of the test.
     * @param passed A boolean indicating whether the test passed (true) or failed (false).
     */
    public TestResult(String testName, boolean passed) {
        this.testName = testName;
        this.passed = passed;
    }

    /**
     * Returns the name of the test.
     * 
     * @return The name of the test.
     */
    public String getTestName() {
        return testName;
    }

    /**
     * Returns the pass/fail status of the test.
     * 
     * @return true if the test passed, false if the test failed.
     */
    public boolean isPassed() {
        return passed;
    }
}
