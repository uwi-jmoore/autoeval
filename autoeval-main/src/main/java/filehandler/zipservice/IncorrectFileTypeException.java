package filehandler.zipservice;

public class IncorrectFileTypeException extends IllegalArgumentException{
    public IncorrectFileTypeException(String errorMessage, Throwable error){
        super(errorMessage, error);
    }
}
