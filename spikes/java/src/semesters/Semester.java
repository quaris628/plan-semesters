/**
 * 
 */
package semesters;

import java.util.Arrays;
import java.util.LinkedList;

import course.Course;

/**
 * Complete 11 Nov
 * @author Quaris
 *
 */
public class Semester implements Comparable<Semester> {

	private int year;
	private Season season;
	private StudentYear studentYear;
	private CourseList courses;
	
	public Semester(int year, Season season, StudentYear studentYear) {
		this.year = year;
		this.season = season;
		this.studentYear = studentYear;
		this.courses = new CourseList();
	}

	public int getYear() {
		return year;
	}
	
	public void setYear(int year) {
		this.year = year;
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
	
	public CourseList getCourses() {
		return courses;
	}
	
	public String getName() {
		StringBuilder s = new StringBuilder();
		s.append(year > 0 ? year : "-").append(" ");
		s.append(season != null ? season : "-").append(", ");
		s.append(studentYear != null ? studentYear : "-");
		return s.toString();
	}
	
	@Override
	public int compareTo(Semester semester) {
		return this.year * 4 + this.season.ordinal() - (semester.year * 4 + semester.season.ordinal());
	}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(this.getName()).append('\n');
		for (Course course : courses) {
			s.append("  ").append(course.toString()).append('\n');
		}
		s.append("  Credits: ").append(this.courses.getTotalCredits()).append('\n');
		return s.toString();
	}

}
