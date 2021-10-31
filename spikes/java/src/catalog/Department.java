/**
 * 
 */
package catalog;

import course.Course;
import degree.Degree;

/**
 * @author Quaris
 *
 */
public abstract class Department {
	
	private String name;
	private Course[] courses;
	private Degree[] degrees;
	
	protected Department(String name, Course[] courses, Degree[] degrees) {
		this.name = name;
		this.courses = courses;
		this.degrees = degrees;
	}
	
	public Course[] getCourses() {
		return courses;
	}
	
	public Course getCourseById(String name) {
		for (Course course : courses) {
			if (course.getId().equals(name)) {
				return course;
			}
		}
		return null;
	}

	public Degree[] getDegrees() {
		return degrees;
	}
	
	public Degree getDegreeByName(String name) {
		for (Degree degree : degrees) {
			if (degree.getName().equals(name)) {
				return degree;
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}
