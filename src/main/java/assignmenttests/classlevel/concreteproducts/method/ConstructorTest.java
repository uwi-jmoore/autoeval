package assignmenttests.classlevel.concreteproducts.method;

import assignmentevaluator.classloader.AssignmentClassLoader;
import assignmenttests.classlevel.concreteproducts.MethodTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ConstructorTest extends MethodTest{
    private static Constructor<?>[] constructors;

    @Override
    public String toString() {
        return "Constructor Test";
    }



    @Override
    public void setUpTestDetails(Map<String, String> setUpContent) {
        List<String> expectedSetupContents = Arrays.asList(
            "methodInputs",
            "modifiedClassAttributes"
        );
        List<String> missingKeys = findMissingKeys(setUpContent,expectedSetupContents);
        if(missingKeys.isEmpty()){
            List<Object> methodPar = Collections.singletonList(setUpContent.get("methodInputs"));
            List<String> modifAttributes = Collections.singletonList(setUpContent.get("modifiedClassAttributes"));
            setMethodParams(methodPar);
            setModifiedAttributes(modifAttributes);
        }else{
            System.err.println("Missing keys in setup Map: "+missingKeys);
        }
    }


    @BeforeEach
    public void initializeLoadedClass() throws IOException{
        testLoader = new AssignmentClassLoader();
        loadedClass = testLoader.loadClassFromFile(classFilePath);
        constructors = loadedClass.getConstructors();
        assertTrue(constructors.length > 0,"Could not find constructor for class");
    }

    private static Stream<Arguments> provideConstructorArguments(){
        if(classFilePath != null  && methodParams != null && modifiedAttributes != null){
            return Stream.of(Arguments.of(
                methodParams,
                modifiedAttributes
            ));
        }else{
            throw new IllegalStateException(
                "methodParams or modifiedAttributes not set. " +
                    "Ensure setMethodParams() and setModifiedAttributes() are called before running the test."
            );
        }
    }

    @ParameterizedTest
    @MethodSource("provideConstructorArguments")
    public void testEx(List<Object> constructorParams){
        for (Constructor<?> constructor: constructors){
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            try{
                if(parameterTypes.length == 0){
                    Object instance = constructor.newInstance();
                    assertNotNull(instance);
                }else{
                    assertAll("sa",
                        ()-> {
                            boolean allMatch = IntStream.range(0, parameterTypes.length)
                                .allMatch(i -> i < methodParams.size() && parameterTypes[i].isInstance(methodParams.get(i)));
                            assertTrue(allMatch);
                            Object instance = constructor.newInstance(methodParams);
                            assertNotNull(instance);
                        }
                     );

                }
            }catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
                System.err.println(e.getMessage());
                //make more verbose later
            }
        }
    }



}
