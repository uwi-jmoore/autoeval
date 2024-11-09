package assignmenttests.classlevel.concreteproducts;

import assignmentevaluator.classloader.AssignmentClassLoader;
import assignmenttests.classlevel.ClassTest;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LogicTest<T> implements ClassTest{
    private String classFilePath;
    private T expectedValue;

    @Override
    public void setUpTestDetails(Map<String, String> setUpContent) {

    }

    @Override
    public void setClassFilePath(String classFilePath) {
        this.classFilePath = classFilePath;
    }

    public void setExpectedValue(T value) {
        this.expectedValue = value;
    }

    @Override
    public String toString(){
        return "Method Logic Test";
    }

    public Stream<String> provideClassFilePath() {
        if (classFilePath != null) {
            return Stream.of(classFilePath);  // Instance-level classFilePath
        } else {
            throw new IllegalStateException("classFilePath is not set. Ensure setClassFilePath() is called before running the test.");
        }
    }
    @ParameterizedTest
    @MethodSource("provideClassFilePath")
    public void testClassExistence(String classFilePath) throws IOException {
        AssignmentClassLoader testLoader = new AssignmentClassLoader();
        Class<?> loadedClass = testLoader.loadClassFromFile(classFilePath);
        assertNotNull(loadedClass);
    }
}
