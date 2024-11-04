package filehandler.traversal;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface DirectoryAggregate {
    public DirectoryIterator createFileIterator();
    public void populateList(String directoryPath) throws IOException;
    public boolean addFileToExpected(String filename);
    public boolean removeFileFromExpected(String filename);
    public boolean validateFile(File file);
    public List<File> getDirectoryFiles();
    public List<String> getExpectedFiles();
}
