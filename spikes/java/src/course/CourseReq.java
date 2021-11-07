/**
 * 
 */
package course;

import general.Plan;
import semesters.Semester;

/**
 * Complete 7 Nov
 * @author Quaris
 *
 */
public abstract class CourseReq {
	
	private String comment;
	private Course reqFor;
	
	protected CourseReq() {
		this.comment = null;
	}
	
	public abstract boolean isSatisfied(Plan plan, Semester semester);
	
	/**
	 * Should only be used by Course constructor, then immutable after that
	 * @param reqFor
	 */
	public void _setRequiredForCourse(Course reqFor) {
		this.reqFor = reqFor;
	}
	
	public Course getRequiredForCourse() {
		return reqFor;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public String getComment() {
		return comment;
	}
}
