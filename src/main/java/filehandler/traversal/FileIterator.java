package filehandler.traversal;

import java.io.File;
import java.util.List;

/**
 * Iterator implementation for traversing a list of files, representing student assignments.
 */
public class FileIterator implements DirectoryIterator {
    private final List<File> studentAssignments;
    private int steps = 0;

    /**
     * Constructs a FileIterator for the specified list of files.
     *
     * @param fileList the list of files to iterate over
     */
    public FileIterator(List<File> fileList) {
        studentAssignments = fileList;
    }

    /**
     * Retrieves the next file in the list.
     *
     * @return the next File object, or null if no more files are available
     */
    @Override
    public File next() {
        if (hasNext()) {
            return studentAssignments.get(steps++);
        }
        return null;
    }

    /**
     * Checks if there are more files to iterate over.
     *
     * @return true if there is a next file, false otherwise
     */
    @Override
    public boolean hasNext() {
        return !studentAssignments.isEmpty() && steps < studentAssignments.size();
    }

    /**
     * Gets the total number of files in the list.
     *
     * @return the number of files in the list
     */
    @Override
    public int getLength() {
        return studentAssignments.size();
    }
}
