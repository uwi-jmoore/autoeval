package assignmentevaluator.classloader;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AssignmentClassLoader extends ClassLoader{

    public Class<?> loadClassFromFileAlpha(String classFilePath) throws IOException{
        Path path = Paths.get(classFilePath);
        byte[] classBytes = Files.readAllBytes(path);

        // Define the class
        return defineClass(null, classBytes, 0, classBytes.length);
    }
}
