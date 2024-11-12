package filehandler;


import filehandler.traversal.DirectoryIterator;

import filehandler.zipservice.ZipUtility;

import java.io.File;
import java.nio.file.*;

import static filehandler.filehelperservice.FileOperationHelpers.*;


public class FileHandler {
    public File extractAssignments(String assignmentDirectoryPath){
        int fileSize = 8192;//expected max size of student assignments in bytes
        ZipUtility zipUtility = new ZipUtility();
        zipUtility.setFileBuffer(fileSize);
        zipUtility.suppressExtractionLog(true);

        File containerDirectory = generateContainerDirectory(assignmentDirectoryPath);
        if(containerDirectory != null){
            DirectoryIterator zippedAssignmentsIterator = createZippedAssignmentsIterator(assignmentDirectoryPath);
            while (zippedAssignmentsIterator.hasNext()){
                File zippedAssignment = zippedAssignmentsIterator.next();
                zipUtility.unzipAssignment(zippedAssignment,containerDirectory);
            }
        }
        return containerDirectory;
    }

    private DirectoryIterator createZippedAssignmentsIterator(String sourceDirectoryPath){
        return createAssignmentIterator(sourceDirectoryPath);
    }
    private File generateContainerDirectory(String sourceDirectoryPath){
        File assignments = new File(sourceDirectoryPath);
        File containerDirectory;
        String targetPath = getParentDirectoryPath(assignments) +
            File.separator +
            "Assignments-" +
            getFileName(assignments);
        boolean containerCreated;
        if(Files.exists(Paths.get(targetPath))){
            deletePopulatedDirectory(pathToFile(targetPath));
            containerDirectory = new File(targetPath);
                containerCreated = containerDirectory.mkdirs();
                if (!containerCreated) {
                    System.err.println("Failed to create directory: " + targetPath);
                }
        }else {
            containerDirectory = new File(targetPath);
            containerCreated = containerDirectory.mkdirs();
            if (!containerCreated) {
                System.err.println("Failed to create directory: " + targetPath);
            }
        }
        return containerCreated ? containerDirectory : null;
    }

}
