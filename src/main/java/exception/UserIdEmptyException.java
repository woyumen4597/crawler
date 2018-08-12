package exception;

public class UserIdEmptyException extends Exception {
    private static final long serialVersionUID = 1L;

    public UserIdEmptyException() {
    }

    public UserIdEmptyException(String message) {
        super(message);
    }
}
