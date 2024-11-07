package filehandler.traversal;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface DirectoryAggregate {
    DirectoryIterator createFileIterator();
    void populateList(String directoryPath) throws IOException;
    List<File> getDirectoryFiles();

}
