package assignmenttests.programlevel;

import assignmentevaluator.evaluationHelpers.AssignmentRunner;
import assignmenttests.AssignmentTest;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;

import java.io.File;
import java.util.List;
import java.util.Map;

import static assignmenttests.classlevel.ClassLevelHelpers.findMissingKeys;
import static org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder.request;

public abstract class ProgramTestBase implements AssignmentTest {
    protected static File assignmentDirectory;
    protected AssignmentRunner runner;
    public ProgramTestBase(){
        runner = new AssignmentRunner();
    }

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
        runner.setRunnerAssignmentDirectory(assignmentDirectory);
    }


    /**
     * Compiles all Java files in a given assignment directory.
     *
     * @return true if compilation is successful, false if compilation fails or if no Java files are found
     */
    protected boolean compileAssignmentTester(){
        return runner.compileAssignment();
    }
}
