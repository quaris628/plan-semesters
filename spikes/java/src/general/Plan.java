/**
 * 
 */
package general;

import semesters.Semester;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import course.Course;
import degree.Degree;

/**
 * @author Quaris
 *
 */
public class Plan {
	private Map<Course, Semester> coursesTaking;
	private ArrayList<Degree> degrees;
	
	public Plan() {
		coursesTaking = new HashMap<Course, Semester>();
		degrees = new ArrayList<Degree>();
	}
	
	// O(n)
	public void addDegree(Degree degree) {
		if (!degrees.contains(degree)) {
			degrees.add(degree);
			
			// add all courses of that degree that weren't already there
			for (Course course : degree.getAllCourses()) {
				if (!coursesTaking.containsKey(course)) {
					coursesTaking.put(course, Semester.UNPLANNED);
				}
			}
		}
	}
	
	// O(n)
	public void removeDegree(Degree degreeToRemove) {
		if (degrees.contains(degreeToRemove)) {
			degrees.remove(degreeToRemove);
			
			// re-add courses of all other degrees
			// can't just remove courses of removing degree, there may be overlap
			Map<Course, Semester> newCoursesTaking = new HashMap<Course, Semester>();
			for (Degree degreeToKeep : degrees) {
				for (Course course : degreeToKeep.getAllCourses()) {
					newCoursesTaking.put(course, coursesTaking.get(course));
				}
			}
			coursesTaking = newCoursesTaking;
		}
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
