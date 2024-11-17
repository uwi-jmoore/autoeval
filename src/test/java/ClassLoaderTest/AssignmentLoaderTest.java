package ClassLoaderTest;
import static org.junit.jupiter.api.Assertions.*;

import assignmentevaluator.classloader.AssignmentClassLoader;

import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.lang.reflect.*;

public class AssignmentLoaderTest {
    @Test
    public void testLoadClassFromFile() throws IOException, ClassNotFoundException {
        //
        String classFilePath = "C:\\Users\\Chimera\\Desktop\\Project_Testbed\\" +
            "Unzipping\\extraction_test\\Assignments-main_assign\\" +
            "Isabella_Augustus_816031354_A1\\Assignment_1\\ChatBot.class";

//        AssignmentClassLoader testLoader = new AssignmentClassLoader();
//        Class<?> loadedClass = testLoader.loadClassFromFile(classFilePath);
//
//        assertAll("Testing Loading of class",
//            () -> assertEquals(loadedClass.getName(), "ChatBot"),
//            () -> assertTrue(Modifier.isPublic(loadedClass.getModifiers())),
//            () -> {
//                Field field = loadedClass.getDeclaredField("chatBotName");
//                assertNotNull(field);
//            }
//
//        );


    }

    @Test
    public void testLoadTest() throws IOException, ClassNotFoundException {
        String testFilePath = "C:\\Users\\Chimera\\Desktop\\METAVAULTS\\" +
            "OMICRON\\Computing\\OOP2\\UWI\\Project\\autoeval" +
            "\\src\\test\\java\\FileIteratorTest\\IteratorTest.java";


//        AssignmentClassLoader testLoader = new AssignmentClassLoader();
//        Class<?> loadedClass = testLoader.loadClassFromFile(testFilePath);
//        assertAll("Testing Loading of class",
//            () -> assertEquals(loadedClass.getName(), "IteratorTest")
//        );
    }


}
