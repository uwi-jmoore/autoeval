package assignmenttests.programlevel;

import assignmentevaluator.AssignmentFeedBack;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class AssignmentFilesAllPresent extends AssignmentOperational{

    private List<File> missingFiles = new ArrayList<>();
    private AssignmentFeedBack assignmentFeedBack;
    @Override
    public boolean evaluateProgramLevelTest() {
        File[] assignmentFiles = Objects.requireNonNull(getAssignmentDirectory().listFiles());
        return Arrays
            .stream(assignmentFiles)
            .peek(file -> {
                if(!validateFile(file)){
                    missingFiles.add(file);
                }
            })
            .allMatch(this::validateFile);
    }

    public List<File> getMissingFiles() {
        return missingFiles;
    }

    private boolean validateFile(File file) {
        Pattern pattern = Pattern.compile(
            String.join("|",assignmentFeedBack.getExpectedFiles()),
            Pattern.CASE_INSENSITIVE
        );
        Matcher matcher = pattern.matcher(file.getName());

        return matcher.find();
    }

    public void setAssignmentFeedBack(AssignmentFeedBack assignmentFeedBack) {
        this.assignmentFeedBack = assignmentFeedBack;
    }
}
