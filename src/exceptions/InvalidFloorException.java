package exceptions;

public class InvalidFloorException extends Exception {

    public InvalidFloorException() {
        super();
    }

    public InvalidFloorException(String message) {
        super(message);
    }

    public InvalidFloorException(String message, Throwable cause) {
        super(message, cause);
    }
}
