package filehandler.filehelperservice;

import filehandler.traversal.DirectoryAggregate;
import filehandler.traversal.DirectoryIterator;
import filehandler.traversal.FileAggregate;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * A utility class providing various file operations and helper methods.
 */
public class FileOperationHelpers {

    /**
     * Retrieves the name of the file without its extension.
     *
     * @param file The File object from which to extract the name.
     * @return The name of the file without the extension.
     */
    public static String getFileName(File file) {
        return getFileNameArray(file)[0];
    }

    /**
     * Retrieves the extension of the file.
     *
     * @param file The File object from which to extract the extension.
     * @return The extension of the file, or an empty string if no extension exists.
     */
    public static String getFileExtension(File file) {
        return getFileNameArray(file)[1];
    }

    /**
     * Splits the file name into its name and extension components.
     *
     * @param file The File object to be processed.
     * @return An array where the first element is the file name and the second is the extension.
     */
    private static String[] getFileNameArray(File file) {
        return file.getName().split("[.]");
    }

    /**
     * Checks if a given file has a valid extension based on a list of allowed extensions.
     *
     * @param file            The File object to check.
     * @param validExtensions A list of valid extensions.
     * @return True if the file has a valid extension, false otherwise.
     */
    public static boolean isValidFileType(File file, List<String> validExtensions) {
        return validExtensions.contains(getFileExtension(file));
    }

    /**
     * Creates directories for a given file path if they do not already exist.
     *
     * @param file        The File object representing the directory to create.
     * @param suppressLog If true, suppresses logging output; otherwise logs success or failure.
     */
    public static void createDirectories(File file, boolean suppressLog) {
        boolean directoryCreated = file.mkdirs();
        if (!suppressLog) {
            if (directoryCreated) {
                System.out.println("Successfully created directory for file: " + file.getName());
            } else {
                System.err.println("Could not create directory for file: " + file.getName());
            }
        }
    }

    /**
     * Retrieves the parent directory path of a given file.
     *
     * @param file The File object whose parent directory path is to be retrieved.
     * @return The path of the parent directory as a String.
     */
    public static String getParentDirectoryPath(File file) {
        return String.valueOf(file.getParentFile());
    }

    /**
     * Creates a DirectoryIterator for traversing files in a specified source directory.
     *
     * @param sourceDirectoryPath The path of the source directory to iterate over.
     * @return A DirectoryIterator for the specified directory.
     * @throws IOException If an I/O error occurs while accessing the directory.
     */
    public static DirectoryIterator createAssignmentIterator(String sourceDirectoryPath) throws IOException {
        DirectoryAggregate assignmentDirectory = new FileAggregate();
        assignmentDirectory.populateList(sourceDirectoryPath);
        return assignmentDirectory.createFileIterator();
    }

    /**
     * Retrieves files from a directory that have a specific extension,
     * excluding files that start with "__".
     *
     * @param directory The directory from which to retrieve files.
     * @param extension The desired file extension (e.g., ".txt").
     * @return An array of Files that match the specified extension, or null if none found.
     */
    public static File[] getDirectoryFilesOfExt(File directory, String extension) {
        return directory.listFiles(
            ((dir, name) -> name.endsWith(extension) && !name.startsWith("__"))
        );
    }

    /**
     * Converts a string path into a File object.
     *
     * @param path The string representation of the path to convert.
     * @return A File object representing the specified path.
     */
    public static File pathToFile(String path) {
        return new File(path);
    }

    /**
     * Recursively deletes all files and subdirectories within a specified directory,
     * and then deletes the directory itself.
     *
     * @param file The directory to delete along with its contents.
     */
    public static void deletePopulatedDirectory(File file) {
        File[] fileContents = file.listFiles();
        if (fileContents != null) {
            for (File f : fileContents) {
                deletePopulatedDirectory(f);
            }
        }
        file.delete();
    }
}
