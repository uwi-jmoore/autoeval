package assignmenttests;

import org.junit.platform.launcher.listeners.SummaryGeneratingListener;

import java.util.Map;

/**
 * Interface representing a general test on an assignment.
 * Classes implementing this interface should define how marks
 * are awarded based on the specific test criteria.
 */
public interface AssignmentTest {

//    /**
//     * Retrieves the marks awarded for the assignment based on the test result.
//     *
//     * @return the marks as an integer
//     */
//    int getMarks();
    SummaryGeneratingListener executeTest(Class<?> targetTestClass);
    void setUpTestDetails(Map<String, Object> setUpContent);
    String toString();
}
