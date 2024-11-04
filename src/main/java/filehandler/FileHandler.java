package filehandler;

import filehandler.traversal.DirectoryAggregate;
import filehandler.traversal.DirectoryIterator;
import filehandler.traversal.FileAggregate;
import filehandler.zipservice.ZipUtility;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;


import static filehandler.filehelperservice.Helpers.getFileName;
import static filehandler.filehelperservice.Helpers.getParentDirectoryPath;


public class FileHandler {
    public void extractAssignments(String assignmentDirectoryPath) throws IOException {
        int fileSize = 8192;//expected max size of student assignments in bytes
        ZipUtility zipUtility = new ZipUtility(fileSize);
        zipUtility.setFileBuffer(fileSize);
        File containerDirectory = generateContainerDirectory(assignmentDirectoryPath);
        if(containerDirectory != null){
            DirectoryIterator zippedAssignmentsIterator = createZippedAssignmentsIterator(assignmentDirectoryPath);
            while (zippedAssignmentsIterator.hasNext()){
                File extractionTarget = zippedAssignmentsIterator.next();
                zipUtility.unzipAssignment(extractionTarget,containerDirectory);
            }
        }
    }

    private DirectoryIterator createZippedAssignmentsIterator(String sourceDirectoryPath) throws IOException {
        DirectoryAggregate fileAggregate = new FileAggregate();
        fileAggregate.populateList(sourceDirectoryPath);
        return fileAggregate.createFileIterator();
    }
    private File generateContainerDirectory(String sourceDirectoryPath){
        File assignments = new File(sourceDirectoryPath);
        File containerDirectory;
        String targetPath = getParentDirectoryPath(assignments) +
            File.separator +
            "Assignments-" +
            getFileName(assignments);
        boolean containerCreated;
        try{
            Files.deleteIfExists(Paths.get(targetPath));
        }catch (NoSuchFileException e){
            System.out.println("No Duplicates detected");
        }catch (IOException ioException){
            System.out.println("Invalid Permissions");
        }finally {
            containerDirectory = new File(targetPath);
            containerCreated = containerDirectory.mkdirs();
        }
        return containerCreated ? containerDirectory : null;
    }
}
