package assignmentevaluator;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AssignmentFeedBack {
    private List<TestFeedback> testResults;
    private List<String> expectedFiles;

    private String studentID;
    private String studentName;
    private String assignmentTitle;


    //Expected Files
    public void addFileToExpected(String filename){
        expectedFiles.add(filename);
    }

    public void removeFileFromExpected(String filename) {
        expectedFiles.remove(filename);
    }

    public List<String> getExpectedFiles(){
        return expectedFiles;
    }

    public boolean validateFile(File file) {
        Pattern pattern = Pattern.compile(
            String.join("|",expectedFiles),
            Pattern.CASE_INSENSITIVE
        );
        Matcher matcher = pattern.matcher(file.getName());

        return matcher.find();
    }


    //Getters
    public String getStudentID(){
        return studentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getAssignmentTitle() {
        return assignmentTitle;
    }

    public List<TestFeedback> getTestResults() {
        return testResults;
    }


    //Setters and adder
    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setAssignmentTitle(String assignmentTitle) {
        this.assignmentTitle = assignmentTitle;
    }

    public void addTestResults(TestFeedback result){
        testResults.add(result);
    }
}
