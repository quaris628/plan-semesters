/**
 * 
 */
package semesters;

import java.util.LinkedList;

import course.Course;

/**
 * Complete 29 Oct
 * @author Quaris
 *
 */
public class Semester implements Comparable<Semester> {

	private int n;
	private Season season;
	private StudentYear studentYear;
	private LinkedList<Course> courses;
	private int totalCredits;
	
	public Semester(int n, Season season, StudentYear studentYear) {
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
	
	public void addCourse(Course course) {
		courses.add(course);
		totalCredits += course.getCredits();
	}
	
	public void removeCourse(Course course) {
		courses.remove(course);
		totalCredits -= course.getCredits();
	}
	
	public Course[] getCourses() {
		// deep copy
		return (Course[])courses.toArray();
	}
	
	public int getTotalCredits() {
		return totalCredits;
	}
	
	@Override
	public int compareTo(Semester semester) {
		return this.n - semester.n;
	}
	
	@Override
	public String toString() {
		return String.valueOf(n) + ":" + season.toString();
	}

}
