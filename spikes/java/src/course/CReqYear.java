/**
 * 
 */
package course;

import general.Plan;
import semesters.Semester;
import semesters.StudentYear;

/**
 * Complete 7 Nov
 * @author Quaris
 *
 */
public class CReqYear extends CourseReq {

	private StudentYear year;
	
	public CReqYear(StudentYear year) {
		this.year = year;
	}
	
	@Override
	public boolean isSatisfied(Plan plan, Semester semesterOfReqFor) {
		StudentYear plannedYear = semesterOfReqFor.getStudentYear();
		return plannedYear.compareTo(year) >= 0;
	}
	
	@Override
	public String toString() {
		return year.toString() + " Standing";
	}
}
