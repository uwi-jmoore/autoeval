package assignmentevaluator.classloader;

import filehandler.traversal.DirectoryIterator;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static filehandler.filehelperservice.FileOperationHelpers.createAssignmentIterator;

public class LoaderService {
    List<File> assignmentFiles;
    List<Class<?>> loadedAssignmentClasses;

    public void setAssignmentFiles(List<File> assignmentFiles) {
        this.assignmentFiles = assignmentFiles;
    }

    public List<Class<?>> getLoadedAssignmentClasses() {
        return loadedAssignmentClasses;
    }

    //assignment directory refers to the path to the Directory of Unzipped assignments
    public void loadAssignmentClasses(String assignmentDirectory) throws IOException {
        DirectoryIterator studentAssignmentIterator = createAssignmentIterator(assignmentDirectory);

    }

}
