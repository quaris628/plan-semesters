/**
 * 
 */
package general;

import semesters.SemesterList;

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
	
	private static final int NUM_STARTING_SEMESTERS = 4; // working on?
	
	private Map<Course, SemesterList> coursesTaking;
	private ArrayList<Degree> degrees;
	private SemesterList lastSemester; // working on?
	
	public Plan() {
		coursesTaking = new HashMap<Course, SemesterList>();
		degrees = new ArrayList<Degree>();
		//lastSemester = SemesterList.FIRST;
		// TODO
		for (int i = 1; i < NUM_STARTING_SEMESTERS; i++) {
			lastSemester = lastSemester.getNext();
		}
	}
	
	// O(n)
	public void addDegree(Degree degree) {
		if (!degrees.contains(degree)) {
			degrees.add(degree);
			
			// add all courses of that degree that weren't already there
			for (Course course : degree.getAllCourses()) {
				if (!coursesTaking.containsKey(course)) {
					coursesTaking.put(course, SemesterList.UNPLANNED);
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
			Map<Course, SemesterList> newCoursesTaking = new HashMap<Course, SemesterList>();
			for (Degree degreeToKeep : degrees) {
				for (Course course : degreeToKeep.getAllCourses()) {
					newCoursesTaking.put(course, coursesTaking.get(course));
				}
			}
			coursesTaking = newCoursesTaking;
		}
	}
	
	public void take(Course course, SemesterList semester) {
		if (coursesTaking.containsKey(course)) {
			coursesTaking.put(course, semester);
		} else {
			throw new IllegalArgumentException("course does not exist in the plan");
		}
	}
	
	public SemesterList getSemesterOf(Course course) {
		return coursesTaking.get(course);
	}
	
	public Course[] getCoursesOf(SemesterList semester) {
		// TODO
		return null;
	}
	
	public Course[] getAllCourses() {
		return (Course[])coursesTaking.keySet().toArray();
	}
	
	public SemesterList getLastSemester() {
		// TODO working on now?
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
