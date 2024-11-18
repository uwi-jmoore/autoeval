package pdfgeneration;

import java.io.FileOutputStream;
import java.util.List;

//import assignmentevaluator.feedback.TestFeedback;
//import com.itextpdf.kernel.pdf.PdfWriter;
//import com.itextpdf.layout.Document;
//import com.itextpdf.layout.element.Paragraph;
import assignmentevaluator.feedback.TestFeedback;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.*;
import assignmentevaluator.AssignmentFeedBack;

import assignmentevaluator.AssignmentFeedBack;
import com.itextpdf.layout.properties.TextAlignment;

/**
 * The PdfReportGenerator class implements the ReportGeneratorInterface and generates
 * a PDF report containing the results of JUnit tests.
 * <p>
 * This class uses the iText library to create and format the PDF document. The generated
 * report includes a title, individual test results (PASS/FAIL), and total points.
 * </p>
 *
 * @see ReportGeneratorInterface
 */
public class PdfReportGenerator implements ReportGeneratorInterface {

    /**
     * Generates a PDF report with the given test results and total points.
     *
     * @param testResults A list of TestResult objects containing the individual test names
     *                    and their pass/fail status.
     * @param totalPoints The total number of points scored in the test.
     * @param outputFileName The name of the output PDF file to be generated.
     */
    @Override
    public void generateReport(List<TestFeedback> testResults, int totalPoints, String outputFileName, AssignmentFeedBack feedback) {
        try (PdfWriter writer = new PdfWriter(new FileOutputStream(outputFileName));
             PdfDocument pdfDoc = new PdfDocument(writer);
             Document document = new Document(pdfDoc)) {

            // Set document margins
            document.setMargins(36, 36, 36, 36);  // top, right, bottom, left

            // Add a title to the PDF
            Paragraph title = new Paragraph("JUnit Test Report")
                .setBold()
                .setFontSize(18)
                .setTextAlignment(TextAlignment.CENTER);
            document.add(title);
            document.add(new Paragraph(" "));  // Add some space between title and content

            // Add each test result to the PDF
            for (TestFeedback result : testResults) {
                String testFeedBack = result.getFeedbackMsg();
                int marks = result.getTestMarks();
                String testType = result.getTestType();
                String status = marks == 0 ? "FAILED" : "PASSED";
                if(testType == "All Files Present Test" && testFeedBack == "All Files Present"){
                    status = "Passed";
                }
                document.add(new Paragraph(testType + ": " + status)
                    .setFontSize(12)
                    .setTextAlignment(TextAlignment.LEFT));
                if (testFeedBack != null && !testFeedBack.isEmpty()) {
                    document.add(new Paragraph("Feedback: " + testFeedBack)
                        .setFontSize(10)
                        .setItalic()
                        .setTextAlignment(TextAlignment.LEFT));
                }
                document.add(new Paragraph(" "));
            }

            document.add(new Paragraph(" "));
            document.add(new Paragraph("Total Points: " + totalPoints)
                .setFontSize(12)
                .setTextAlignment(TextAlignment.LEFT));

            // Add assignment feedback to the PDF
            document.add(new Paragraph("Assignment Feedback:")
                .setBold()
                .setFontSize(14));
            document.add(new Paragraph("Student ID: " + feedback.getStudentID())
                .setFontSize(12));
            document.add(new Paragraph("Student Name: " + feedback.getStudentName())
                .setFontSize(12));
            document.add(new Paragraph("Assignment Title: " + feedback.getAssignmentTitle())
                .setFontSize(12));
            document.add(new Paragraph("Expected Files: " + String.join(", ", feedback.getExpectedFiles()))
                .setFontSize(12));

            System.out.println("Test report generated: " + outputFileName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    @Override
//    public void generateReport(List<TestFeedback> testResults, int totalPoints, String outputFileName, AssignmentFeedBack feedback) {
//        try (PdfWriter writer = new PdfWriter(new FileOutputStream(outputFileName));
//             Document document = new Document(new com.itextpdf.kernel.pdf.PdfDocument(writer))) {
//
//            // Add a title to the PDF
//            Paragraph title = new Paragraph("JUnit Test Report").setBold().setMarginLeft(250);
//            document.add(title);
//            document.add(new Paragraph(" "));
//
//            // Add each test result to the PDF
//            for (TestFeedback result : testResults) {
////                String status = result.isPassed() ? "PASSED" : "FAILED";
//                String testFeedBack = result.getFeedbackMsg();
//                int marks = result.getTestMarks();
//                String testType =  result.getTestType();
//                String status = marks==0 ? "FAILED" : "PASSED";
//                document.add(new Paragraph(testType + ": " + status));
//            }
//
//            document.add(new Paragraph(" "));
//            document.add(new Paragraph("Total Points: " + totalPoints));
//
//            // Add assignment feedback to the PDF
//            document.add(new Paragraph("Assignment Feedback:"));
//            document.add(new Paragraph("Student ID: " + feedback.getStudentID()));
//            document.add(new Paragraph("Student Name: " + feedback.getStudentName()));
//            document.add(new Paragraph("Assignment Title: " + feedback.getAssignmentTitle()));
//            document.add(new Paragraph("Expected Files: " + String.join(", ", feedback.getExpectedFiles())));
//
//            System.out.println("Test report generated: " + outputFileName);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}


