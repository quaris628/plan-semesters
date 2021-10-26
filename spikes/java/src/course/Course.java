/**
 * 
 */
package course;

/**
 * @author Quaris
 *
 */
public class Course {
	
	private int id;
	private String dept;
	private String fullName;
	private int credits;
	private String description;
	private CourseReq reqs;
	private String comment;
	
	public Course(int id, String dept, String fullName, int credits) {
		// TODO
	}
	
	public Course(int id, String dept, String fullName, int credits,
			String description) {
		// TODO
	}
	
	public Course(int id, String dept, String fullName, int credits,
			CourseReq reqs) {
		// TODO
	}
	
	public Course(int id, String dept, String fullName, int credits,
			String description, CourseReq reqs) {
		// TODO
	}
	
	public int getId() {
		return id;
	}

	public String getDept() {
		return dept;
	}

	public String getFullName() {
		return fullName;
	}

	public String getDescription() {
		return description;
	}

	public int getCredits() {
		return credits;
	}

	public CourseReq getReqs() {
		return reqs;
	}
	
	public void addComment(String comment) {
		this.comment = comment;
	}

	public String getComments() {
		return comment;
	}

	@Override
	public boolean equals(Object o) {
		// TODO
		return false;
	}
	
	@Override
	public String toString() {
		// TODO
		return null;
	}
	
}
