/**
 * 
 */
package degree;

import course.Course;
import general.Plan;

/**
 * @author Quaris
 *
 */
public abstract class DegreeReq {

	private String comment;
	
	public abstract boolean isSatisfied(Plan plan);
	// TODO getAllCourses, addComment, and getComment are
	//      often duplicated in other DegreeReqs.
	//      Move these to a superclass?
	public abstract Course[] getAllCourses();
	
	public void addComment(String comment) {
		this.comment = comment;
	}
	
	public String getComment() {
		return comment;
	}
}
