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

/**
 * Class to verify that all required files are present in a student's assignment directory.
 * If any expected files are missing, they will be stored in the `missingFiles` list.
 */
public class AssignmentFilesAllPresent extends AssignmentOperational {

    private List<File> missingFiles = new ArrayList<>();
    private AssignmentFeedBack assignmentFeedBack;

    /**
     * Evaluates the program-level test by checking if all required files are present.
     *
     * @return true if all required files are found, false if any are missing
     */
    @Override
    public boolean evaluateProgramLevelTest() {
        File[] assignmentFiles = Objects.requireNonNull(getAssignmentDirectory().listFiles());
        return Arrays
            .stream(assignmentFiles)
            .peek(file -> {
                if (!validateFile(file)) {
                    missingFiles.add(file);
                }
            })
            .allMatch(this::validateFile);
    }

    /**
     * Retrieves a list of files that are missing based on the expected file list.
     *
     * @return a list of missing files
     */
    public List<File> getMissingFiles() {
        return missingFiles;
    }

    /**
     * Validates if a given file matches any of the expected file patterns.
     *
     * @param file the file to validate
     * @return true if the file matches an expected pattern, false otherwise
     */
    private boolean validateFile(File file) {
        Pattern pattern = Pattern.compile(
            String.join("|", assignmentFeedBack.getExpectedFiles()),
            Pattern.CASE_INSENSITIVE
        );
        Matcher matcher = pattern.matcher(file.getName());

        return matcher.find();
    }

    /**
     * Sets the feedback for the assignment, which includes the expected files.
     *
     * @param assignmentFeedBack the feedback containing expected file names
     */
    public void setAssignmentFeedBack(AssignmentFeedBack assignmentFeedBack) {
        this.assignmentFeedBack = assignmentFeedBack;
    }
}
