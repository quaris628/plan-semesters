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
	
	// TODO
	// constructor tbd -- should control construction so only
	// certain pre-determined degrees may be chosen?
	
	public DegreeReq getReqirements() {
		return reqs;
	}
	
	// may not need?
	public Course[] getAllCourses() {
		// TODO
		return null;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		// TODO
		return null;
	}
	
}
