/**
 * 
 */
package general;

import semesters.Semester;

import java.util.LinkedList;
import java.util.Map;

import course.Course;
import degree.Degree;

/**
 * @author Quaris
 *
 */
public class Plan {
	private Map<Course, Semester> coursesTaking;
	private LinkedList<Degree> degrees;
	
	public Plan() {
		// TODO?
	}
	
	public void addDegree(Degree degree) {
		// TODO
	}
	
	public void removeDegree(Degree degree) {
		// TODO
	}
	
	public void take(Course course, Semester semester) {
		// TODO
	}
	
	public Semester getSemesterOf(Course course) {
		// TODO
		return null;
	}
	
	public Course[] getCoursesOf(Semester semester) {
		// TODO
		return null;
	}
	
	public Course[] getAllCourses() {
		// TODO
		return null;
	}
	
	public Semester getFirstSemester() {
		// TODO
		return null;
	}
	
	public Semester getLastSemester() {
		// TODO
		return null;
	}
	
	public int getNumSemesters() {
		// TODO
		return 0;
	}
	
	public void generateRecommendation() {
		// TODO, big TODO
	}
	
	public boolean isRecommendationReady() {
		// TODO
		return false;
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
