/**
 * 
 */
package course;

import general.Plan;

/**
 * @author Quaris
 *
 */
public class CPreReq implements CourseReq {

	private Course course;
	private String comment;
	
	public CPreReq(Course course) {
		this.course = course;
	}
	
	@Override
	public boolean isSatisfied(Plan plan, Course course) {
		// TODO Auto-generated method stub
		return false;
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
