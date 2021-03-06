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
public class DReqOr implements DegreeReq {

	private DegreeReq[] reqs;
	private int selectionIndex;
	private String comment;
	
	public DReqOr(DegreeReq req1, DegreeReq req2) {
		this.reqs = new DegreeReq[] { req1, req2 };
		selectionIndex = -1;
	}
	
	public DReqOr(DegreeReq req1, DegreeReq req2, DegreeReq req3) {
		this.reqs = new DegreeReq[] { req1, req2, req3 };
		selectionIndex = -1;
	}
	
	public DReqOr(DegreeReq[] reqs) {
		this.reqs = reqs;
		selectionIndex = -1;
	}
	
	public DegreeReq[] getOptions() {
		return reqs;
	}
	
	public void setSelection(DegreeReq choice) {
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
	public void addComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String getComment() {
		return comment;
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
