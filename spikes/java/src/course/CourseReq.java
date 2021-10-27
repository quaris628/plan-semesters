/**
 * 
 */
package course;

import general.Plan;

/**
 * Complete
 * @author Quaris
 *
 */
public interface CourseReq {
	boolean isSatisfied(Plan plan, Course course);
	void addComment(String comment);
	String getComment();
}
