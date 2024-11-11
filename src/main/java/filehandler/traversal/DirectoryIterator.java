package filehandler.traversal;

import java.io.File;

/**
 * The DirectoryIterator interface defines the methods required for iterating over files in a directory.
 * <p>
 * Implementing classes must provide functionality to iterate through the files, check if there are more files,
 * and retrieve the total number of files.
 * </p>
 */
public interface DirectoryIterator {

    /**
     * Retrieves the next file in the directory traversal.
     * 
     * @return The next File in the directory.
     * @throws NoSuchElementException If there are no more files to iterate through.
     */
    public File next();

    /**
     * Checks if there are more files to iterate through in the directory.
     * 
     * @return true if there are more files to iterate through, false otherwise.
     */
    public boolean hasNext();

    /**
     * Retrieves the total number of files in the directory.
     * 
     * @return The total number of files.
     */
    public int getLength();
}

