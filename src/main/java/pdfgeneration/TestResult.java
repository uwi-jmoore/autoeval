package pdfgeneration;

public class TestResult {
    private final String testName;
    private final boolean passed;

    public TestResult(String testName, boolean passed) {
        this.testName = testName;
        this.passed = passed;
    }

    public String getTestName() {
        return testName;
    }

    public boolean isPassed() {
        return passed;
    }
}

