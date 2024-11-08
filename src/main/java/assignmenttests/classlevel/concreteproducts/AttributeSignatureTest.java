package assignmenttests.classlevel.concreteproducts;

import assignmentevaluator.classloader.AssignmentClassLoader;
import assignmenttests.classlevel.SignatureTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.launcher.*;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import java.io.IOException;
import java.util.stream.Stream;

import static filehandler.filehelperservice.FileOperationHelpers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder.request;

public class AttributeSignatureTest {
    private static String classFilePath;

    public void setClassFilePath(String classFilePath) {
        AttributeSignatureTest.classFilePath = classFilePath;
    }

    // Static method to provide the classFilePath
    public static    Stream<String> provideClassFilePaths() {
        if (classFilePath != null) {
            return Stream.of(classFilePath);
        } else {
            throw new IllegalStateException("classFilePath is not set. Ensure setClassFilePath() is called before running the test.");
        }
    }

    @ParameterizedTest
    @MethodSource("provideClassFilePaths")
    public void testSignature(String classFilePath) throws IOException {
        AssignmentClassLoader testLoader = new AssignmentClassLoader();
        Class<?> loadedClass = testLoader.loadClassFromFile(classFilePath);

        String className = getFileName(pathToFile(classFilePath));
        assertAll("Testing signature of class: " + className,
            () -> assertNotNull(loadedClass)
        );
    }

}
