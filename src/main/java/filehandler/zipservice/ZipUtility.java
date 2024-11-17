package filehandler.zipservice;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static filehandler.filehelperservice.FileOperationHelpers.*;

/**
 * Utility class for handling ZIP file operations, including extraction.
 */
public class ZipUtility {
    private byte[] fileBuffer;
    private boolean suppressExtractionLogging = true;

    /**
     * Initializes the buffer size for file extraction.
     *
     * @param buffer the size of the buffer in bytes
     */
    public void setFileBuffer(int buffer) {
        if (fileBuffer == null) {
            fileBuffer = new byte[buffer];
        }
    }

    /**
     * Sets the logging behavior for directory creation during extraction.
     *
     * @param suppressExtractionLogging if true, suppresses log output during extraction
     */
    public void suppressExtractionLog(boolean suppressExtractionLogging) {
        this.suppressExtractionLogging = suppressExtractionLogging;
    }

    /**
     * Extracts a file from a ZIP input stream to a specified file location.
     *
     * @param zipInputStream the ZIP input stream containing the file to extract
     * @param newFile        the destination file where the extracted content will be written
     * @throws IOException if an I/O error occurs during extraction
     */
    private void extractFile(ZipInputStream zipInputStream, File newFile) throws IOException {
        createDirectories(new File(newFile.getParent()), suppressExtractionLogging);
        try (FileOutputStream fileOutputStream = new FileOutputStream(newFile)) {
            int length;
            while ((length = zipInputStream.read(fileBuffer)) > 0) {
                fileOutputStream.write(fileBuffer, 0, length);
            }
        } catch (IOException ioException) {
            throw new IOException("Failed to create new FileOutputStream", ioException);
        }
    }

    /**
     * Unzips a ZIP file into a specified container directory, creating directories
     * as needed and handling both file and directory entries within the ZIP file.
     *
     * @param zippedAssignment    the ZIP file to unzip
     * @param assignmentContainer  the directory where contents will be extracted
     */
    public void unzipAssignment(File zippedAssignment, File assignmentContainer) {
        try (FileInputStream fileInputStream = new FileInputStream(zippedAssignment)) {
            ZipInputStream zipInputStream = new ZipInputStream(fileInputStream);
            ZipEntry zipElement;

            // Getting base name for the destination path
            String zipFileName = getFileName(zippedAssignment);
            String destinationPath = assignmentContainer.getAbsolutePath() + File.separator + zipFileName;

            while ((zipElement = zipInputStream.getNextEntry()) != null) {
                File newFile = new File(
                    destinationPath + File.separator + zipElement.getName()
                );

                if (zipElement.isDirectory()) { // Creating subdirectory if element is a directory
                    createDirectories(newFile, suppressExtractionLogging);
                } else { // Extracting if element is a file
                    extractFile(zipInputStream, newFile);
                }
                zipInputStream.closeEntry();
            }
        } catch (FileNotFoundException fileNotFoundException) {
            System.err.println("File not found: " + zippedAssignment.getName());
        } catch (IllegalArgumentException illegalArgumentException) {
            if (!isValidFileType(zippedAssignment, List.of("zip"))) {
                System.err.println("Invalid file type, .zip expected");
                throw new IncorrectFileTypeException("Incorrect file type", illegalArgumentException);
            }
        } catch (IOException ioException) {
            System.err.println("Failed to create inputStream for: " + zippedAssignment);
        }
    }
}
