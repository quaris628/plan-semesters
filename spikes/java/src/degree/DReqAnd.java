/**
 * 
 */
package degree;

import course.Course;
import general.Plan;

/**
 * @author Quaris
 *
 */
public class DReqAnd implements DegreeReq {

	private DegreeReq[] reqs;
	private String comment;
	
	public DReqAnd(DegreeReq req1, DegreeReq req2) {
		this.reqs = new DegreeReq[] { req1, req2 };
	}
	
	public DReqAnd(DegreeReq req1, DegreeReq req2, DegreeReq req3) {
		this.reqs = new DegreeReq[] { req1, req2, req3 };
	}
	
	public DReqAnd(DegreeReq[] reqs) {
		this.reqs = reqs;
	}
	
	
	@Override
	public boolean isSatisfied(Plan plan) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Course[] getAllCourses() {
		// TODO Auto-generated method stub
		return null;
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
