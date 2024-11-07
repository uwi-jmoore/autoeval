package filehandler.traversal;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.List.of;

public class FileAggregate implements DirectoryAggregate{
    private List<File> directoryFiles;

    @Override
    public DirectoryIterator createFileIterator() {
        return new FileIterator(directoryFiles);
    }

    @Override
    public void populateList(String directoryPath) throws IOException {
        File[] files = new File(directoryPath).listFiles();
        if (files == null) { // Check if the path is invalid or not a directory
            throw new IOException("Directory Not Present!");
        }
        directoryFiles = new ArrayList<>(of(files));
    }

    @Override
    public List<File> getDirectoryFiles(){
        return directoryFiles;
    }



}
