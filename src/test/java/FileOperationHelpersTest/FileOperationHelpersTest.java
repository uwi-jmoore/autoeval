package FileOperationHelpersTest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Assertions; //https://junit.org/junit5/docs/5.4.1/api/org/junit/jupiter/api/io/TempDir.html
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import filehandler.filehelperservice.FileOperationHelpers;
import filehandler.traversal.DirectoryIterator;

/**
 * The FileOperationHelpersTest class contains unit tests for methods in the
 * {@link FileOperationHelpers} utility class. These tests cover various file
 * operations such as file and directory creation, file name and extension retrieval,
 * and directory filtering based on file extensions.
 */
public class FileOperationHelpersTest {

    /**
     * Temporary directory created by JUnit for testing purposes. All temporary files and
     * directories are created within this path.
     */
    @TempDir
    Path tempDir;

    private File testFile;
    private File directory;

    /**
     * Sets up a temporary test file and directory before each test is executed.
     *
     * @throws IOException if an error occurs during file or directory creation.
     */
    @BeforeEach
    public void setup() throws IOException {
        testFile = Files.createFile(tempDir.resolve("testFile.txt")).toFile();
        directory = Files.createDirectory(tempDir.resolve("newSubDir")).toFile();
    }

    /**
     * Tests {@link FileOperationHelpers#getFileName(File)} to verify that the correct
     * file name is returned without the extension.
     */
    @Test
    public void testGetFileName() {
        Assertions.assertEquals("testFile", FileOperationHelpers.getFileName(testFile));
    }

    /**
     * Tests {@link FileOperationHelpers#getFileExtension(File)} to verify that the correct
     * file extension is retrieved from the file.
     */
    @Test
    public void testGetFileExtension() {
        assertEquals("txt", FileOperationHelpers.getFileExtension(testFile));
    }

    /**
     * Tests {@link FileOperationHelpers#isValidFileType(File, List)} to verify that the
     * method correctly identifies valid file extensions and invalid file extensions.
     */
    @Test
    public void testIsValidFileType() {
        List<String> validExtensions = List.of("txt", "doc");
        assertTrue(FileOperationHelpers.isValidFileType(testFile, validExtensions));

        File invalidFile = tempDir.resolve("testFile.pdf").toFile();
        assertFalse(FileOperationHelpers.isValidFileType(invalidFile, validExtensions));
    }

    /**
     * Tests {@link FileOperationHelpers#createDirectories(File, boolean)} to verify
     * that a directory is successfully created.
     */
    @Test
    public void testCreateDirectories() {
        File newDir = tempDir.resolve("newDir").toFile();
        FileOperationHelpers.createDirectories(newDir, false);
        assertTrue(newDir.exists() && newDir.isDirectory());
    }

    /**
     * Tests {@link FileOperationHelpers#getParentDirectoryPath(File)} to verify that
     * the correct parent directory path is returned for a given file.
     */
    @Test
    public void testGetParentDirectoryPath() {
        assertEquals(tempDir.toString(), FileOperationHelpers.getParentDirectoryPath(testFile));
    }

    /**
     * Tests {@link FileOperationHelpers#createAssignmentIterator(String)} to ensure
     * that the method creates a non-null DirectoryIterator and verifies it contains files.
     *
     * @throws IOException if an error occurs during the iterator setup.
     */
    @Test
    public void testCreateAssignmentIterator() throws IOException {
        DirectoryIterator iterator = FileOperationHelpers.createAssignmentIterator(tempDir.toString());
        assertNotNull(iterator);
        assertTrue(iterator.hasNext());
    }

    /**
     * Tests {@link FileOperationHelpers#getDirectoryFilesOfExt(File, String)} to ensure
     * only files with the specified extension are retrieved from a directory.
     *
     * @throws IOException if an error occurs while creating the test files.
     */
    @Test
    public void testGetDirectoryFilesOfExt() throws IOException {
        File txtFile = Files.createFile(directory.toPath().resolve("file.txt")).toFile();
        File docFile = Files.createFile(directory.toPath().resolve("file.doc")).toFile();

        File[] txtFiles = FileOperationHelpers.getDirectoryFilesOfExt(directory, ".txt");

        assertEquals(1, txtFiles.length);
        assertEquals(txtFile.getName(), txtFiles[0].getName());
    }

    /**
     * Tests {@link FileOperationHelpers#pathToFile(String)} to verify that a path is
     * correctly converted to a File object.
     */
    @Test
    public void testPathToFile() {
        File file = FileOperationHelpers.pathToFile(tempDir.toString());
        assertEquals(tempDir.toFile(), file);
    }

    /**
     * Tests {@link FileOperationHelpers#deletePopulatedDirectory(File)} to verify that a
     * directory and its contents are deleted successfully.
     *
     * @throws IOException if an error occurs during file or directory deletion.
     */
    @Test
    public void testDeletePopulateDirectory() throws IOException {
        File subFile = Files.createFile(directory.toPath().resolve("subFile.txt")).toFile();
        FileOperationHelpers.deletePopulatedDirectory(directory);

        assertFalse(directory.exists());
        assertFalse(subFile.exists());
    }
}
