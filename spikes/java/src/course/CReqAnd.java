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
public class CReqAnd extends CourseReq {

	private CourseReq[] reqs;
	
	public CReqAnd(CourseReq req1, CourseReq req2) {
		this.reqs = new CourseReq[] { req1, req2 };
	}
	
	public CReqAnd(CourseReq req1, CourseReq req2, CourseReq req3) {
		this.reqs = new CourseReq[] { req1, req2, req3 };
	}
	
	public CReqAnd(CourseReq[] reqs) {
		this.reqs = reqs;
	}
	
	@Override
	public boolean isSatisfied(Plan plan, Semester semesterOfReqFor) {
		for (CourseReq req : reqs) {
			if (!req.isSatisfied(plan, semesterOfReqFor)) {
				return false;
			}
		}
		return true;
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
		// e.g. "( PreReq:CS1430 and CoReq:CS2130 and ( PreReq:Math1050 or PreReq:Math2340 ) )" 
		StringBuilder toReturn = new StringBuilder("( ");
		for (CourseReq req : reqs) {
			toReturn.append(req.toString());
			toReturn.append(" and ");
		}
		toReturn.replace(toReturn.length() - " and ".length(), toReturn.length(), " )");
		return toReturn.toString();
	}
}
