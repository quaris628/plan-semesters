/**
 * 
 */
package degree;

import course.Course;

/**
 * @author Quaris
 *
 */
public class Degree {
	private DegreeReq reqs;
	private String name;
	
	public Degree(String name, DegreeReq reqs) {
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
