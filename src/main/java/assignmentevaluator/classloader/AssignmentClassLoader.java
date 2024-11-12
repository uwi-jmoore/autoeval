package assignmentevaluator.classloader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class AssignmentClassLoader extends ClassLoader {

    private final File classPath;

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
