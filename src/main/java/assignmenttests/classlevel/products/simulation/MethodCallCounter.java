package assignmenttests.classlevel.products.simulation;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.MethodCallExpr;

import java.io.IOException;
import java.nio.file.Paths;

public class MethodCallCounter {

    public static int countMethodCalls(String filePath, String methodName) throws IOException {
        JavaParser parser = new JavaParser();
        CompilationUnit cu = parser.parse(Paths.get(filePath)).getResult().orElseThrow(() -> new IOException("Unable to parse file"));

        long count = cu.findAll(MethodCallExpr.class).stream()
            .filter(mce -> mce.getNameAsString().equals(methodName))
            .count();

        return (int) count;
    }
}
