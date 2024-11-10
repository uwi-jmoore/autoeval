package assignmentevaluator.evaluationHelpers;

import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.TestPlan;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static filehandler.filehelperservice.FileOperationHelpers.getDirectoryFilesOfExt;
import static filehandler.filehelperservice.FileOperationHelpers.getFileName;
import static org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder.request;

public class EvalHelpers {
    public static File[] getAssignmentFiles(File studentAssignmentDirectory, String type){
        return getDirectoryFilesOfExt(studentAssignmentDirectory,type);
    }

    public static File getFile(File studentAssignmentDirectory, String fileName, String fileType){
        File[] assignmentJavaFiles = getAssignmentFiles(studentAssignmentDirectory,fileType);
        for(File f: assignmentJavaFiles){
            if(Objects.equals(getFileName(f), fileName)){
                return f;
            }
        }
        return null;
    }
    public static Map<String,String> createAttributeTestSetupMap(String attrName, String dataType, String hasDefault,String defaultVal, String isFinal, String isStatic){
        Map<String, String> map = new HashMap<>();
        map.put("attributeName",attrName);
        map.put("dataType",dataType);
        map.put("hasDefault",hasDefault);
        map.put("defaultValue",defaultVal);
        map.put("isFinal",isFinal);
        map.put("isStatic",isStatic);
        return map;
    }

    public static SummaryGeneratingListener getTestReturn(Class<?> targetClass){
        Launcher launcher = LauncherFactory.create();
        SummaryGeneratingListener listener = new SummaryGeneratingListener();

        LauncherDiscoveryRequest request = request()
            .selectors(
                DiscoverySelectors.selectClass(targetClass)
            )
            .build();

        //for debugging
        TestPlan testPlan = launcher.discover(request);

        System.out.println("Discovered Tests: " + testPlan.getRoots().size());
        for (TestIdentifier testIdentifier : testPlan.getRoots()) {
            System.out.println("Root Identifier: " + testIdentifier.getDisplayName());
            for (TestIdentifier child : testPlan.getChildren(testIdentifier)) {
                System.out.println("Child Identifier: " + child.getDisplayName());
                for(TestIdentifier grandChild : testPlan.getChildren(child)){
                    System.out.println("GrandChild Identifier: " + grandChild.getDisplayName());
                }
            }
        }

        // Execute the tests
        launcher.execute(request, listener);
        return listener;
    }
}
