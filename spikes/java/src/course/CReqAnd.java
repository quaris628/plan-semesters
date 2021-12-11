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
public class CReqAnd extends CourseReq {

	private CourseReq[] reqs;
	
	public CReqAnd(CourseReq req1, CourseReq req2) {
		Args.checkNull(req1, "req1");
		Args.checkNull(req2, "req2");
		this.reqs = new CourseReq[] { req1, req2 };
	}
	
	public CReqAnd(CourseReq req1, CourseReq req2, CourseReq req3) {
		Args.checkNull(req1, "req1");
		Args.checkNull(req2, "req2");
		Args.checkNull(req3, "req3");
		this.reqs = new CourseReq[] { req1, req2, req3 };
	}
	
	public CReqAnd(CourseReq[] reqs) {
		Args.checkNullArr(reqs, "reqs");
		this.reqs = reqs;
	}
	
	@Override
	public boolean isSatisfied(Plan plan, Semester semesterOfReqFor) {
		Args.checkNull(plan, "plan");
		Args.checkNull(semesterOfReqFor, "semesterOfReqFor");
		for (CourseReq req : reqs) {
			if (!req.isSatisfied(plan, semesterOfReqFor)) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public void _setRequiredForCourse(Course reqFor) {
		Args.checkNull(reqFor, "reqFor");
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
