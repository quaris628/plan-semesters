/**
 * 
 */
package general;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.HashSet;

import course.Course;
import degree.Degree;
import semesters.Semester;

/**
 * 
 * 
 * @author Quaris
 *
 */
public class Plan {
	
	private List<Semester> semesters;
	private Set<Course> courses; // should be sorted by id
	private Set<Course> unassignedCourses;
	private Set<Course> satisfiedCourses;
	// map is maintained only for performance of query methods
	private Map<Course, Semester> coursesInSemesters;
	private PlanSettings settings;
	
	public Plan() {
		semesters = new ArrayList<Semester>();
		courses = new TreeSet<Course>();
		unassignedCourses = new HashSet<Course>();
		satisfiedCourses = new HashSet<Course>();
		coursesInSemesters = new HashMap<Course, Semester>();
		settings = new PlanSettings();
	}
	
	/**
	 * Tests whether a course can validly be assigned to a semester
	 * @param course
	 * @param semester
	 * @return true if course can be assigned to the semester, otherwise false
	 */
	public boolean canAssign(Course course, Semester semester) {
		checkArgument(course);
		checkArgument(semester);
		
		if (course.getReqs() == null) {
			return true;
		}
		return course.getReqs().isSatisfied(this, semester);
	}
	
	/**
	 * Assigns a course to a semester
	 * @param course
	 * @param semester to assign the course to
	 */
	public void assign(Course course, Semester semester) {
		checkArgument(course);
		checkArgument(semester);
		
		if (coursesInSemesters.containsKey(course)) {
			Semester oldSemester = coursesInSemesters.get(course);
			if (oldSemester == semester) {
				return;
			}
			oldSemester.remove(course);
			coursesInSemesters.remove(course);
		}
		unassignedCourses.remove(course);
		satisfiedCourses.remove(course);
		
		semester.add(course);
		coursesInSemesters.put(course, semester);
	}
	
	/**
	 * Unassigns course from all semesters
	 * @param course to unassign
	 */
	public void unassign(Course course) {
		checkArgument(course);
		
		if (coursesInSemesters.containsKey(course)) {
			coursesInSemesters.get(course).remove(course);
			coursesInSemesters.remove(course);
		}
		satisfiedCourses.remove(course);
		unassignedCourses.add(course);
	}
	
	/**
	 * Marks course as already been satisfied before this plan's start
	 * @param course
	 */
	public void assignSatisfied(Course course) {
		checkArgument(course);
		
		if (coursesInSemesters.containsKey(course)) {
			coursesInSemesters.get(course).remove(course);
			coursesInSemesters.remove(course);
		}
		satisfiedCourses.add(course);
		unassignedCourses.remove(course);
	}

	/**
	 * Unassigns all courses from all semesters
	 * Does not move courses marked as satisfied
	 */
	public void clearAssigned() {
		for (Semester s : semesters) {
			s.clearCourses();
		}
		coursesInSemesters.clear();
	}
	
	/**
	 * Unassigns all courses from all semesters
	 * Also unmarks all satisfied courses, moving them to unassigned
	 */
	public void unassignAll() {
		for (Semester s : semesters) {
			s.clearCourses();
		}
		coursesInSemesters.clear();
		satisfiedCourses.clear();
	}
	
	public void autoAssign() {
		// TODO move from PlanMgr.java
	}
	
	/**
	 * Checks whether the course is unassigned in the plan
	 * @param course
	 * @return true if course is unassigned, otherwise false
	 */
	public boolean isUnassigned(Course course) {
		checkArgument(course);
		return unassignedCourses.contains(course);
	}
	
	/**
	 * Checks whether the course is marked as satisfied in the plan
	 * @param course
	 * @return true if course is satisfied, otherwise false
	 */
	public boolean isSatisfied(Course course) {
		checkArgument(course);
		return satisfiedCourses.contains(course);
	}
	
	/**
	 * Checks whether the course is present in the plan
	 * @param course
	 * @return true if course is in the plan, otherwise false
	 */
	public boolean contains(Course course) {
		if (course == null) {
			throw new IllegalArgumentException("course cannot be null");
		}
		
		return unassignedCourses.contains(course)
				|| satisfiedCourses.contains(course)
				|| coursesInSemesters.containsKey(course);
	}
	
	/**
	 * Checks whether the semester is present in the plan
	 * @param semester
	 * @return true if semester is in the plan, otherwise false
	 */
	public boolean contains(Semester semester) {
		if (semester == null) {
			throw new IllegalArgumentException("semester cannot be null");
		}
		
		return semesters.contains(semester);
	}
	
	public Semester getSemesterOf(Course course) {
		checkArgument(course);
		return coursesInSemesters.get(course);
	}
	
	private void checkArgument(Course course) {
		if (course == null) {
			throw new IllegalArgumentException("course cannot be null");
		} if (!(unassignedCourses.contains(course)
				|| satisfiedCourses.contains(course)
				|| coursesInSemesters.containsKey(course))) {
			throw new IllegalArgumentException("plan does not contain " + course.toString());
		}
	}
	
	private void checkArgument(Semester semester) {
		if (semester == null) {
			throw new IllegalArgumentException("semester cannot be null");
		} if (!semesters.contains(semester)) {
			throw new IllegalArgumentException("plan does not contain " + semester.toString());
		}
	}
	
	/**
	 * Gets iterator over all semesters contained in this plan
	 * @return iterator over semesters
	 */
	public Iterator<Semester> getSemesters() {
		return semesters.iterator();
	}
	
	/**
	 * Gets iterator over all courses contained in this plan
	 * @return iterator over courses
	 */
	public Iterator<Course> getAllUniqueCourses() {
		return courses.iterator();
	}
	
	public void applySettings() {
		if (settings.hasChanged()) {
			// TODO
			// throw exceptions if things go wrong
			updateDegrees();
			
			settings.resetChangeFlag();
		}
	}

	private void updateStartingSeason() {
		// TODO
	}
	
	private void updateEnabledSeasons() {
		// TODO
	}
	
	private void updateYears() {
		// TODO
	}
	
	private void updateCumulativeCredits() {
		// TODO
	}
	
	// must be called before updating semesters, so old semester object references still hold up
	private void updateDegrees() {
		Set<Course> courses = new TreeSet<Course>();
		Set<Course> unassignedCourses = new HashSet<Course>();
		Set<Course> satisfiedCourses = new HashSet<Course>();
		Map<Course, Semester> coursesInSemesters = new HashMap<Course, Semester>();
		
		Iterator<Degree> degreesIter = settings.getDegrees();
		while (degreesIter.hasNext()) {
			Degree degree = degreesIter.next();
			for (Course course : degree.getAllCourses()) {
				courses.add(course);
				if (this.coursesInSemesters.containsKey(course)) {
					Semester semester = this.coursesInSemesters.get(course);
					coursesInSemesters.put(course, semester);
					semester.add(course);
				} else if (this.satisfiedCourses.contains(course)) {
					satisfiedCourses.add(course);
				} else {
					unassignedCourses.add(course);
				}
			}
		}
		
		this.courses = courses;
		this.unassignedCourses = unassignedCourses;
		this.coursesInSemesters = coursesInSemesters;
	}
	
	public String toPrint() {
		StringBuilder s = new StringBuilder();
		
		s.append("Degrees:\n");
		Iterator<Degree> iter = settings.getDegrees();
		while (iter.hasNext()) {
			Degree degree = iter.next();
			s.append("  ");
			s.append(degree.getReqirements().isSatisfied(this) ? "✓ - " : "X - ");
			s.append(degree).append('\n');
		}
		s.append("\nUnassigned:\n");
		s.append(toPrintIndented(unassignedCourses));
		s.append("Satisfied:\n");
		s.append(toPrintIndented(satisfiedCourses));
		for (Semester semester : semesters) {
			s.append(semester.toString()).append('\n');
			s.append("Cumulative Credits: ").append(semester.getPriorCumulativeCredits()).append(", ");
			s.append(semester.getStudentYear()).append('\n');
			s.append("Credits: ").append(semester.getTotalCredits());
			s.append(toPrintIndented(semester));
		}
		return s.toString();
	}
	
	private String toPrintIndented(Iterable<?> objects) {
		StringBuilder s = new StringBuilder();
		for (Object o : objects) {
			s.append("  ").append(o).append('\n');
		}
		return s.toString();
	}
	
	@Override
	public String toString() {
		return "Plan";
	}
}
