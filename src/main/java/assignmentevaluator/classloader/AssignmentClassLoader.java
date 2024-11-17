package assignmentevaluator.classloader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * A custom class loader that loads a class from a specified file path.
 * This class extends the built-in {@link ClassLoader} class and allows for dynamic loading
 * of a class by reading the binary class data from a file.
 */

public class AssignmentClassLoader extends ClassLoader {
    private final File assignmentDirectory;


    // Constructor takes the path where the class files are located
    public AssignmentClassLoader(File assignmentDirectory) {
        this.assignmentDirectory = assignmentDirectory;
    }

    /**
     * Loads a class from a class name that is passed as a String.
     *
     * @param className The String name of the .class file to be loaded.
     * @return The {@link Class} object created from the specified class file.
     * @throws ClassNotFoundException If the class name given cannot be found in the directory given by assignmentDirectory.
     */

    @Override
    public Class<?> loadClass(String className) throws ClassNotFoundException {
        // Check if the class is already loaded
        Class<?> loadedClass = findLoadedClass(className);
        if (loadedClass != null) {
            return loadedClass;
        }

        try {
            String classFilePath = className.replace('.', File.separatorChar) + ".class";
            File classFile = new File(assignmentDirectory, classFilePath);

            if (classFile.exists()) {
                byte[] classBytes = Files.readAllBytes(classFile.toPath());
                return defineClass(className, classBytes, 0, classBytes.length);
            }
        } catch (IOException ioException) {
            System.err.println("Could Not Read bytes of class file for " + className +
                ". Reason: " + ioException.getMessage());
        }
        //if we cannot find the class in the assignment directory, try to find the class by querying JVM
        return super.loadClass(className);
    }

}

