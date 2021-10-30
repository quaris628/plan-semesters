/**
 * 
 */
package exceptions;

import semesters.Season;
import semesters.Semester;

/**
 * Complete 30 Oct
 * @author Quaris
 *
 */
public class CoursesNotClearedException extends RuntimeException {

	/**
	 * Auto-generated from an eclipse suggestion. idk what it is.
	 */
	private static final long serialVersionUID = 4072379364008159715L;

	public CoursesNotClearedException() {
		
	}

	public CoursesNotClearedException(Season seasonToDisable, Semester problemSemester) {
		super("Cannot disable " + seasonToDisable.toString() + "because " + problemSemester.toString() + "still contains courses");
	}
	
	public CoursesNotClearedException(String message) {
		super(message);
		// Auto-generated constructor stub
	}

	public CoursesNotClearedException(Throwable cause) {
		super(cause);
		// Auto-generated constructor stub
	}

	public CoursesNotClearedException(String message, Throwable cause) {
		super(message, cause);
		// Auto-generated constructor stub
	}

	public CoursesNotClearedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// Auto-generated constructor stub
	}

}
