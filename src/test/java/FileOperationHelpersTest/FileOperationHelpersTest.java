package FileOperationHelpersTest;

import filehandler.filehelperservice.FileOperationHelpers;

import filehandler.traversal.DirectoryIterator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir; //https://junit.org/junit5/docs/5.4.1/api/org/junit/jupiter/api/io/TempDir.html

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FileOperationHelpersTest {
    @TempDir
    Path tempDir; //temp directory created by junit

    private File testFile;
    private File directory;

    @BeforeEach
    public void setup() throws IOException{
        //creates a temp file and directory for testing
        testFile = Files.createFile(tempDir.resolve("testFile.txt")).toFile();
        directory = Files.createDirectory(tempDir.resolve("newSubDir")).toFile(); //thisll append the txt file and subDir to the tempDIr's path
    }

    @Test
    public void testGetFileName(){
        //verifies that getFileName actually retrieves the file name (w/o the ext)
        Assertions.assertEquals("testFile", FileOperationHelpers.getFileName(testFile));
    }

    @Test
    public void testGetFileExtension(){
        //verifies that getFileExtension actually retrieves the file's ext
        assertEquals("txt", FileOperationHelpers.getFileExtension(testFile));
    }

    @Test
    public void testIsValidFileType(){
        //for testing purposes
        List<String> validExtensions = List.of("txt", "doc");
        assertTrue(FileOperationHelpers.isValidFileType(testFile, validExtensions)); //checks if valid file type (.txt in this case) is identified as such

        //creates an invalid file with a .pdf extension and checks to see if it's identified as invalid
        File invalidFile = tempDir.resolve("testFile.pdf").toFile();
        assertFalse(FileOperationHelpers.isValidFileType(invalidFile, validExtensions));
    }

    @Test
    public void testCreateDirectories(){
        File newDir = tempDir.resolve("newDir").toFile();
        FileOperationHelpers.createDirectories(newDir, false);
        assertTrue(newDir.exists() && newDir.isDirectory());
    }

    @Test
    public void testGetParentDirectoryPath(){
        assertEquals(tempDir.toString(), FileOperationHelpers.getParentDirectoryPath(testFile));
    }

    @Test
    public void testCreateAssignmentIterator() throws IOException{
        DirectoryIterator iterator = FileOperationHelpers.createAssignmentIterator(tempDir.toString());
        assertNotNull(iterator);
        assertTrue(iterator.hasNext());
    }

    @Test
    public void testGetDirectoryFilesOfExt() throws IOException{
        //creates files with different extensions to test if it'll filter it properly
        File txtFile = Files.createFile(directory.toPath().resolve("file.txt")).toFile();
        File docFile = Files.createFile(directory.toPath().resolve("file.doc")).toFile();

        //retrieves only the .txt file from the directory
        File[] txtFiles = FileOperationHelpers.getDirectoryFilesOfExt(directory, ".txt");

        //makes sure that only the .txt file is returned
        assertEquals(1, txtFiles.length);
        assertEquals(txtFile.getName(),txtFiles[0].getName());
    }

    @Test
    public void testPathToFile(){
        //converts the the path to a file obj and checks to see if it matches the temp directory file obj
        File file = FileOperationHelpers.pathToFile(tempDir.toString());
        assertEquals(tempDir.toFile(), file);
    }

    @Test
    public void testDeletePopulateDirectory() throws IOException{
        File subFile = Files.createFile(directory.toPath().resolve("subFile.txt")).toFile();
        FileOperationHelpers.deletePopulatedDirectory(directory);

        //makes sure that both the directory and the file were deleted successfully
        assertFalse(directory.exists());
        assertFalse(subFile.exists());
    }
}
