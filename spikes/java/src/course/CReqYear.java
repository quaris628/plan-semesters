/**
 * 
 */
package course;

import general.Plan;
import semesters.StudentYear;

/**
 * @author Quaris
 *
 */
public class CReqYear implements CourseReq {

	private StudentYear year;
	private String comment;
	
	public CReqYear(StudentYear year) {
		this.year = year;
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
