package FileIteratorTest;

import filehandler.traversal.DirectoryAggregate;
import filehandler.traversal.FileAggregate;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;



import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
public class AggregateTest {


    @Test
    public void testAddFileToExpected(){
        String expectedFile = "expected";
        DirectoryAggregate testDir = new FileAggregate();
        testDir.addFileToExpected(expectedFile);
        assertTrue(testDir.getExpectedFiles().contains("expected"));

    }

    @Test
    public void testRemoveFileToExpected(){
        String expectedFile = "expected";
        DirectoryAggregate testDir = new FileAggregate();
        testDir.addFileToExpected(expectedFile);
        testDir.removeFileFromExpected("expected");
        assertFalse(testDir.getExpectedFiles().contains("expected"));
    }

    @Test
    public void testValidateFile(){
        String directoryPath = "C:\\Users\\Chimera\\Desktop\\Project_Testbed\\" +
                "Unzipping\\extraction_test\\extraction_src\\ExampleProject1.zip";
        FileAggregate testDir = new FileAggregate();
        String expected = "project";
        testDir.addFileToExpected(expected);
        assertTrue(testDir.validateFile(new File(directoryPath)),
                "Expected filename to have substring: "+ expected);
    }

    @Test
    public void testPopulateList() throws IOException{
        String directoryPath = "C:\\Users\\Chimera\\Desktop\\Project_Testbed\\" +
                "Unzipping\\extraction_test\\extraction_src";
        FileAggregate testDir = new FileAggregate();
        File[] files = new File(directoryPath).listFiles();
        if (files == null) { // Check if the path is invalid or not a directory
            throw new IOException("Directory Not Present!");
        }
        testDir.populateList(directoryPath);
        assertThat(testDir.getDirectoryFiles()).containsExactly(files);
    }
}
