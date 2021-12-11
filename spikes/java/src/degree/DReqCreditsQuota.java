/**
 * 
 */
package degree;

import course.Course;
import exceptions.ItemNotFoundException;
import general.Plan;
import utils.Args;

/**
 * Complete 31 Oct
 * @author Quaris
 *
 */
public class DReqCreditsQuota implements DegreeReq {

	private DReqCourse[] reqs;
	private int creditsQuota;
	private boolean[] selected;
	private String comment;
	
	public DReqCreditsQuota(DReqCourse[] reqs, int creditsQuota) {
		Args.checkNullArr(reqs, "reqs");
		Args.checkNonNegative(creditsQuota, "creditsQuota");
		this.reqs = reqs;
		this.creditsQuota = creditsQuota;
		selected = new boolean[reqs.length];
	}
	
	// TODO ?
	/*
	public DReqCreditsQuota(Course[] reqs, int creditsQuota) {
		// TODO reqs from raw Courses
		this.creditsQuota = creditsQuota;
		selectionIndices = new int[reqs.length];
	}*/
	
	public DegreeReq[] getOptions() {
		return reqs;
	}
	
	public DegreeReq[] getSelections() {
		int n = 0;
		for (int i = 0; i < reqs.length; i++) {
			if (selected[i]) {
				n++;
			}
		}
		DegreeReq[] selections = new DegreeReq[n];
		int selectionIndex = 0;
		for (int i = 0; i < reqs.length; i++) {
			if (selected[i]) {
				selections[selectionIndex++] = reqs[i];
			}
		}
		return selections;
	}
	
	/**
	 * Does nothing if selection is already selected
	 * Throws exception if selection not found
	 */
	public void select(DegreeReq selection) {
		Args.checkNull(selection, "selection");
		for (int i = 0; i < reqs.length; i++) {
			if (reqs[i] == selection) {
				selected[i] = true;
			}
		}
		throw new ItemNotFoundException();
	}
	
	/**
	 * Does nothing if deselection is already deselected
	 * Throws exception if deselection not found
	 */
	public void deselect(DegreeReq deselection) {
		Args.checkNull(deselection, "deselection");
		for (int i = 0; i < reqs.length; i++) {
			if (reqs[i] == deselection) {
				selected[i] = false;
			}
		}
		throw new ItemNotFoundException();
	}
	
	@Override
	public boolean isSatisfied(Plan plan) {
		Args.checkNull(plan, "plan");
		int totalCredits = 0;
		for (DReqCourse reqCourse : reqs) {
			if (reqCourse.isSatisfied(plan)) {
				totalCredits += reqCourse.getCourse().getCredits();
			}
		}
		return totalCredits >= creditsQuota;
	}

	@Override
	public Course[] getAllCourses() {
		int n = 0;
		for (DegreeReq req : reqs) {
			n += req.getAllCourses().length;
		}
		Course[] allCourses = new Course[n];
		
		// copy arrays
		int i = 0;
		for (DegreeReq req : reqs) {
			for (Course course : req.getAllCourses()) {
				allCourses[i++] = course;
			}
		}
		return allCourses;
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
		StringBuilder s = new StringBuilder();
		s.append("At least ").append(creditsQuota).append(" credits from ( ");
		for (DReqCourse req : reqs) {
			s.append(req.toString()).append(", ");
		}
		s.replace(s.length() - ", ".length(), s.length(), " )");
		return s.toString();
	}


}
