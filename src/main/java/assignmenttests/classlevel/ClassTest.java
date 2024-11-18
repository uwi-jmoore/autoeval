package assignmenttests.classlevel;

import assignmenttests.AssignmentTest;

/**
 * Interface for tests that involve validating or interacting with a class.
 * 
 * <p>
 * The {@code ClassTest} interface extends {@link AssignmentTest} and adds a method to set
 * the file path of the class to be tested. Implementing this interface allows for customized
 * testing of class-level behavior in assignments, including the ability to specify the class file
 * to be used for the test.
 * </p>
 */
public interface ClassTest extends AssignmentTest {

    /**
     * Sets the file path of the class to be tested.
     * 
     * <p>
     * This method is used to specify the location of the class file that will be tested
     * as part of the program.
     * </p>
     *
     * @param classFilePath The file path of the class to be tested.
     */
    void setClassFilePath(String classFilePath);
}
