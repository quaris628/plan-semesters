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
public interface DegreeReq {

	boolean isSatisfied(Plan plan);
	// TODO getAllCourses, addComment, and getComment are
	//      often duplicated in other DegreeReqs.
	//      Move these to a superclass?
	Course[] getAllCourses();
	void addComment(String comment);
	String getComment();
}
