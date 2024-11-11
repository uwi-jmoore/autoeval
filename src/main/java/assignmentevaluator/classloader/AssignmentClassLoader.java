package assignmentevaluator.classloader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * A custom class loader that loads a class from a specified file path.
 * This class extends the built-in {@link ClassLoader} class and allows for dynamic loading
 * of a class by reading the binary class data from a file.
 */

public class AssignmentClassLoader extends ClassLoader {

    /**
     * Loads a class from a file path that is passed as a String.
     *
     * @param classFilePath The String path of the .class file to be loaded.
     * @return The {@link Class} object created from the specified class file.
     * @throws IOException If there is an error reading the class file.
     */

    public Class<?> loadClassFromFile(String classFilePath) throws IOException {
        Path path = Paths.get(classFilePath);
        byte[] classBytes = Files.readAllBytes(path);

        // Define the class
        return defineClass(null, classBytes, 0, classBytes.length);
    }

}
