/**
 * 
 */
package semesters;

import java.util.LinkedList;
import java.util.List;

import course.Course;
import utils.Args;

/**
 * Complete 11 Nov
 * @author Quaris
 *
 */
public class Semester implements Comparable<Semester> {

	private int year;
	private Season season;
	private int priorCumCredits;
	private int numCredits;
	private final List<Course> courses;
	
	/**
	 * Creates a new semester 
	 * @param year, such as 2000
	 * @param season
	 * @param studentYear
	 * @param priorCumCredits, cumulative credits planned to earn prior to this semester
	 */
	public Semester(int year, Season season, int priorCumCredits) {
		Args.checkNonNegative(year, "year");
		Args.checkNull(season, "season");
		this.year = year;
		this.season = season;
		this.priorCumCredits = priorCumCredits;
		this.numCredits = 0;
		this.courses = new LinkedList<Course>();
	}

	public int getYear() {
		return year;
	}
	
	public void setYear(int year) {
		Args.checkNonNegative(year, "year");
		this.year = year;
	}
	
	public Season getSeason() {
		return season;
	}
	
	public void setSeason(Season season) {
		Args.checkNull(season, "season");
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
	
	/**
	 * Get number of credits of courses contained in this semester
	 * Does not include the prior cumulative sum of credits 
	 * @return
	 */
	public int getCredits() {
		return numCredits;
	}
	
	/**
	 * @return total cumulative credits, including credits this semester
	 */
	public int getTotalCumulativeCredits() {
		return priorCumCredits + numCredits;
	}
	
	public void add(Course course) {
		Args.checkNull(course, "course");
		if (!courses.contains(course)) {
			courses.add(course);
			numCredits += course.getCredits();
		}
	}
	
	public void remove(Object course) {
		Args.checkNull(course, "course");
		if (!(course instanceof Course)) {
			throw new IllegalArgumentException("Cannot remove non-courses");
		}
		Course courseToRemove = (Course)course;
		if (courses.contains(courseToRemove)) {
			courses.remove(courseToRemove);
			numCredits -= courseToRemove.getCredits();
		}
	}
	
	public void clearCourses() {
		courses.clear();
	}
	
	public Iterable<Course> getCourses() {
		return courses;
	}
	
	@Override
	public int compareTo(Semester semester) {
		Args.checkNull(semester, "semester");
		return this.year * 4 + this.season.ordinal() - (semester.year * 4 + semester.season.ordinal());
	}
	
	public String getName() {
		StringBuilder s = new StringBuilder();
		s.append(year > 0 ? year : "-").append(" ");
		s.append(season != null ? season : "-");
		return s.toString();
	}
	
	public String toPrint() {
		StringBuilder s = new StringBuilder();
		s.append(getName()).append(", ").append(getStudentYear()).append('\n');
		for (Course course : courses) {
			s.append("  ").append(course.toString()).append('\n');
		}
		s.append("  Credits: ").append(numCredits).append('\n');
		return s.toString();
	}
	
	@Override
	public String toString() {
		return getName();
	}

}
