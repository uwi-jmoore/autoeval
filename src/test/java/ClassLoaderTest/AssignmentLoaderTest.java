package ClassLoaderTest;
import static filehandler.filehelperservice.FileOperationHelpers.createAssignmentIterator;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import assignmentevaluator.classloader.AssignmentClassLoader;
import filehandler.traversal.DirectoryIterator;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class AssignmentLoaderTest {
    @Test
    public void testLoadClassFromFile() throws IOException, ClassNotFoundException {
        //
        String classFilePath = "C:\\Users\\Chimera\\Desktop\\Project_Testbed\\Unzipping" +
            "\\extraction_test\\Assignments-main_assign\\Assignment_1\\ChatBot.class";

        AssignmentClassLoader testLoader = new AssignmentClassLoader();
        Class<?> loadedClass = testLoader.loadClassFromFileAlpha(classFilePath);
        assertAll("Testing Loading of class",
            () -> assertEquals(loadedClass.getName(), "ChatBot")
        );


    }


}
