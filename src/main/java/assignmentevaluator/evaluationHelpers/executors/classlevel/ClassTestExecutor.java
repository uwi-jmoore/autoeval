package assignmentevaluator.evaluationHelpers.executors.classlevel;

import assignmentevaluator.evaluationHelpers.executors.TestExecutor;
import assignmenttests.classlevel.ClassTest;

import java.io.File;
import java.util.Map;

public abstract class ClassTestExecutor extends TestExecutor {
    /** The class file being evaluated. */
    protected File evaluatingClassFile;

    /** The specific class level test that will be executed on the student's class. */
    protected ClassTest test;

    /** A map containing setup details for the test case. */
    protected Map<String, Object> testSetupDetailMap;




    /**
     * Sets the file being evaluated.
     *
     * @param evaluatingClassFile the file to be tested.
     */
    public void setEvaluatingFile(File evaluatingClassFile) {
        this.evaluatingClassFile = evaluatingClassFile;
    }

    /**
     * Sets the test case to be executed.
     *
     * @param test the test case for evaluation.
     */
    public void setClassTest(ClassTest test) {
        this.test = test;
    }

    /**
     * Sets up the details for the test using a map of configuration values.
     *
     * @param testSetupDetailMap a map of details to configure the test.
     */
    public void setTestSetupDetailMap(Map<String, Object> testSetupDetailMap) {
        this.testSetupDetailMap = testSetupDetailMap;
    }
}
