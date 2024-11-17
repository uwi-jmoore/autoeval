package assignmentevaluator.classloader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * A custom {@code ClassLoader} implementation that dynamically loads classes
 * from a specified directory. This class is useful for evaluating assignments
 * or loading classes that are not available in the standard classpath.
 * 
 * <p>The {@code AssignmentClassLoader} attempts to load the class from the
 * specified directory first. If the class cannot be found in the directory,
 * it delegates the loading process to the parent {@code ClassLoader}.
 */
public class AssignmentClassLoader extends ClassLoader {

    /**
     * The root directory containing the compiled class files to be loaded.
     */
    private final File assignmentDirectory;

    /**
     * Constructs a new {@code AssignmentClassLoader} with the specified directory.
     *
     * @param assignmentDirectory the root directory containing the class files to be loaded.
     *                            Must not be {@code null}.
     * @throws IllegalArgumentException if {@code assignmentDirectory} is not a directory
     *                                  or does not exist.
     */
    public AssignmentClassLoader(File assignmentDirectory) {
        if (assignmentDirectory == null || !assignmentDirectory.isDirectory()) {
            throw new IllegalArgumentException("The provided assignmentDirectory must be a valid directory.");
        }
        this.assignmentDirectory = assignmentDirectory;
    }

    /**
     * Loads the class with the specified name. The method first attempts to load
     * the class from the directory specified during the creation of this class loader.
     * If the class is not found in the directory, it delegates the loading process
     * to the parent class loader.
     *
     * @param className the fully qualified name of the class to be loaded.
     * @return the {@code Class} object representing the loaded class.
     * @throws ClassNotFoundException if the class cannot be found in the specified directory
     *                                or the parent class loader cannot load it.
     */
    @Override
    public Class<?> loadClass(String className) throws ClassNotFoundException {
        // Check if the class is already loaded
        Class<?> loadedClass = findLoadedClass(className);
        if (loadedClass != null) {
            return loadedClass;
        }

        try {
            // Convert the fully qualified class name to a file path
            String classFilePath = className.replace('.', File.separatorChar) + ".class";
            File classFile = new File(assignmentDirectory, classFilePath);

            // Load the class bytes if the file exists
            if (classFile.exists()) {
                byte[] classBytes = Files.readAllBytes(classFile.toPath());
                return defineClass(className, classBytes, 0, classBytes.length);
            }
        } catch (IOException ioException) {
            System.err.println("Could not read bytes of class file for " + className +
                ". Reason: " + ioException.getMessage());
        }

        // Delegate to the parent class loader if the class is not found
        return super.loadClass(className);
    }
}
