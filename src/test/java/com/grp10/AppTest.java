package com.grp10;

import junit.framework.TestCase;
import java.lang.reflect.Method;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class AppTest extends TestCase {
    
    private static List<TestResult> testResults = new ArrayList<>();
    private static int totalPoints = 0;

    public AppTest(String testName) {
        super(testName);
    }

    @Test
    public void testMainMethodExists() {
        try {
            // Check if the main method exists in the App class
            Method method = App.class.getDeclaredMethod("main", String[].class);
            testResults.add(new TestResult("Main method exists", true));
            totalPoints += 10;
        } catch (NoSuchMethodException e) {
            testResults.add(new TestResult("Main method exists", false));
            fail("Method main() does not exist in App.");
        }
    }

     public void GeneratePdfReport() {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("TestReport.pdf"));
            document.open();

            // Add a title to the PDF
            Paragraph title = new Paragraph("JUnit Test Report");
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(new Paragraph(" ")); 

            // Add each test result to the PDF
            for (TestResult result : testResults) {
                String status = result.isPassed() ? "PASSED" : "FAILED";
                document.add(new Paragraph(result.getTestName() + ": " + status));
            }

            document.add(new Paragraph(" ")); 

            // Add the total points to the PDF
            document.add(new Paragraph("Total Points: " + totalPoints));

            System.out.println("Test report generated: TestReport.pdf");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
        

    } 

   // pdf generation not sure why the above method wasnt doing it on its on 
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        GeneratePdfReport();
    }
    


   

    //  store test results
    static class TestResult {
        private String testName;
        private boolean passed;

        public TestResult(String testName, boolean passed) {
            this.testName = testName;
            this.passed = passed;
        }

        public String getTestName() {
            return testName;
        }

        public boolean isPassed() {
            return passed;
        }
    }
}


