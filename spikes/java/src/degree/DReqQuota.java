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
public class DReqQuota implements DegreeReq {

	private DegreeReq[] reqs;
	private int quota;
	private int[] selectionIndices;
	private String comment;
	
	public DReqQuota(DegreeReq[] reqs, int quota) {
		this.reqs = reqs;
		this.quota = quota; 
		selectionIndices = new int[quota];
	}
	
	public DegreeReq[] getOptions() {
		return reqs;
	}
	
	public DegreeReq getSelections() {
		// TODO
		return null;
	}
	
	public void addSelection(DegreeReq selection) {
		// TODO set selectionIndices
	}
	
	public void removeSelection(DegreeReq selection) {
		// TODO set selectionIndices
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
