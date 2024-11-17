package filehandler;

import filehandler.traversal.DirectoryIterator;
import filehandler.zipservice.ZipUtility;

import java.io.File;
import java.nio.file.*;
import static filehandler.filehelperservice.FileOperationHelpers.*;

/**
 * A utility class for handling file-related operations, specifically for extracting
 * assignment submissions from zipped files and managing directory structures.
 */
public class FileHandler {

    /**
     * Extracts all assignment zip files from the specified directory into a new container directory.
     *
     * @param assignmentDirectoryPath the path to the directory containing zipped assignment files
     * @return the container directory where assignments are extracted, or null if directory creation fails
     * @throws IOException if an I/O error occurs while creating or accessing files
     */
    public File extractAssignments(String assignmentDirectoryPath) throws IOException {
        int fileSize = 8192; // expected max size of student assignments in bytes
        ZipUtility zipUtility = new ZipUtility();
        zipUtility.setFileBuffer(fileSize);
        zipUtility.suppressExtractionLog(true);

        File containerDirectory = generateContainerDirectory(assignmentDirectoryPath);
        if (containerDirectory != null) {
            DirectoryIterator zippedAssignmentsIterator = createZippedAssignmentsIterator(assignmentDirectoryPath);
            while (zippedAssignmentsIterator.hasNext()) {
                File zippedAssignment = zippedAssignmentsIterator.next();
                zipUtility.unzipAssignment(zippedAssignment, containerDirectory);
            }
        }
        return containerDirectory;
    }

    /**
     * Creates an iterator for iterating through all zipped assignment files in a directory.
     *
     * @param sourceDirectoryPath the path to the directory containing the zipped files
     * @return a {@link DirectoryIterator} for zipped assignment files
     * @throws IOException if an I/O error occurs while accessing files
     */
    private DirectoryIterator createZippedAssignmentsIterator(String sourceDirectoryPath) throws IOException {
        return createAssignmentIterator(sourceDirectoryPath);
    }

    /**
     * Generates a container directory for extracted assignments in the parent directory of the source directory.
     * Deletes any existing directory with the same name before creation.
     *
     * @param sourceDirectoryPath the path to the directory containing assignments
     * @return the newly created container directory, or null if directory creation fails
     */
    private File generateContainerDirectory(String sourceDirectoryPath) {
        File assignments = new File(sourceDirectoryPath);
        File containerDirectory;
        String targetPath = getParentDirectoryPath(assignments) +
            File.separator +
            "Assignments-" +
            getFileName(assignments);

        if (Files.exists(Paths.get(targetPath))) {
            deletePopulatedDirectory(pathToFile(targetPath));
        }

        containerDirectory = new File(targetPath);
        boolean containerCreated = containerDirectory.mkdirs();
        if (!containerCreated) {
            System.err.println("Failed to create directory: " + targetPath);
        }

        return containerCreated ? containerDirectory : null;
    }
}
