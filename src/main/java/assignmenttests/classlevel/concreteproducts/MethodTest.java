package assignmenttests.classlevel.concreteproducts;

import assignmentevaluator.classloader.AssignmentClassLoader;
import assignmenttests.classlevel.ClassTest;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.provider.Arguments;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public abstract class MethodTest implements ClassTest {
    protected static Class<?> loadedClass;
    protected static AssignmentClassLoader testLoader;
    protected static String classFilePath;

    protected static String methodName;
    protected static List<Object> methodParams;
    protected static List<String> modifiedAttributes;

    @Override
    public void setClassFilePath(String classFilePath) {
        MethodTest.classFilePath = classFilePath;
    }

    //Standard Method Test methods
    protected List<String> findMissingKeys(Map<String, String> map, List<String> requiredKeys){
        List<String> missingKeys = new ArrayList<>();
        for (String k: requiredKeys){
            if(!map.containsKey(k)){
                missingKeys.add(map.get(k));
            }
        }
        return missingKeys;
    }


    protected void setMethodName(String inputMethodName){
        MethodTest.methodName = inputMethodName;
    }

    protected void setMethodParams(List<Object> inputParams){
        MethodTest.methodParams = inputParams;
    }

    protected void setModifiedAttributes(List<String> attributeNames){
        MethodTest.modifiedAttributes = attributeNames;
    }
    protected static Stream<Arguments> provideMethodArguments(){
        if(classFilePath != null && methodName != null && methodParams != null){
            return Stream.of(Arguments.of(
                methodName,
                methodParams
            ));
        }else{
            throw new IllegalStateException(
                "methodName or methodParams not set. " +
                    "Ensure setMethodName() and setMethodParams() are called before running the test."
            );
        }
    }
}
