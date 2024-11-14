package FileIteratorTest;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

import filehandler.traversal.FileAggregate;

/**
 * The AggregateTest class contains tests for validating the functionality of
 * the FileAggregate class, specifically the population of files from a directory.
 * <p>
 * This test checks that the directory files are accurately listed by comparing
 * the populated list in the FileAggregate instance with the actual files in the directory.
 * </p>
 */
public class AggregateTest {

    /**
     * Tests the {@link FileAggregate#populateList(String)} method to verify that it correctly
     * populates the list of files from the specified directory.
     * <p>
     * This test performs the following steps:
     * <ul>
     *     <li>Checks if the directory exists; throws an IOException if not.</li>
     *     <li>Populates the list of files in the {@link FileAggregate} instance.</li>
     *     <li>Asserts that the populated file list matches the expected files in the directory.</li>
     * </ul>
     * </p>
     *
     * @throws IOException if the directory path is invalid or if the directory does not exist.
     */
    @Test
    public void testPopulateList() throws IOException {
        String directoryPath = "C:\\Users\\felix\\Downloads\\Project Src\\ASSIGNMENT 1\\AssignmentTarget";
        FileAggregate testDir = new FileAggregate();
        File[] files = new File(directoryPath).listFiles();
        
        if (files == null) { // Check if the path is invalid or not a directory
            throw new IOException("Directory Not Present!");
        }
        
        testDir.populateList(directoryPath);
        
        // Verify that the populated file list matches the actual directory contents
        assertThat(testDir.getDirectoryFiles()).containsExactly(files);
    }
}
