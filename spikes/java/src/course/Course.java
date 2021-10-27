/**
 * 
 */
package course;

/**
 * Complete, 26 Oct
 * @author Quaris
 *
 */
public class Course {
	
	private String id; // e.g. "CS1430"
	private String dept; // e.g. "Computer Science"
	private String fullName; // e.g. "Intro to Programming"
	private int credits;
	private String description;
	private CourseReq reqs;
	private String comment;
	
	public Course(String id, String dept, String fullName, int credits) {
		this.id = id;
		this.dept = dept;
		this.fullName = fullName;
		this.credits = credits;
		this.description = null;
		this.reqs = null;
		this.comment = null;
	}
	
	public Course(String id, String dept, String fullName, int credits,
			String description) {
		this.id = id;
		this.dept = dept;
		this.fullName = fullName;
		this.credits = credits;
		this.description = description;
		this.reqs = null;
		this.comment = null;
	}
	
	public Course(String id, String dept, String fullName, int credits,
			CourseReq reqs) {
		this.id = id;
		this.dept = dept;
		this.fullName = fullName;
		this.credits = credits;
		this.description = null;
		this.reqs = reqs;
		this.comment = null;
	}
	
	public Course(String id, String dept, String fullName, int credits,
			String description, CourseReq reqs) {
		this.id = id;
		this.dept = dept;
		this.fullName = fullName;
		this.credits = credits;
		this.description = description;
		this.reqs = reqs;
		this.comment = null;
	}
	
	public String getId() {
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
		if (this == o) { return true; }
		if (!(o instanceof Course)) { return false; }
		return this.id.equals(((Course)o).id);
	}
	
	@Override
	public String toString() {
		return id;
	}
	
}
