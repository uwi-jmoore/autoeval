package assignmenttests.classlevel.products.simulation;

import com.github.javaparser.ParseProblemException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * The {@code InstantiationAgent} class is responsible for detecting instances of a 
 * specified class within a given Java file. It checks the fields of a class and searches 
 * for instances of the target class, as well as parsing the file to identify class 
 * instantiations.
 * 
 * <p>This class uses reflection and the JavaParser library to perform its tasks:</p>
 * <ul>
 *     <li>It inspects the fields of a given class to find instances of the target class.</li>
 *     <li>It parses a Java file to search for variable declarations of the target class type.</li>
 * </ul>
 */
public class InstantiationAgent {
    /**
     * Flag indicating whether an instance of the target class has been detected.
     */
    protected static boolean instanceDetected = false;

    /**
     * The name of the target class to search for in the file.
     */
    protected static String targetClassName;

    /**
     * The file containing the code to be analyzed.
     */
    protected static File file;

    /**
     * Tests the fields of a class for instances of the target class.
     * 
     * @param fields The fields of the class to test.
     */
    public void testFields(Field[] fields){
        for (Field field : fields) {
            field.setAccessible(true);
            Object fieldValue = null;
            try {
                fieldValue = field.get(null);
                if (fieldValue != null && fieldValue.getClass().getName().equals(targetClassName)) {
                    instanceDetected = true;
                    break;
                }
            } catch (IllegalAccessException illegalAccessException) {
                System.err.println("IllegalAccessException occurred while trying to access field "
                    + field.getName()
                    +". Reason: "
                    + illegalAccessException.getMessage()
                );
            }
        }
    }

    /**
     * Sets the target class name to search for in the fields and the Java file.
     * 
     * @param className The name of the class to search for.
     */
    public void setTargetClassName(String className) {
        InstantiationAgent.targetClassName = className;
    }

    /**
     * Sets the file to be analyzed.
     * 
     * @param file The file containing the Java code to analyze.
     */
    public void setFile(File file){
        InstantiationAgent.file = file;
    }

    /**
     * Checks whether an instance of the target class was detected in the file.
     * 
     * @return {@code true} if an instance was detected, {@code false} otherwise.
     */
    public boolean isInstanceDetected() {
        return instanceDetected;
    }

    /**
     * Searches the given file for an instance of the target class by parsing the file and checking
     * for variable declarations of the target class type.
     * 
     * @param loadedClass The class being loaded for analysis.
     * @return {@code true} if an instance of the target class was found, {@code false} otherwise.
     */
    public boolean findInstance(Class<?> loadedClass){
        String fname = file.getName().split("[.]")[0];
        String parentPath = file.getParentFile().getAbsolutePath();
        String completePath = Paths.get(parentPath, fname + ".java").toString();
        try {
            String content = new String(Files.readAllBytes(Paths.get(completePath)));
            CompilationUnit cu = null;
            try {
                cu = StaticJavaParser.parse(content);
                VariableVisitor visitor = new VariableVisitor(targetClassName);
                cu.accept(visitor, null);
                return visitor.getFound();
            } catch (ParseProblemException e) {
                System.err.println("Error parsing the file: " + e.getMessage());
                e.getProblems().forEach(problem -> {
                    System.err.println("Problem: " + problem.getMessage());
                });
                System.err.println("The file path: "+ completePath);
            }
        } catch (IOException ioException) {
            System.err.println("IOException occurred while trying to read bytes of file"+ ioException.getMessage());
        }
        return false;
    }

    /**
     * A helper class that visits variable declarations in the parsed Java file and checks if 
     * any of the variables are of the target class type.
     */
    private static class VariableVisitor extends VoidVisitorAdapter<Void> {
        private final String targetType;
        private boolean found = false;

        /**
         * Constructs a {@code VariableVisitor} for the given target type.
         * 
         * @param targetType The target class type to search for.
         */
        public VariableVisitor(String targetType) {
            this.targetType = targetType;
        }

        /**
         * Visits a variable declaration and checks if it is of the target type.
         * 
         * @param var The variable declaration to visit.
         * @param arg Additional argument, not used here.
         */
        public void visit(VariableDeclarator var, Void arg) {
            super.visit(var, arg);
            if (var.getType().asString().equals(targetType)) {
                found = true;
            }
        }

        /**
         * Returns whether a variable of the target class type was found.
         * 
         * @return {@code true} if a variable of the target class type was found, 
         *         {@code false} otherwise.
         */
        public boolean getFound() {
            return found;
        }
    }
}
