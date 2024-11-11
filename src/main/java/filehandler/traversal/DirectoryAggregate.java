package filehandler.traversal;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * The DirectoryAggregate interface defines the structure for classes that manage file traversal within a directory.
 * <p>
 * Implementing classes must provide methods for iterating over files in a directory, populating a list of files
 * from a given directory path, and retrieving the list of files.
 * </p>
 */
public interface DirectoryAggregate {

    /**
     * Creates a DirectoryIterator that can be used to iterate over the files in the directory.
     * 
     * @return A DirectoryIterator object for traversing the files in the directory.
     */
    DirectoryIterator createFileIterator();

    /**
     * Populates a list with files found in the specified directory path.
     * 
     * @param directoryPath The path of the directory to populate the list from.
     * @throws IOException If an I/O error occurs during file reading.
     */
    void populateList(String directoryPath) throws IOException;

    /**
     * Retrieves the list of files in the directory.
     * 
     * @return A list of File objects representing the files in the directory.
     */
    List<File> getDirectoryFiles();
}

