/**
 * 
 */
package exceptions;

/**
 * @author Quaris
 *
 */
public class ItemNotFoundException extends RuntimeException {

	public static final String DEFAULT_MESSAGE =
			"Item was not found in the collection";
	
	private static final long serialVersionUID = -4588462940924950776L;

	/**
	 * Uses the default message
	 */
	public ItemNotFoundException() {
		super(DEFAULT_MESSAGE);
	}

	/**
	 * @param message
	 */
	public ItemNotFoundException(String message) {
		super(message);
		// Auto-generated constructor stub
	}

	/**
	 * Uses the default message
	 * @param cause
	 */
	public ItemNotFoundException(Throwable cause) {
		super(DEFAULT_MESSAGE, cause);
		// Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ItemNotFoundException(String message, Throwable cause) {
		super(message, cause);
		// Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public ItemNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// Auto-generated constructor stub
	}

}
