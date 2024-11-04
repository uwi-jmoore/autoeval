package filehandler.zipservice;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static filehandler.filehelperservice.Helpers.*;

public class ZipUtility {
    private byte[] fileBuffer;
    public ZipUtility(int bufferSize){
        fileBuffer = new byte[bufferSize];
    }
    public void setFileBuffer(int buffer){
        if (fileBuffer == null){
            fileBuffer = new byte[buffer];
        }
    }

    private void extractFile(ZipInputStream zipInputStream, File newFile) throws IOException{
        createDirectories(new File(newFile.getParent()));
        try (FileOutputStream fileOutputStream = new FileOutputStream(newFile)) {
            int length;
            while ((length = zipInputStream.read(fileBuffer)) > 0) {
                fileOutputStream.write(fileBuffer, 0, length);
            }
        }catch (IOException ioException){
            throw new IOException("Failed to create new FileOutputStream");
        }
    }


    /**
     * Method that unzips a given File object obtained by the FileIterator
    **/
    public void unzipAssignment(File zippedAssignment, File assignmentContainer){
        try(FileInputStream fileInputStream = new FileInputStream(zippedAssignment)){
            ZipInputStream zipInputStream = new ZipInputStream(fileInputStream);
            ZipEntry zipElement;
            String destinationPath = assignmentContainer.getAbsolutePath();
            while((zipElement = zipInputStream.getNextEntry()) != null){
                File newFile = new File(
                    destinationPath
                        + File.separator
                        + zipElement.getName()
                );
                if(zipElement.isDirectory()){//creating subdirectory if zip element is a directory
                    createDirectories(newFile);
                }else{//extracting if zip element is a file
                    extractFile(zipInputStream,newFile);
                }
                zipInputStream.closeEntry();
            }
        }catch (FileNotFoundException fileNotFoundException){
            System.out.println("File not found");
        }catch (IllegalArgumentException illegalArgumentException){
            if(!isValidFileType(zippedAssignment,List.of(".zip"))){
                System.out.println("Invalid File type, .zip expected");
                throw new IncorrectFileTypeException("Incorrect Filetype",illegalArgumentException);
            }
        }catch (IOException ioException){
            //fill in with more robust Exception logging?
            System.out.println("Failed to create inputStream for: "+ zippedAssignment);
        }
    }

}
