/**
 * 
 */
package degree;


import course.Course;
import general.Plan;
import utils.Args;

/**
 * Complete 31 Oct
 * @author Quaris
 *
 */
public class DReqAnd extends DegreeReq {

	private DegreeReq[] reqs;
	
	public DReqAnd(DegreeReq req1, DegreeReq req2) {
		Args.checkNull(req1, "req1");
		Args.checkNull(req2, "req2");
		this.reqs = new DegreeReq[] { req1, req2 };
	}
	
	public DReqAnd(DegreeReq req1, DegreeReq req2, DegreeReq req3) {
		Args.checkNull(req1, "req1");
		Args.checkNull(req2, "req2");
		Args.checkNull(req3, "req3");
		this.reqs = new DegreeReq[] { req1, req2, req3 };
	}
	
	public DReqAnd(DegreeReq[] reqs) {
		Args.checkNullArr(reqs, "reqs");
		this.reqs = reqs;
	}
	
	@Override
	public boolean isSatisfied(Plan plan) {
		Args.checkNull(plan, "plan");
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
