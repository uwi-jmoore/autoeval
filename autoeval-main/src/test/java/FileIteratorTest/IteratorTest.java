package FileIteratorTest;

import filehandler.traversal.DirectoryAggregate;
import filehandler.traversal.DirectoryIterator;
import filehandler.traversal.FileAggregate;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class IteratorTest {
    @Test
    public void testNext() throws IOException {

        DirectoryIterator testIter = initIterator();
        assertNotNull(testIter.next());
    }

    @Test
    public void testHasNext() throws IOException {
        DirectoryIterator testIter = initIterator();
        assertTrue(testIter.hasNext());
    }
    @Test
    public void testGetLength() throws IOException{
        DirectoryIterator testIter = initIterator();
        assertEquals(2, testIter.getLength());
    }
    private static DirectoryIterator initIterator() throws IOException {
        DirectoryAggregate testDir = new FileAggregate();

        String directoryPath = "C:\\Users\\Chimera\\Desktop\\Project_Testbed\\" +
            "Unzipping\\extraction_test\\extraction_src";
        testDir.populateList(directoryPath);
        return testDir.createFileIterator();
    }
}
