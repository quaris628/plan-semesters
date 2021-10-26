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
public class DReqCreditsQuota implements DegreeReq {

	private DReqCourse[] reqs;
	private int creditsQuota;
	private int[] selectionIndices;
	private String comment;
	
	public DReqCreditsQuota(DReqCourse[] reqs, int creditsQuota) {
		this.reqs = reqs;
		this.creditsQuota = creditsQuota;
		selectionIndices = new int[reqs.length];
	}
	
	public DReqCreditsQuota(Course[] reqs, int creditsQuota) {
		// TODO reqs from raw Courses
		this.creditsQuota = creditsQuota;
		selectionIndices = new int[reqs.length];
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
