/**
 * 
 */
package course;

import utils.Args;

/**
 * Complete, 26 Oct
 * @author Quaris
 *
 */
public class Course implements Comparable<Course> {
	
	private String id; // e.g. "CS1430"
	private String dept; // e.g. "Computer Science"
	private String fullName; // e.g. "Intro to Programming"
	private int credits;
	private String description;
	private CourseReq reqs;
	private String comment;
	
	public Course(String id, String dept, String fullName, int credits) {
		Args.checkNull(id, "id");
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
		Args.checkNull(id, "id");
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
		Args.checkNull(id, "id");
		Args.checkNull(reqs, "reqs");
		this.id = id;
		this.dept = dept;
		this.fullName = fullName;
		this.credits = credits;
		this.description = null;
		this.reqs = reqs;
		reqs._setRequiredForCourse(this);
		this.comment = null;
	}
	
	public Course(String id, String dept, String fullName, int credits,
			String description, CourseReq reqs) {
		Args.checkNull(id, "id");
		Args.checkNull(reqs, "reqs");
		this.id = id;
		this.dept = dept;
		this.fullName = fullName;
		this.credits = credits;
		this.description = description;
		this.reqs = reqs;
		reqs._setRequiredForCourse(this);
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
	public int hashCode() {
		return (id == null) ? 0 : id.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Course))
			return false;
		Course other = (Course) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public int compareTo(Course c) {
		return this.id.compareTo(c.id);
	}
	
	@Override
	public String toString() {
		return id;
	}

	
}
