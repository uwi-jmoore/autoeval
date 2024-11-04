package filehandler.traversal;

import java.io.File;
import java.util.List;

public class FileIterator implements DirectoryIterator{
    private final List<File> studentAssignments;
    int steps = 0;
    public FileIterator(List<File> fileList){
        studentAssignments = fileList;
    }
    @Override
    public File next() {
        if(hasNext()){
            return studentAssignments.get(steps++);
        }
        return null;
    }

    @Override
    public boolean hasNext() {
        return !studentAssignments.isEmpty() && steps < studentAssignments.size();
    }

    @Override
    public int getLength() {
        return studentAssignments.size();
    }
}
