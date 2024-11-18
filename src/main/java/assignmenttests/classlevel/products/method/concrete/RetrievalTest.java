package assignmenttests.classlevel.products.method.concrete;

import java.lang.reflect.Modifier;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import assignmenttests.classlevel.products.method.MethodTest;

/**
 * Test class for verifying retrieval (accessor, getter) methods in class-level testing.
 * This class extends {@link MethodTest} and implements method testing functionality.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RetrievalTest extends MethodTest {

    /**
     * Returns the string representation of this test class.
     * @return String "Retrieval (Accessors, Getters) Method Test".
     */
    @Override
    public String toString() {
        return "Retrieval (Accessors, Getters) Method Test";
    }

    /**
     * Sets up the test details based on the provided map content.
     * @param setUpContent A map containing setup data for the method test.
     */
    @Override
    protected void setMethodTestDetails(Map<String, Object> setUpContent) {
        super.setMethodTestDetails(setUpContent);
    }

    /**
     * Initializes the test by invoking the superclass method and loading the method.
     */
    @BeforeEach
    public void initializeTest(){
        super.stdMethodTestInit();
        loadMethod(loadedClass, methodName);
        testResponse = getTestResponse();
    }

    /**
     * Tests if the response from the method invocation is not null.
     */
    @Test
    @DisplayName("Response Existence Test")
    public void testRetrievalMethodResponseExistence(){
        assertNotNull(testResponse, "Did not get a response");
    }

    /**
     * Tests if the response datatype matches the expected datatype.
     */
    @Test
    @DisplayName("Response Datatype Correctness Test")
    public void testRetrievalMethodDatatypeResponse(){
        assertEquals(testResponse.getClass(), primitiveToWrapper(methodReturn.datatype()), "Response Datatype is incorrect");
    }

    /**
     * Tests if the response content matches the expected return value.
     */
    @Test
    @DisplayName("Response Data Match Test")
    public void testRetrievalMethodResponseContent(){
        assertTrue(testMethodReturnSimilarity(testResponse), "What was returned: "
            + testResponse
            + " did not match expected: "
            + methodReturn.returnValue());
    }

    /**
     * Tests if the method is static.
     */
    @Test
    @DisplayName("Method Static Signature check")
    public void testRetrievalMethodIsStatic(){
        assertEquals(Modifier.isStatic(method.getModifiers()), isMethodStatic);
    }

    /**
     * Resets all initializations after all tests have run.
     */
    @AfterAll
    public void resetAllInitializations(){
        resetClassAttribute();
    }
}
