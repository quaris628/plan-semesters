/**
 * 
 */
package course;

import general.Plan;

/**
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
		// TODO Auto-generated method stub
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
		// TODO
		return null;
	}
}
