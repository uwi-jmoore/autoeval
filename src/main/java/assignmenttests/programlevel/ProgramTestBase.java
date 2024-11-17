package assignmenttests.programlevel;

import assignmenttests.AssignmentTest;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static assignmenttests.classlevel.ClassLevelHelpers.findMissingKeys;
import static filehandler.filehelperservice.FileOperationHelpers.getDirectoryFilesOfExt;
import static org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder.request;

public abstract class ProgramTestBase implements AssignmentTest {
    protected static File assignmentDirectory;

    public void setAssignmentDirectory(File assignmentDirectory) {
        ProgramTestBase.assignmentDirectory = assignmentDirectory;
    }

    @Override
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


    public void setUpTestDetails(Map<String, Object> setUpContent) {
        List<String> expectedSetupContents = List.of(
            "assignmentDirectory"
        );
        List<String> missingKeys = findMissingKeys(setUpContent,expectedSetupContents);
        if(missingKeys.isEmpty()){
            setCompileTestDetails(setUpContent);
        }else {
            System.err.println("Missing keys in setup Map: "+missingKeys);
        }
    }

    protected void setCompileTestDetails(Map<String, Object> setUpContent){
        setAssignmentDirectory((File) setUpContent.get("assignmentDirectory"));
    }


    /**
     * Compiles all Java files in a given assignment directory.
     *
     * @return true if compilation is successful, false if compilation fails or if no Java files are found
     */
    protected boolean compileAssignmentActual(){
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            System.err.println("Could Not Compile, JDK missing");
            return false;
        }

        File[] assignmentFiles = getDirectoryFilesOfExt(assignmentDirectory, ".java");
        assert assignmentFiles != null;

        if (assignmentFiles.length == 0) {
            System.err.println("No Java Files found");
            return false;
        }

        int result = compiler.run(null, null, null,
            Arrays.stream(assignmentFiles)
                .map(File::getPath)
                .toArray(String[]::new)
        );

        if (result == 0) {
            return true;
        }

        System.err.println("Compilation failed");
        return false;
    }
}
