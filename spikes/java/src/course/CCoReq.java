/**
 * 
 */
package course;

import general.Plan;
import semesters.Semester;
import utils.Args;

/**
 * Complete 7 Nov
 * @author Quaris
 *
 */
public class CCoReq extends CourseReq {
	
	private Course coReqCourse;
	
	public CCoReq(Course coReqCourse) {
		Args.checkNull(coReqCourse, "coReqCourse");
		this.coReqCourse = coReqCourse;
	}
	
	@Override
	public boolean isSatisfied(Plan plan, Semester semesterOfReqFor) {
		Semester coReqSemester = plan.getSemesterOf(this.coReqCourse);
		return coReqSemester.compareTo(semesterOfReqFor) <= 0;
	}

	@Override
	public String toString() {
		return "CoReq:" + coReqCourse.toString();
	}
	
}
