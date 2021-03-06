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
public class CReqOr extends CourseReq {

	private CourseReq[] reqs;
	
	public CReqOr(CourseReq req1, CourseReq req2) {
		this.reqs = new CourseReq[] { req1, req2 };
	}
	
	public CReqOr(CourseReq req1, CourseReq req2, CourseReq req3) {
		this.reqs = new CourseReq[] { req1, req2, req3 };
	}
	
	public CReqOr(CourseReq[] reqs) {
		this.reqs = reqs;
	}
	
	@Override
	public boolean isSatisfied(Plan plan, Semester semesterOfReqFor) {
		for (CourseReq req : reqs) {
			if (req.isSatisfied(plan, semesterOfReqFor)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void _setRequiredForCourse(Course reqFor) {
		super._setRequiredForCourse(reqFor);
		for (CourseReq req : reqs) {
			req._setRequiredForCourse(reqFor);
		}
	}
	
	@Override
	public String toString() {
		// e.g. "( PreReq:CS1430 or CoReq:CS2130 )" 
		StringBuilder toReturn = new StringBuilder("( ");
		for (CourseReq req : reqs) {
			toReturn.append(req.toString());
			toReturn.append(" or ");
		}
		toReturn.replace(toReturn.length() - " or ".length(), toReturn.length(), " )");
		return toReturn.toString();
	}
}
