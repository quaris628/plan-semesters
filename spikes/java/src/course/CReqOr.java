/**
 * 
 */
package course;

import general.Plan;

/**
 * Complete 26 Oct
 * @author Quaris
 *
 */
public class CReqOr implements CourseReq {

	private CourseReq[] reqs;
	private String comment;
	
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
	public boolean isSatisfied(Plan plan, Course course) {
		for (CourseReq req : reqs) {
			if (req.isSatisfied(plan, course)) {
				return true;
			}
		}
		return false;
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
		StringBuilder toReturn = new StringBuilder("( ");
		for (CourseReq req : reqs) {
			toReturn.append(req.toString());
			toReturn.append(" or ");
		}
		toReturn.replace(toReturn.length() - " or ".length(), toReturn.length(), " )");
		return toReturn.toString();
	}
}
