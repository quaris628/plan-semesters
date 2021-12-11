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
public class DReqQuota implements DegreeReq {

	private DegreeReq[] reqs;
	private int quota;
	private boolean[] selected;
	private String comment;
	
	public DReqQuota(DegreeReq[] reqs, int quota) {
		Args.checkNullArr(reqs, "reqs");
		Args.checkNonNegative(quota, "quota");
		if (quota >= reqs.length) {
			throw new IllegalArgumentException("quota is unattainably large");
		}
		this.reqs = reqs;
		this.quota = quota; 
		selected = new boolean[reqs.length];
	}
	
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
	
	public void select(DegreeReq selection) {
		Args.checkNull(selection, "selection");
		for (int i = 0; i < reqs.length; i++) {
			if (reqs[i] == selection) {
				selected[i] = true;
			}
		}
		throw new ItemNotFoundException();
	}
	
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
		int totalSubReqsSatisfied = 0;
		for (DegreeReq req : reqs) {
			if (req.isSatisfied(plan)) {
				totalSubReqsSatisfied++;
			}
		}
		return totalSubReqsSatisfied >= quota;
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
		s.append("At least ").append(quota).append(" of ( ");
		for (DegreeReq req : reqs) {
			s.append(req.toString()).append(", ");
		}
		s.replace(s.length() - ", ".length(), s.length(), " )");
		return s.toString();
	}

}
