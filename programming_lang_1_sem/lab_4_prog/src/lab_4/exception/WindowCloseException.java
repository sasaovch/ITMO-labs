package lab_4.exception;

public class WindowCloseException extends RuntimeException {
    public WindowCloseException(String message){
        super(message);
    }
}
