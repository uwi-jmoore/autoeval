package filehandler.filehelperservice;

import java.io.File;
import java.util.List;

/**
 * The Helpers class provides utility methods for handling files and directories.
 * These methods allow for file name extraction, directory creation, and validation of file types.
 * <p>
 * The class includes methods for getting the name, extension, and directory paths of a file, as well as validating 
 * if the file's extension matches a list of allowed extensions.
 * </p>
 */
public class Helpers {

    /**
     * Retrieves the name of the file (without its extension).
     * 
     * @param file The file from which the name is to be extracted.
     * @return The name of the file (without its extension).
     */
    public static String getFileName(File file) {
        return getFileNameArray(file)[0];
    }

    /**
     * Retrieves the extension of the file (the part after the last dot).
     * 
     * @param file The file from which the extension is to be extracted.
     * @return The file's extension.
     */
    public static String getFileExtension(File file) {
        return getFileNameArray(file)[1];
    }

    /**
     * Splits the file name into the name and extension.
     * 
     * @param file The file to split.
     * @return An array where the first element is the file name and the second element is the file extension.
     */
    private static String[] getFileNameArray(File file) {
        return file.getName().split("[.]");
    }

    /**
     * Retrieves the path of the grandparent directory of the given file.
     * 
     * @param file The file whose grandparent directory path is to be retrieved.
     * @return The path of the grandparent directory.
     */
    public static String getGrandparentDirectoryPath(File file) {
        return String.valueOf(file.getParentFile().getParentFile());
    }

    /**
     * Checks if the file has a valid extension from the provided list of valid extensions.
     * 
     * @param file The file to check.
     * @param validExtensions A list of valid file extensions.
     * @return true if the file's extension is in the list of valid extensions, false otherwise.
     */
    public static boolean isValidFileType(File file, List<String> validExtensions) {
        return validExtensions.contains(getFileExtension(file));
    }

    /**
     * Creates the directories for the specified file, including any necessary parent directories.
     * 
     * @param file The file for which the directories are to be created.
     */
    public static void createDirectories(File file) {
        System.out.println(file.mkdirs() ? "Successfully created directories" : "Could not create directories");
    }

    /**
     * Retrieves the path of the parent directory of the given file.
     * 
     * @param file The file whose parent directory path is to be retrieved.
     * @return The path of the parent directory.
     */
    public static String getParentDirectoryPath(File file) {
        return String.valueOf(file.getParentFile());
    }
}
