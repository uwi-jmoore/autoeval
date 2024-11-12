package filehandler.filehelperservice;

import filehandler.traversal.DirectoryAggregate;
import filehandler.traversal.DirectoryIterator;
import filehandler.traversal.FileAggregate;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileOperationHelpers {
    public static String getFileName(File file){
        return getFileNameArray(file)[0];
    }
    public static String getFileExtension(File file){
        return getFileNameArray(file)[1];
    }
    private static String[] getFileNameArray(File file){
        return file.getName().split("[.]");
    }

    public static boolean isValidFileType(File file, List<String> validExtensions){
        return validExtensions.contains(getFileExtension(file));
    }

    public static void createDirectories(File file, boolean supressLog){
        boolean directoryCreated = file.mkdirs();
        if(!supressLog){
            if(directoryCreated){
                System.out.println("Successfully created directory for file: " + file.getName());
            }else{
                System.err.println("Could not create directory for file: "+ file.getName());
            }
        }
    }

    public static String getParentDirectoryPath(File file){
        return String.valueOf(file.getParentFile());
    }

    public static DirectoryIterator createAssignmentIterator(String sourceDirectoryPath){
        DirectoryAggregate assignmentDirectory = new FileAggregate();
        try {
            assignmentDirectory.populateList(sourceDirectoryPath);
        } catch (IOException e) {
            System.err.println("Failed to Populate List as Directory Not Present or is Empty. " +
                "Exception Message: "+ e.getMessage());
            throw new RuntimeException(e);

        }
        return assignmentDirectory.createFileIterator();

    }
    /*
    * Added condition to not include
    * */
    public static File[] getDirectoryFilesOfExt(File directory,String extension){
        return directory.listFiles(
            ((dir, name) -> name.endsWith(extension) && !name.startsWith("__"))
        );
    }

    public static File pathToFile(String path){
        return new File(path);
    }

    public static void deletePopulatedDirectory(File file){
        File[] fileContents = file.listFiles();
        if(fileContents != null){
            for(File f: fileContents){
                deletePopulatedDirectory(f);
            }
        }
        file.delete();
    }

    public static String getFileNameFromPathString(String filePath){
        String[] parts = filePath.split("[\\\\/]");
        String name = parts[parts.length - 1];
        return name.split("[.]")[0];
    }
}
