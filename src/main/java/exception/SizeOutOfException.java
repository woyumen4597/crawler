package exception;

public class SizeOutOfException extends Exception{
	private static final long serialVersionUID = 1L;

	public SizeOutOfException() {
		super();
	}

	public SizeOutOfException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public SizeOutOfException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public SizeOutOfException(String message) {
		super(message);
	}

	public SizeOutOfException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
