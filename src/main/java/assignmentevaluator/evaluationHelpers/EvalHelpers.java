package assignmentevaluator.evaluationHelpers;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Objects;

import assignmentevaluator.classloader.AssignmentClassLoader;


import static filehandler.filehelperservice.FileOperationHelpers.getDirectoryFilesOfExt;
import static filehandler.filehelperservice.FileOperationHelpers.getFileName;

/**
 * Utility class for helper methods used in evaluating assignments.
 */
public class EvalHelpers {

    /**
     * Retrieves an array of files with a specified extension from a given directory.
     *
     * @param studentAssignmentDirectory The directory containing the student's assignment files.
     * @param type The file extension type to filter for (e.g., "java" or "class").
     * @return An array of {@link File} objects that match the specified extension within the directory.
     */
    public static File[] getAssignmentFiles(File studentAssignmentDirectory, String type) {
        return getDirectoryFilesOfExt(studentAssignmentDirectory, type);
    }

    /**
     * Searches for a specific file in a given directory by its name and type.
     *
     * @param studentAssignmentDirectory The directory containing the student's assignment files.
     * @param fileName The name of the file to search for (without extension).
     * @param fileType The file type/extension to search for.
     * @return The {@link File} object that matches the given name and type, or {@code null} if not found.
     */
    public static File getFile(File studentAssignmentDirectory, String fileName, String fileType) {
        File[] assignmentJavaFiles = getAssignmentFiles(studentAssignmentDirectory, fileType);
        for (File f : assignmentJavaFiles) {
            if (Objects.equals(getFileName(f), fileName)) {
                return f;
            }
        }
        return null;
    }


    /**
     * Accesses and loads fields from classes not currently loaded for the current test.
     *
     * @param attributeName the attribute to load from the non-loaded Class
     * @param testLoader instance of AssignmentClassLoader to load the external class
     * @param className the name of the class to load, passed in as parameter to testLoader.loadClass()
     * @return A {@link Field} representing the loaded field from the external class.
     */
    public static Field getOuterClassAttribute(String attributeName, AssignmentClassLoader testLoader, String className){
        Class<?> otherClass;
        Field loadedOtherField = null;

        try {
            otherClass = testLoader.loadClass(className);
            loadedOtherField = otherClass.getDeclaredField(attributeName);

        } catch (NoSuchFieldException | ClassNotFoundException e) {
            System.err.println("Could not load field "+ attributeName
                +"trying to access outer class , NoSuchFieldException occurred. Reason: " + e.getMessage());
        }
        return loadedOtherField;
    }
}

