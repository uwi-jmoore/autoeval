package assignmenttests.classlevel.concreteproducts;

import assignmenttests.classlevel.ClassTest;
import org.junit.jupiter.api.TestInstance;

import java.util.Map;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MethodSignatureTest implements ClassTest {
    private Class<?> loadedClass;

    private String classFilePath;


    @Override
    public void setClassFilePath(String classFilePath) {
        this.classFilePath = classFilePath;
    }

    @Override
    public void setUpTestDetails(Map<String, String> setUpContent) {

    }
}
