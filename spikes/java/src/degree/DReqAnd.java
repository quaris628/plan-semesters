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
		for (DegreeReq req : reqs) {
			if (!req.isSatisfied(plan)) {
				return false;
			}
		}
		return true;
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
		StringBuilder s = new StringBuilder("( ");
		for (DegreeReq req : reqs) {
			s.append(req.toString());
			s.append(" and ");
		}
		s.replace(s.length() - " and ".length(), s.length(), " )");
		return s.toString();
		
	}
}
