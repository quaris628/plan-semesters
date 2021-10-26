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
	Course[] getAllCourses();
	void addComment(String comment);
	String getComment();
}
