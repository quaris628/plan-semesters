/**
 * 
 */
package general;

import semesters.Season;
import semesters.Semester;
import semesters.SemesterList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import course.Course;
import degree.Degree;

/**
 * TODO revisit skeleton of this class
 * TODO write implementation of this class
 * @author Quaris
 *
 */
public class Plan {
	
	public static final Season DEFAULT_STARTING_SEASON = Season.FALL;
	
	private ArrayList<Degree> degrees;
	private SemesterList semesters;
	private Map<Course, Semester> courses;
	
	public Plan() {
		degrees = new ArrayList<Degree>();
		semesters = new SemesterList(0, DEFAULT_STARTING_SEASON);
		courses = new HashMap<Course, Semester>();
		
		
	}
	
	public void addDegree(Degree degree) {
		if (!degrees.contains(degree)) {
			degrees.add(degree);
			
			// add all courses of that degree that weren't already there
			for (Course course : degree.getAllCourses()) {
				if (!courses.containsKey(course)) {
					// TODO
					//coursesTaking.put(course, SemesterList.UNPLANNED);
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
					newCoursesTaking.put(course, courses.get(course));
				}
			}
			courses = newCoursesTaking;
		}
	}
	
	public void take(Course course, Semester semester) {
		if (courses.containsKey(course)) {
			courses.put(course, semester);
		} else {
			throw new IllegalArgumentException("course does not exist in the plan");
		}
	}
	
	public Semester getSemesterOf(Course course) {
		return courses.get(course);
	}
	
	public Course[] getCoursesOf(Semester semester) {
		// TODO
		return null;
	}
	
	public Course[] getAllCourses() {
		return (Course[])courses.keySet().toArray();
	}
	
	public Semester getLastSemester() {
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
