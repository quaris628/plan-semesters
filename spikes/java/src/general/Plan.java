/**
 * 
 */
package general;

import semesters.Season;
import semesters.Semester;
import semesters.SemesterList;

import java.lang.reflect.Array;
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
	private SemesterList semesterList;
	private Map<Course, Semester> coursesMap;
	
	public Plan() {
		degrees = new ArrayList<Degree>();
		semesterList = new SemesterList(0, DEFAULT_STARTING_SEASON);
		coursesMap = new HashMap<Course, Semester>();
	}
	
	public void addDegree(Degree degree) {
		if (!degrees.contains(degree)) {
			degrees.add(degree);
			
			// add all courses of that degree that weren't already there
			for (Course course : degree.getAllCourses()) {
				if (!coursesMap.containsKey(course)) {
					semesterList.getUnplanned().addCourse(course);
					coursesMap.put(course, semesterList.getUnplanned());
				}
			}
		}
	}
	
	public void removeDegree(Degree degreeToRemove) {
		if (degrees.contains(degreeToRemove)) {
			degrees.remove(degreeToRemove);
			
			// re-add courses of all other degrees
			// can't just remove courses of removing degree, there may be overlap
			Map<Course, Semester> newCoursesMap = new HashMap<Course, Semester>();
			
			// remove 
			for (Course courseToRemove : degreeToRemove.getAllCourses()) {
				coursesMap.get(courseToRemove).removeCourse(courseToRemove);
			}
			
			for (Degree degreeToKeep : degrees) {
				for (Course courseToKeep : degreeToKeep.getAllCourses()) {
					coursesMap.get(courseToKeep).addCourse(courseToKeep);
					newCoursesMap.put(courseToKeep, coursesMap.get(courseToKeep));
				}
			}
			
			coursesMap = newCoursesMap;
		}
	}
	
	public Degree[] getDegrees() {
		return (Degree[])degrees.toArray(new Degree[degrees.size()]);
	}
	
	public boolean hasDegree(Degree degree) {
		return degrees.contains(degree);
	}
	
	public void take(Course course, Semester semester) {
		if (coursesMap.containsKey(course)) {
			coursesMap.put(course, semester);
			semester.addCourse(course);
		} else {
			throw new IllegalArgumentException("course does not exist in the plan");
		}
	}
	
	public SemesterList getSemesters() {
		return semesterList;
	}
	
	public Semester getSemesterOf(Course course) {
		return coursesMap.get(course);
	}
	
	public Course[] getAllCourses() {
		return (Course[])coursesMap.keySet().toArray();
	}
	
	public void generateRecommendation() {
		// TODO, big TODO
	}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		for (Semester semester : semesterList) {
			s.append(semester.toString()).append(":\n");
			for (Course course : semester.getCourses()) {
				s.append('\t').append(course.toString()).append('\n');
			}
			s.append("\tCredits: ").append(semester.getTotalCredits()).append('\n');
		}
		return s.toString();
	}
}
