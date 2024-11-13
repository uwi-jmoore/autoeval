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
    public void testPopulateList() throws IOException{
        String directoryPath = "C:\\Users\\felix\\Downloads\\Project Src\\ASSIGNMENT 1\\AssignmentTarget";
        FileAggregate testDir = new FileAggregate();
        File[] files = new File(directoryPath).listFiles();
        if (files == null) { // Check if the path is invalid or not a directory
            throw new IOException("Directory Not Present!");
        }
        testDir.populateList(directoryPath);
        assertThat(testDir.getDirectoryFiles()).containsExactly(files);
    }
}
