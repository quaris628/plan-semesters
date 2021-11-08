/**
 * 
 */
package semesters;

import java.util.Arrays;
import java.util.LinkedList;

import course.Course;

/**
 * Complete 29 Oct
 * @author Quaris
 *
 */
public class Semester implements Comparable<Semester> {

	private SemesterList container;
	
	private int n;
	private Season season;
	private StudentYear studentYear;
	private LinkedList<Course> courses;
	private int totalCredits;
	
	public Semester(SemesterList container, int n, Season season, StudentYear studentYear) {
		this.container = container;
		this.n = n;
		this.setSeason(season);
		this.setStudentYear(studentYear);
		this.courses = new LinkedList<Course>();
		this.totalCredits = 0;
	}

	public Season getSeason() {
		return season;
	}

	public void setSeason(Season season) {
		this.season = season;
	}

	public StudentYear getStudentYear() {
		return studentYear;
	}

	public void setStudentYear(StudentYear studentYear) {
		this.studentYear = studentYear;
	}
	
	/**
	 * Does not add course if already present in this semester
	 * @param course
	 */
	public void addCourse(Course course) {
		if (!courses.contains(course)) {
			if (courses.isEmpty() && container != null
					&& this != container.getUnplanned() 
					&& this != container.getSatisfied()) {
				container.getSemesterAfter(this);
			}
			courses.add(course);
			totalCredits += course.getCredits();
			if (container != null) {
				container.refreshYears();
				
			}
		}
	}
	
	public void removeCourse(Course course) {
		courses.remove(course);
		totalCredits -= course.getCredits();
		if (container != null) {
			container.refreshYears();
		}
	}
	
	public boolean hasCourse(Course course) {
		return courses.contains(course);
	}
	
	public Course[] getCourses() {
		// deep copy
		Course[] courseArr = courses.toArray(new Course[courses.size()]);
		Arrays.sort(courseArr);
		return courseArr;
	}
	
	public int getTotalCredits() {
		return totalCredits;
	}
	
	public int getN() {
		return n;
	}
	
	public String getName() {
		if (this == container.getUnplanned()) {
			return "Unplanned";
		} else if (this == container.getSatisfied()) {
			return "Satisfied";
		} else if (season == null) {
			return "Unknown " + String.valueOf(n);
		} else {
			return season.toString() + " " + String.valueOf(n) + " - " + studentYear.toString();
		}
	}
	
	@Override
	public int compareTo(Semester semester) {
		return this.n - semester.n;
	}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(this.getName()).append("\n");
		for (Course course : courses) {
			s.append("  ").append(course.toString()).append('\n');
		}
		s.append("  Credits: ").append(this.getTotalCredits()).append('\n');
		return s.toString();
	}

}
