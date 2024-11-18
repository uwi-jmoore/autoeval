package assignmenttests.classlevel.products.method.concrete;

import assignmenttests.classlevel.products.method.MethodTest;
import org.junit.jupiter.api.*;


import java.lang.reflect.Modifier;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RetrievalTest extends MethodTest {

    @Override
    public String toString() {
        return "Retrieval (Accessors, Getters) Method Test";
    }

    @Override
    protected void setMethodTestDetails(Map<String, Object> setUpContent) {
        super.setMethodTestDetails(setUpContent);
    }


    @BeforeEach
    public void initializeTest(){
        super.stdMethodTestInit();
        loadMethod(loadedClass,methodName);
        testResponse = getTestResponse();
    }


    @Test
    @DisplayName("Response Existence Test")
    public void testRetrievalMethodResponseExistence(){
        assertNotNull(testResponse,"Did not get a response");
    }

    @Test
    @DisplayName("Response Datatype Correctness Test")
    public void testRetrievalMethodDatatypeResponse(){
        assertEquals(testResponse.getClass(),primitiveToWrapper(methodReturn.datatype()),"Response Datatype is incorrect");
    }

    @Test
    @DisplayName("Response Data Match Test")
    public void testRetrievalMethodResponseContent(){
        assertTrue(testMethodReturnSimilarity(testResponse),"What was returned: "
            +testResponse
            +" did not match expected: "
            +methodReturn.returnValue());
    }

    @Test
    @DisplayName("Method Static Signature check")
    public void testRetrievalMethodIsStatic(){
        assertEquals(Modifier.isStatic(method.getModifiers()),isMethodStatic);
    }

    @AfterAll
    public void resetAllInitializations(){
        resetClassAttribute();
    }

}
