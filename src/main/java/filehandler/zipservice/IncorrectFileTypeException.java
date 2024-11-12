package filehandler.zipservice;

/**
 * Custom exception for handling incorrect file type errors during file processing.
 * Extends {@link IllegalArgumentException} to provide additional context when an
 * invalid file type is encountered.
 */
public class IncorrectFileTypeException extends IllegalArgumentException {

    /**
     * Constructs an {@code IncorrectFileTypeException} with the specified detail message and cause.
     *
     * @param errorMessage the detail message, saved for later retrieval by the {@link Throwable#getMessage()} method
     * @param error the cause of the exception, saved for later retrieval by the {@link Throwable#getCause()} method
     */
    public IncorrectFileTypeException(String errorMessage, Throwable error) {
        super(errorMessage, error);
    }
}
