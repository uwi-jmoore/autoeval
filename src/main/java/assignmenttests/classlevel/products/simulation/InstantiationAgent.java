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


public class InstantiationAgent {
    protected static boolean instanceDetected = false;
    protected static String targetClassName;
    protected static File file;

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
    public void setTargetClassName(String className) {
        InstantiationAgent.targetClassName = className;
    }

    public void setFile(File file){
        InstantiationAgent.file = file;
    }
    // Method to check if the target instance was detected
    public boolean isInstanceDetected() {
        return instanceDetected;
    }

    public boolean findInstance(){

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

    private static class VariableVisitor extends VoidVisitorAdapter<Void> {
        private final String targetType;
        private boolean found = false;

        public VariableVisitor(String targetType) {
            this.targetType = targetType;
        }

        public void visit(VariableDeclarator var, Void arg) {
            super.visit(var, arg);
            if (var.getType().asString().equals(targetType)) {
                found = true;
            }
        }

        public boolean getFound() {
            return found;
        }


    }
}
