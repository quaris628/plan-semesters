/**
 * 
 */
package degree;

import course.Course;
import general.Plan;

/**
 * Complete 31 Oct
 * @author Quaris
 *
 */
public class DReqCourse implements DegreeReq {

	private Course course;
	private String comment;
	
	public DReqCourse(Course course) {
		this.course = course;
	}
	
	public Course getCourse() {
		return course;
	}
	
	@Override
	public boolean isSatisfied(Plan plan) {
		return plan.getSemesterOf(course).getN() >= 0;
	}

	@Override
	public Course[] getAllCourses() {
		return new Course[] { course };
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
		if (comment != null) {
			return course.toString() + ", " + comment;
		}
		return course.toString();
	}

}
