/**
 * 
 */
package course;

import general.Plan;
import semesters.SemesterList;

/**
 * Complete, 26 Oct
 * @author Quaris
 *
 */
public class CCoReq implements CourseReq {
	
	private Course course;
	private String comment;
	
	public CCoReq(Course course) {
		this.course = course;
	}
	
	@Override
	public boolean isSatisfied(Plan plan, Course course) {
		SemesterList unlockedCourseSemester = plan.getSemesterOf(course);
		SemesterList coReqSemester = plan.getSemesterOf(this.course);
		return coReqSemester.compareTo(unlockedCourseSemester) <= 0;
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
		return "CoReq:" + course.toString();
	}
	
}
