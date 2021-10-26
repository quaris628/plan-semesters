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
		// TODO set selectionIndex
	}
	
	public DegreeReq getSelection() {
		return reqs[selectionIndex];
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
