package filehandler.filehelperservice;

import java.io.File;
import java.util.List;

public class Helpers {
    public static String getFileName(File file){
        return getFileNameArray(file)[0];
    }
    public static String getFileExtension(File file){
        return getFileNameArray(file)[1];
    }
    private static String[] getFileNameArray(File file){
        return file.getName().split("[.]");
    }
    public static String getGrandparentDirectoryPath(File file){
        return String.valueOf(file.getParentFile().getParentFile());
    }
    public static boolean isValidFileType(File file, List<String> validExtensions){
        return validExtensions.contains(getFileExtension(file));
    }
    public static void createDirectories(File file){
        System.out.println(file.mkdirs() ? "Successfully created directories" : "Could not create directories");
    }
    public static String getParentDirectoryPath(File file){
        return String.valueOf(file.getParentFile());
    }
}
