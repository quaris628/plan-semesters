/**
 * 
 */
package semesters;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import course.Course;

/**
 * Complete 11 Nov
 * @author Quaris
 *
 */
public class Semester implements Comparable<Semester>, Iterable<Course> {

	private int year;
	private Season season;
	private int priorCumCredits;
	private int totalCredits;
	private final List<Course> courses;
	
	/**
	 * Creates a new semester 
	 * @param year, such as 2000
	 * @param season
	 * @param studentYear
	 * @param priorCumCredits, cumulative credits planned to earn prior to this semester
	 */
	public Semester(int year, Season season, int priorCumCredits) {
		this.year = year;
		this.season = season;
		this.priorCumCredits = priorCumCredits;
		this.totalCredits = 0;
		this.courses = new LinkedList<Course>();
	}

	public int getYear() {
		return year;
	}
	
	public void setYear(int year) {
		if (year < 0) {
			throw new IllegalArgumentException("Invalid Year " + String.valueOf(year));
		}
		this.year = year;
	}
	
	public Season getSeason() {
		return season;
	}
	
	public void setSeason(Season season) {
		if (season == null) {
			throw new IllegalArgumentException("season cannot be null");
		}
		this.season = season;
	}
	
	/**
	 * @return prior cumulative credits, not including credits this semester
	 */
	public int getPriorCumulativeCredits() {
		return priorCumCredits;
	}
	
	public void setPriorCumulativeCredits(int priorCumCredits) {
		this.priorCumCredits = priorCumCredits;
	}
	
	public StudentYear getStudentYear() {
		return StudentYear.getStudentYear(priorCumCredits);
	}
	
	public int getTotalCredits() {
        return totalCredits;
	}
	
	/**
	 * @return total cumulative credits, including credits this semester
	 */
	public int getTotalCumulativeCredits() {
		return priorCumCredits + totalCredits;
	}
	
	public void add(Course course) {
		if (course == null) {
			throw new IllegalArgumentException("Cannot add a null course");
		}
		if (!courses.contains(course)) {
			courses.add(course);
			totalCredits += course.getCredits();
		}
	}
	
	public void remove(Object course) {
		if (course == null) {
			throw new IllegalArgumentException("Cannot remove a null course");
		}
		if (!(course instanceof Course)) {
			throw new IllegalArgumentException("Cannot remove non-courses");
		}
		Course courseToRemove = (Course)course;
		if (courses.contains(courseToRemove)) {
			courses.remove(courseToRemove);
			totalCredits -= courseToRemove.getCredits();
		}
	}
	
	public void clearCourses() {
		courses.clear();
	}
	
	public Iterator<Course> iterator() {
		return courses.iterator();
	}
	
	@Override
	public int compareTo(Semester semester) {
		return this.year * 4 + this.season.ordinal() - (semester.year * 4 + semester.season.ordinal());
	}
	
	public String getName() {
		StringBuilder s = new StringBuilder();
		s.append(season != null ? season : "-").append(" ");
		s.append(year > 0 ? year : "-");
		return s.toString();
	}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(this.getName()).append('\n');
		for (Course course : courses) {
			s.append("  ").append(course.toString()).append('\n');
		}
		s.append("  Credits: ").append(totalCredits).append('\n');
		return s.toString();
	}

}
