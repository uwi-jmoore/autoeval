package FileIteratorTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import filehandler.traversal.DirectoryAggregate;
import filehandler.traversal.DirectoryIterator;
import filehandler.traversal.FileAggregate;

/**
 * The IteratorTest class provides unit tests for the {@link DirectoryIterator} interface.
 * These tests validate the functionality of methods within DirectoryIterator for
 * traversing through directory files.
 * <p>
 * The tests include checking if the iterator correctly retrieves the next file, verifies
 * if more files exist, and accurately returns the number of files.
 * </p>
 */
public class IteratorTest {

    /**
     * Tests the {@link DirectoryIterator#next()} method to ensure it retrieves the next
     * file from the iterator without returning null.
     *
     * @throws IOException if an error occurs while initializing the iterator.
     */
    @Test
    public void testNext() throws IOException {
        DirectoryIterator testIter = initIterator();
        assertNotNull(testIter.next(), "Expected a non-null file from the iterator.");
    }

    /**
     * Tests the {@link DirectoryIterator#hasNext()} method to verify that the iterator
     * correctly identifies if there are more files to iterate through.
     *
     * @throws IOException if an error occurs while initializing the iterator.
     */
    @Test
    public void testHasNext() throws IOException {
        DirectoryIterator testIter = initIterator();
        assertTrue(testIter.hasNext(), "Expected hasNext() to return true for a non-empty iterator.");
    }

    /**
     * Tests the {@link DirectoryIterator#getLength()} method to confirm that it returns
     * the correct number of files in the directory.
     * <p>
     * This test assumes there are exactly 2 files in the specified directory.
     * </p>
     *
     * @throws IOException if an error occurs while initializing the iterator.
     */
    @Test
    public void testGetLength() throws IOException {
        DirectoryIterator testIter = initIterator();
        assertEquals(2, testIter.getLength(), "Expected the iterator length to match the number of files.");
    }

    /**
     * Initializes a DirectoryIterator instance for testing by creating and populating
     * a {@link DirectoryAggregate} with files from a specified directory path.
     *
     * @return a DirectoryIterator for traversing the files in the specified directory.
     * @throws IOException if the directory path is invalid or cannot be accessed.
     */
    private static DirectoryIterator initIterator() throws IOException {
        DirectoryAggregate testDir = new FileAggregate();
        String directoryPath = "C:\\Users\\Chimera\\Desktop\\Project_Testbed\\" +
            "Unzipping\\extraction_test\\extraction_src";
        testDir.populateList(directoryPath);
        return testDir.createFileIterator();
    }
}
