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
public class CPreReq extends CourseReq {

	private Course preReqCourse;
	
	public CPreReq(Course preReqCourse) {
		this.preReqCourse = preReqCourse;
	}
	
	@Override
	public boolean isSatisfied(Plan plan, Semester semesterOfReqFor) {
		Semester coReqSemester = plan.getSemesterOf(this.preReqCourse);
		return coReqSemester.compareTo(semesterOfReqFor) < 0;
	}

	@Override
	public String toString() {
		return "PreReq:" + preReqCourse.toString();
	}
}
