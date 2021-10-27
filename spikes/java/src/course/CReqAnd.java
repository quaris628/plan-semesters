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
public class CReqAnd implements CourseReq {

	private CourseReq[] reqs;
	private String comment;
	
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
	public boolean isSatisfied(Plan plan, Course course) {
		for (CourseReq req : reqs) {
			if (!req.isSatisfied(plan, course)) {
				return false;
			}
		}
		return true;
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
