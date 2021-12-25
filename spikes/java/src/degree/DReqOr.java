/**
 * 
 */
package degree;

import course.Course;
import general.Plan;
import utils.Args;

/**
 * Complete 31 Oct
 * @author Quaris
 *
 */
public class DReqOr extends DegreeReq {

	private DegreeReq[] reqs;
	private int selectionIndex;
	
	public DReqOr(DegreeReq req1, DegreeReq req2) {
		Args.checkNull(req1, "req1");
		Args.checkNull(req2, "req2");
		this.reqs = new DegreeReq[] { req1, req2 };
		selectionIndex = -1;
	}
	
	public DReqOr(DegreeReq req1, DegreeReq req2, DegreeReq req3) {
		Args.checkNull(req1, "req1");
		Args.checkNull(req2, "req2");
		Args.checkNull(req3, "req3");
		this.reqs = new DegreeReq[] { req1, req2, req3 };
		selectionIndex = -1;
	}
	
	public DReqOr(DegreeReq[] reqs) {
		Args.checkNullArr(reqs, "reqs");
		this.reqs = reqs;
		selectionIndex = -1;
	}
	
	public DegreeReq[] getOptions() {
		return reqs;
	}
	
	public void setSelection(DegreeReq choice) {
		Args.checkNull(choice, "choice");
		for (int i = 0; i < reqs.length; i++) {
			if (reqs[i] == choice) {
				selectionIndex = i;
				return;
			}
		}
	}
	
	public DegreeReq getSelection() {
		return reqs[selectionIndex];
	}
	
	@Override
	public boolean isSatisfied(Plan plan) {
		Args.checkNull(plan, "plan");
		for (DegreeReq req : reqs) {
			if (req.isSatisfied(plan)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Course[] getAllCourses() {
		if (selectionIndex < 0) {
			return null;
		}
		return reqs[selectionIndex].getAllCourses();
	}
	
	@Override
	public String toString() {
		// e.g. "( PreReq:CS1430 or CoReq:CS2130 )" 
		StringBuilder s = new StringBuilder("( ");
		for (DegreeReq req : reqs) {
			s.append(req.toString());
			s.append(" or ");
		}
		s.replace(s.length() - " or ".length(), s.length(), " )");
		return s.toString();
	}

}
