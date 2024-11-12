package assignmenttests.programlevel;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.util.Arrays;

import static filehandler.filehelperservice.FileOperationHelpers.getDirectoryFilesOfExt;

/**
 * Utility class providing helper methods for program-level tests on assignments.
 */
public class ProgramLevelTestHelpers {

    /**
     * Compiles all Java files in a given assignment directory.
     *
     * @param assignmentDirectory the directory containing the Java source files
     * @return true if compilation is successful, false if compilation fails or if no Java files are found
     */
    public static boolean compileAssignment(File assignmentDirectory) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            System.err.println("Could Not Compile, JDK missing");
            return false;
        }

        File[] assignmentFiles = getDirectoryFilesOfExt(assignmentDirectory, ".java");
        assert assignmentFiles != null;

        if (assignmentFiles.length == 0) {
            System.err.println("No Java Files found");
            return false;
        }

        int result = compiler.run(null, null, null,
            Arrays.stream(assignmentFiles)
                .map(File::getPath)
                .toArray(String[]::new)
        );

        if (result == 0) {
            System.out.println("Compilation successful");
            return true;
        }

        System.err.println("Compilation failed");
        return false;
    }
}
