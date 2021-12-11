/**
 * 
 */
package exceptions;

import semesters.Season;

/**
 * Complete 30 Oct
 * @author Quaris
 *
 */
public class LastEnabledSeasonException extends RuntimeException {
	
	public static final String DEFAULT_MESSAGE =
			"The last enabled season cannot be disabled";
	
	private static final long serialVersionUID = -8486158378951796288L;

	public LastEnabledSeasonException() {
		super(DEFAULT_MESSAGE);
	}
	
	public LastEnabledSeasonException(Season season) {
		super(DEFAULT_MESSAGE + " (" + season.toString() + ")");
	}
	
	public LastEnabledSeasonException(String message) {
		super(message);
	}

	public LastEnabledSeasonException(Throwable cause) {
		super(cause);
		// Auto-generated constructor stub
	}

	public LastEnabledSeasonException(String message, Throwable cause) {
		super(message, cause);
		// Auto-generated constructor stub
	}

	public LastEnabledSeasonException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// Auto-generated constructor stub
	}
}
