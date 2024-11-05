package UnzipFileTest;

import filehandler.zipservice.ZipUtility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.util.Arrays;

import static filehandler.filehelperservice.Helpers.getFileName;
import static org.junit.jupiter.api.Assertions.*;
public class ZipUtilityTest {
    @ParameterizedTest
    @ValueSource(strings = {
        "C:\\Users\\Chimera\\Desktop\\Project_Testbed" + "\\Unzipping\\extraction_test\\extraction_src\\ExampleProject1.zip"
    })
    public void testUnzipAssignment(String assignmentPath){
        String desktopPath = System.getProperty("user.home") + File.separator +"Desktop";
        File testAssignment= new File(assignmentPath);
        File outputDirectory = new File(
            desktopPath
            + File.separator
            + "unzipTest"
        );
        int fileSize = 8192;//expected max size of student assignments in bytes
        ZipUtility zipUtility = new ZipUtility(fileSize);
        zipUtility.setFileBuffer(fileSize);
        zipUtility.unzipAssignment(testAssignment,outputDirectory);
        assertAll("Testing ZipUtility",
            ()->assertTrue(outputDirectory.exists()),
            ()->assertTrue(findFile(outputDirectory.listFiles(),testAssignment))
        );

    }
    private boolean findFile(File[] fileList, File target){
        return Arrays.stream(fileList)
            .anyMatch(file -> file.isDirectory() && file.getName().equals(getFileName(target)));
    }
}
