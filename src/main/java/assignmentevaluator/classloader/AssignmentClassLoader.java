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
    private final File classPath;

    /**
     * Loads a class from a file path that is passed as a String.
     *
     * @param classFilePath The String path of the .class file to be loaded.
     * @return The {@link Class} object created from the specified class file.
     * @throws IOException If there is an error reading the class file.
     */
    
    // Constructor takes the path where the class files are located
    public AssignmentClassLoader(File classPath) {
        this.classPath = classPath;
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        try {
            String classFilePath = name.replace('.', File.separatorChar) + ".class";
            File classFile = new File(classPath, classFilePath);

            if (classFile.exists()) {
                byte[] classBytes = Files.readAllBytes(classFile.toPath());
                return defineClass(name, classBytes, 0, classBytes.length);
            }
        } catch (IOException ioException) {
            System.err.println("Could Not Read bytes of class file for " + name +
                ". Reason: "+ ioException.getMessage());
        }
        return super.loadClass(name);
    }

}
//Old ClassLoader
//public class AssignmentClassLoader extends ClassLoader{
//
//    public Class<?> loadClassFromFile(String classFilePath) throws IOException{
//        Path path = Paths.get(classFilePath);
//        byte[] classBytes = Files.readAllBytes(path);
//
//        URL classFileURL = path.toUri().toURL();
//        CodeSource codeSource = new CodeSource(classFileURL, (java.security.cert.Certificate[]) null);
//
//        // Create a ProtectionDomain with the CodeSource
//        ProtectionDomain protectionDomain = new ProtectionDomain(codeSource, null);
//
//        // Define the class
//        return defineClass(null, classBytes, 0, classBytes.length,protectionDomain);
//    }
//}
