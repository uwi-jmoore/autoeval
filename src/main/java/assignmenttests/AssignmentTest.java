package assignmenttests;

/**
 * Interface representing a general test on an assignment.
 * Classes implementing this interface should define how marks
 * are awarded based on the specific test criteria.
 */
public interface AssignmentTest {

    /**
     * Retrieves the marks awarded for the assignment based on the test result.
     *
     * @return the marks as an integer
     */
    int getMarks();
}
