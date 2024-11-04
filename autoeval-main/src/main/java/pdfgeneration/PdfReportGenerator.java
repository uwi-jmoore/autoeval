package pdfgeneration;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.FileOutputStream;
import java.util.List;

public class PdfReportGenerator implements ReportGeneratorInterface {

    @Override
    public void generateReport(List<TestResult> testResults, int totalPoints, String outputFileName) {
        try (PdfWriter writer = new PdfWriter(new FileOutputStream(outputFileName));
             Document document = new Document(new com.itextpdf.kernel.pdf.PdfDocument(writer))) {

            // Add a title to the PDF
            Paragraph title = new Paragraph("JUnit Test Report").setBold().setMarginLeft(250); 
            document.add(title);
            document.add(new Paragraph(" ")); 

            // Add each test result to the PDF
            for (TestResult result : testResults) {
                String status = result.isPassed() ? "PASSED" : "FAILED";
                document.add(new Paragraph(result.getTestName() + ": " + status));
            }

            document.add(new Paragraph(" ")); 
            document.add(new Paragraph("Total Points: " + totalPoints));

            System.out.println("Test report generated: " + outputFileName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

