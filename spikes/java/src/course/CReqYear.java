/**
 * 
 */
package course;

import general.Plan;
import semesters.StudentYear;

/**
 * Complete 26 Oct
 * @author Quaris
 *
 */
public class CReqYear implements CourseReq {

	private StudentYear year;
	private String comment;
	
	public CReqYear(StudentYear year) {
		this.year = year;
	}
	
	@Override
	public boolean isSatisfied(Plan plan, Course course) {
		StudentYear plannedYear = plan.getSemesterOf(course).getStudentYear();
		return plannedYear.compareTo(year) >= 0;
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
		return year.toString() + " Standing";
	}
}
