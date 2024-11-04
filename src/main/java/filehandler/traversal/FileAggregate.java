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
    private List<String> expectedFiles = new ArrayList<>();

    @Override
    public DirectoryIterator createFileIterator() {
        return new FileIterator(directoryFiles);
    }

    @Override
    public boolean addFileToExpected(String filename){
        expectedFiles.add(filename);
        return expectedFiles.contains(filename);
    }

    @Override
    public boolean removeFileFromExpected(String filename) {
        expectedFiles.remove(filename);
        return !expectedFiles.contains(filename);
    }

    @Override
    public boolean validateFile(File file) {
        Pattern pattern = Pattern.compile(
                String.join("|",expectedFiles),
                Pattern.CASE_INSENSITIVE
        );
        Matcher matcher = pattern.matcher(file.getName());

        return matcher.find();
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

    @Override
    public List<String> getExpectedFiles(){
        return expectedFiles;
    }

}
