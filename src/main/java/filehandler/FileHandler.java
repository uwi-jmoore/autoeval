package filehandler;


import filehandler.traversal.DirectoryIterator;

import filehandler.zipservice.ZipUtility;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.rmi.AccessException;

import static filehandler.filehelperservice.FileOperationHelpers.*;


public class FileHandler {
    public File extractAssignments(String assignmentDirectoryPath) throws IOException {
        int fileSize = 8192;//expected max size of student assignments in bytes
        ZipUtility zipUtility = new ZipUtility();
        zipUtility.setFileBuffer(fileSize);
        zipUtility.suppressExtractionLog(true);

        File containerDirectory = generateContainerDirectory(assignmentDirectoryPath);
        if(containerDirectory != null){
            DirectoryIterator zippedAssignmentsIterator = createZippedAssignmentsIterator(assignmentDirectoryPath);
            while (zippedAssignmentsIterator.hasNext()){
                File extractionTarget = zippedAssignmentsIterator.next();
                zipUtility.unzipAssignment(extractionTarget,containerDirectory);
            }
        }
        return containerDirectory;
    }

    private DirectoryIterator createZippedAssignmentsIterator(String sourceDirectoryPath) throws IOException {
        return  createAssignmentIterator(sourceDirectoryPath);
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
        }
        catch (NoSuchFileException e){
            System.out.println("No duplicates detected: " + e.getMessage());
        }
        catch(DirectoryNotEmptyException notEmptyException){
            deletePopulatedDirectory(pathToFile(targetPath));
        }

        catch (FileSystemException fileSystemException){
            System.err.println("FileSystemException occurred when trying to delete existing duplicate directory: "
                + fileSystemException.getMessage());
            System.err.println("Directory currently locked by another process");
        }
        catch (AccessException accessException){
            System.err.println("AccessException occurred when trying to delete existing duplicate directory: "
                + accessException.getMessage());
            System.err.println("Invalid File Permissions");
        }
        catch (IOException ioException){
            System.err.println("IOException occurred when trying to delete existing duplicate directory: "
                + ioException.getMessage());
        }
        finally {
            containerDirectory = new File(targetPath);
            containerCreated = containerDirectory.mkdirs();
            if (!containerCreated) {
                System.err.println("Failed to create directory: " + targetPath);
            }
        }
        return containerCreated ? containerDirectory : null;
    }
}
