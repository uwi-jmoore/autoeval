package filehandler.traversal;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.List.of;

/**
 * Implementation of the DirectoryAggregate interface for aggregating files within a directory.
 */
public class FileAggregate implements DirectoryAggregate {
    private List<File> directoryFiles;

    /**
     * Creates an iterator for traversing files within the directory.
     *
     * @return a DirectoryIterator to iterate over the files
     */
    @Override
    public DirectoryIterator createFileIterator() {
        return new FileIterator(directoryFiles);
    }

    /**
     * Populates the list of files from a given directory path.
     *
     * @param directoryPath the path of the directory to be populated
     * @throws IOException if the directory is not present or the path is invalid
     */
    @Override
    public void populateList(String directoryPath) throws IOException {
        File[] files = new File(directoryPath).listFiles();
        if (files == null) { // Check if the path is invalid or not a directory
            throw new IOException("Directory Not Present!");
        }
        directoryFiles = new ArrayList<>(of(files));
    }

    /**
     * Retrieves the list of files within the directory.
     *
     * @return a list of files in the directory
     */
    @Override
    public List<File> getDirectoryFiles() {
        return directoryFiles;
    }
}
