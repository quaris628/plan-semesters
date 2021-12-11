/**
 * 
 */
package degree;

import course.Course;
import utils.Args;

/**
 * @author Quaris
 *
 */
public class Degree {
	private DegreeReq reqs;
	private String name;
	
	public Degree(String name, DegreeReq reqs) {
		Args.checkNull(name, "name");
		Args.checkNull(reqs, "reqs");
		this.name = name;
		this.reqs = reqs;
	}
	
	public DegreeReq getReqirements() {
		return reqs;
	}
	
	public Course[] getAllCourses() {
		return reqs.getAllCourses();
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}
