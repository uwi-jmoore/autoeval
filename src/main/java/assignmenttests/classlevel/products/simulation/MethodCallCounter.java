package assignmenttests.classlevel.products.simulation;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.MethodCallExpr;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * A utility class that provides a method to count the occurrences of a specific method call 
 * within a Java source file. This class uses the JavaParser library to parse the Java file 
 * and search for the specified method calls.
 */
public class MethodCallCounter {

    /**
     * Counts the number of times a specific method is called in a Java source file.
     * This method parses the given file, searches for all method call expressions, 
     * and counts how many times the specified method is called.
     *
     * @param filePath The path to the Java source file to analyze.
     * @param methodName The name of the method whose calls are to be counted.
     * @return The number of times the specified method is called in the file.
     * @throws IOException If the file cannot be read or parsed.
     */
    public static int countMethodCalls(String filePath, String methodName) throws IOException {
        JavaParser parser = new JavaParser();
        CompilationUnit cu = parser.parse(Paths.get(filePath)).getResult()
            .orElseThrow(() -> new IOException("Unable to parse file"));

        long count = cu.findAll(MethodCallExpr.class).stream()
            .filter(mce -> mce.getNameAsString().equals(methodName))
            .count();

        return (int) count;
    }
}
