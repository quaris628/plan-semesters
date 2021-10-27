/**
 * 
 */
package semesters;

import java.util.LinkedList;

import course.Course;

/**
 * @author Quaris
 *
 */
public class Semester implements Comparable<Semester> {
	
	// Sort of meant to be an enum, but with dynamic options
	// Currently have a manually linked list implementation, may change later
	
	private static final int UNPLANNED_INDEX = -2;
	public static final Semester UNPLANNED = new Semester(UNPLANNED_INDEX);
	public static final Semester SATISFIED = new Semester(-1);
	public static final Semester FIRST = new Semester(0);
	
	private int n;
	private Semester next; // TODO maybe add previous
	private Season season;
	private YearStatus year;
	private LinkedList<Course> courses;
	
	private Semester(int n) {
		this.n = n;
		this.next = null;
		this.season = null;
		this.courses = new LinkedList<Course>();
	}
	
	private Semester(int n, Season season) {
		this.n = n;
		this.next = null;
		this.season = season;
		this.courses = new LinkedList<Course>();
	}
	
	public static void initializeFirst(Season season, YearStatus year) {
		FIRST.season = season;
		FIRST.year = year;
		// iterate through all semesters and update their seasons
		Semester s = FIRST;
		while (s.hasNext()) {
			s.next.season = s.season.getNext();
			s.next.year = s.year.getStatusNextSemester();
			s = s.next;
		}
	}
	
	public Semester getNext() {
		if (!hasNext() && n >= 0) {
			next = new Semester(n + 1, season.getNext());
		}
		return next;
	}
	
	public boolean hasNext() {
		return next == null;
	}
	
	public Season getSeason() {
		return season;
	}
	
	public StudentYear getStudentYear() {
		return year.GetStudentYear();
	}
	
	// course management
	public void addCourse(Course course) {
		courses.add(course);
	}
	
	public void removeCourse(Course course) {
		courses.remove(course);
	}
	
	public Course[] getCourses() {
		return courses.toArray(new Course[courses.size()]);
	}
	
	public int getCredits() {
		// TODO
		return 0;
	}
	
	/**
	 * @return for s1.compareTo(s2),
	 * 		    1 if s1 > s2
	 * 			0 if s1 == s2
	 * 		   -1 if s1 < s2
	 */
	@Override
	public int compareTo(Semester s) {
		if (this.n == UNPLANNED_INDEX) { return -1; } // so prereq checks work well
		else if (this.n > s.n) { return 1; }
		else if (this.n == s.n) { return 0; }
		else { return -1; }
	}
	
	@Override
	public String toString() {
		// TODO
		return null;
	}
	
}
