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
public class DReqCourse implements DegreeReq {

	private Course course;
	private String comment;
	
	public DReqCourse(Course course) {
		this.course = course;
	}
	
	@Override
	public boolean isSatisfied(Plan plan) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Course[] getAllCourses() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String getComment() {
		return comment;
	}
	
	@Override
	public String toString() {
		// TODO
		return null;
	}

}
