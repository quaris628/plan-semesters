/**
 * 
 */
package course;

import general.Plan;

/**
 * @author Quaris
 *
 */
public interface CourseReq {
	boolean isSatisfied(Plan plan, Course course);
	void addComment(String comment);
	String getComment();
}
