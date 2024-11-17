package assignmenttests.classlevel;

import assignmentevaluator.classloader.AssignmentClassLoader;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;

import java.io.File;


import static filehandler.filehelperservice.FileOperationHelpers.getFileNameFromPathString;
import static org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder.request;

public abstract class ClassTestBase implements ClassTest{
    protected static Class<?> loadedClass;
    protected static AssignmentClassLoader testLoader;

    protected static String classFilePath;


    public void setClassFilePath(String classFilePath) {
        ClassTestBase.classFilePath = classFilePath;
    }


    protected void classTestBaseSetUp(){
        ClassTestBase.testLoader = new AssignmentClassLoader(new File(classFilePath).getParentFile());
        String className = getFileNameFromPathString(classFilePath);
        try {
            ClassTestBase.loadedClass = testLoader.loadClass(className);
        } catch (ClassNotFoundException classNotFoundException) {
            System.err.println("ClassNotFoundException occurred while trying to load class: "
                + className
                + ".Reason: "
                + classNotFoundException.getMessage()
            );
        }
    }

    public SummaryGeneratingListener executeTest(Class<?> targetTestClass){
        Launcher launcher = LauncherFactory.create();
        SummaryGeneratingListener listener = new SummaryGeneratingListener();
        LauncherDiscoveryRequest request = request()
            .selectors(
                DiscoverySelectors.selectClass(targetTestClass)
            )
            .build();
        launcher.execute(request, listener);
        return listener;
    }
    protected Class<?> primitiveToWrapper(Class<?> primitive){
        return switch (primitive.getName()){
            case "boolean" -> Boolean.class;
            case "byte" ->Byte.class;
            case "char" -> Character.class;
            case "double" -> Double.class;
            case "float" -> Float.class;
            case "int" -> Integer.class;
            case "long" -> Long.class;
            case "short" -> Short.class;
            default -> primitive;
        };
    }
}
