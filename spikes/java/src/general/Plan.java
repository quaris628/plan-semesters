/**
 * 
 */
package general;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.HashSet;

import course.Course;
import degree.Degree;
import semesters.Season;
import semesters.Semester;
import utils.Args;

/**
 * 
 * 
 * @author Quaris
 *
 */
public class Plan {
	
	private static final String UNASSIGNED_NAME = "Unassigned";
	private static final String SATISFIED_NAME = "Satisfied";
	
	private List<Semester> semesters;
	private Set<Course> courses; // should be sorted by id
	private Set<Course> unassignedCourses;
	private Set<Course> satisfiedCourses;
	// map is maintained only for performance of query methods
	private Map<Course, Semester> coursesInSemesters;
	private PlanSettings settings;
	private PlanSettings settingsAtLastUpdate;
	
	public Plan() {
		semesters = new ArrayList<Semester>();
		courses = new TreeSet<Course>();
		unassignedCourses = new HashSet<Course>();
		satisfiedCourses = new HashSet<Course>();
		coursesInSemesters = new HashMap<Course, Semester>();
		settings = new PlanSettings();
		settingsAtLastUpdate = null;
		semesters.add(new Semester(settings.getStartingYear(),
				settings.getStartingSeason(),
				settings.getStartingCredits()));
		
		applySettings();
	}
	
	
	// --------------------------------
	//  Assignment methods
	// --------------------------------
	
	/**
	 * Tests whether a course can validly be assigned to a semester
	 * @param course
	 * @param semester
	 * @return true if course can be assigned to the semester, otherwise false
	 */
	public boolean canAssign(Course course, Semester semester) {
		checkArg(course);
		checkArg(semester);
		
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
		checkArg(course);
		checkArg(semester);
		
		// TODO this has some problem unassigning a course from another semester
		// it is already in. Came up when assigning to a semester later than
		// the current semester.
		
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
		
		// if adding to last semester in the list, expand semester list
		if (semesters.get(semesters.size() - 1) == semester) {
			semesters.add(getSemesterAfter(semester));
		}
	}
	
	/**
	 * Unassigns course from all semesters
	 * @param course to unassign
	 */
	public void unassign(Course course) {
		checkArg(course);
		
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
		checkArg(course);
		
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
	public void unassignAllNotSatisfied() {
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
	
	// overload of autoAssign(Course[])
	public void autoAssign(Iterable<Course> coursesToAssign) {
		// copy to linkedlist
		LinkedList<Course> coursesList = new LinkedList<Course>();
		for (Course course : coursesToAssign) {
			coursesList.addLast(course);
		}
		
		// copy to array
		Course[] coursesArr = coursesList.toArray(new Course[coursesList.size()]);;
		
		autoAssign(coursesArr);
	}
	
	public void autoAssign(Course[] coursesToAssign) {
		boolean lastIterationHasChange = true;
		while (lastIterationHasChange) {
			lastIterationHasChange = false;
			// must do i-counting instead of for-each because modification
			// of the semesters data structure happens during loops
			for (int i = 0; i < coursesToAssign.length; i++) {
				for (int j = 0; j < semesters.size(); j++) {
					Semester semester = semesters.get(j);
					if (canAssign(coursesToAssign[i], semester)) {
						if (this.getSemesterOf(coursesToAssign[i]) != semester) {
							assign(coursesToAssign[i], semester);
							lastIterationHasChange = true;
						}
						break;
					}
				}
			}
		}
	}
	
	
	// --------------------------------
	//  Getters, etc queries
	// --------------------------------
	
	/**
	 * Checks whether the course is unassigned in the plan
	 * @param course
	 * @return true if course is unassigned, otherwise false
	 */
	public boolean isUnassigned(Course course) {
		checkArg(course);
		return unassignedCourses.contains(course);
	}
	
	/**
	 * Checks whether the course is marked as satisfied in the plan
	 * @param course
	 * @return true if course is satisfied, otherwise false
	 */
	public boolean isSatisfied(Course course) {
		checkArg(course);
		return satisfiedCourses.contains(course);
	}
	
	/**
	 * Gets iterable over all unassigned courses
	 * @return Iterable<Course> of all unassigned courses
	 */
	public Iterable<Course> getUnassigned() {
		return unassignedCourses;
	}
	
	/**
	 * Gets iterable over all satisfied courses
	 * @return Iterable<Course> of all satisfied courses
	 */
	public Iterable<Course> getSatisfied() {
		return satisfiedCourses;
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
		checkArg(course);
		return coursesInSemesters.get(course);
	}
	
	public String getAssignmentNameOf(Course course) {
		if (isUnassigned(course)) {
			return UNASSIGNED_NAME;
		} else if (isSatisfied(course)) {
			return SATISFIED_NAME;
		} else {
			return getSemesterOf(course).getName();
		}
		
	}
	
	/**
	 * Gets iterable over all semesters contained in this plan
	 * @return iterator over semesters
	 */
	public Iterable<Semester> getSemesters() {
		return semesters;
	}
	
	/**
	 * Gets iterable over all courses (including unassigned and satisfied) contained in this plan
	 * @return Iterable<Course> over all courses
	 */
	public Iterable<Course> getCourses() {
		return courses;
	}

	private void checkArg(Course course) {
		Args.checkNull(course, "course");
		if (!(unassignedCourses.contains(course)
				|| satisfiedCourses.contains(course)
				|| coursesInSemesters.containsKey(course))) {
			throw new IllegalArgumentException("plan does not contain " + course.toString());
		}
	}
	
	private void checkArg(Semester semester) {
		Args.checkNull(semester, "semester");
		if (!semesters.contains(semester)) {
			throw new IllegalArgumentException("plan does not contain " + semester.toString());
		}
	}
	
	// --------------------------------
	//  Plan Settings
	// --------------------------------
	
	public PlanSettings getSettings() {
		return settings;
	}
	
	public void applySettings() {
		if (settings.hasChanged() && !settings.equals(settingsAtLastUpdate)) {
			// important to update enabled seasons before
			//     updating years and cumulative credits
			updateEnabledSeasons();
			updateStartingSeason();
			updateYears();
			updateCumulativeCredits();
			updateDegrees();
			settingsAtLastUpdate = settings.clone();
			settings.resetChangeFlag();
		}
	}
	
	private void updateEnabledSeasons() {
		// TODO increase efficiency? (with manual linked list? or hash something?)
		
		// remove semesters with recently disabled seasons
		for (int i = 0; i < semesters.size(); i++) {
			Semester semester = semesters.get(i);
			if (!settings.isEnabled(semester.getSeason())) {
				// unassign any courses in this semester
				for (Course course : semester.getCourses()) {
					unassign(course);
				}
				semesters.remove(i);
			}
		}
		
		// add semesters with recently enabled seasons
		Season season = settings.getStartingSeason();
		for (int i = 0; i < semesters.size(); i++) {
			
			// while this semester's season comes after the intended season
			while (season.ordinal() - semesters.get(i).getSeason().ordinal() < 0) {
				// insert a new semester, with the recently enabled season
				Semester newSemester = getSemesterBefore(semesters.get(i));
				semesters.add(i, newSemester);
			}
			season = settings.getNearestEnabledAfter(season);
		}
		
	}
	
	private void updateStartingSeason() {
		while (semesters.get(0).getSeason() != settings.getStartingSeason()) {
			// insert new semester at front of list
			Semester newSemester = getSemesterBefore(semesters.get(0));
			semesters.add(0, newSemester);
		}
	}
	
	private void updateYears() {
		int year = settings.getStartingYear();
		for (Semester semester : semesters) {
			// if previous semester is in a different year
			if (semester.getSeason().ordinal() <=
					settings.getNearestEnabledBefore(semester.getSeason()).ordinal()) {
				year++;
			}
			
			semester.setYear(year);
		}
	}
	
	private void updateCumulativeCredits() {
		int cumCredits = settings.getStartingCredits();
		// sum up all satisfied credits
		for (Course course : satisfiedCourses) {
			cumCredits += course.getCredits();
		}
		
		for (Semester semester : semesters) {
			semester.setPriorCumulativeCredits(cumCredits);
			for (Course course : semester.getCourses()) {
				cumCredits += course.getCredits();
			}
		}
	}
	
	private void updateDegrees() {
		Set<Course> courses = new TreeSet<Course>();
		Set<Course> unassignedCourses = new HashSet<Course>();
		Set<Course> satisfiedCourses = new HashSet<Course>();
		Map<Course, Semester> coursesInSemesters = new HashMap<Course, Semester>();
		
		for (Degree degree : settings.getDegrees()) {
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
	
	private Semester getSemesterBefore(Semester semester) {
		Season season = settings.getNearestEnabledBefore(semester.getSeason());
		int year = semester.getYear()
				- (semester.getSeason().ordinal() - season.ordinal() > 0 ? 0 : 1);
		int cumCredits = settings.getStartingCredits();
		return new Semester(year, season, cumCredits);
	}
	
	private Semester getSemesterAfter(Semester semester) {
		Season season = settings.getNearestEnabledAfter(semester.getSeason());
		int year = semester.getYear();
		if (season.ordinal() <= semester.getSeason().ordinal()) {
			year++;
		}
		int cumCredits = semester.getTotalCumulativeCredits();
		return new Semester(year, season, cumCredits);
	}
	
	
	// ----------
	
	public String toPrint() {
		StringBuilder s = new StringBuilder();
		
		s.append("Degrees:\n");
		for (Degree degree : settings.getDegrees()) {
			s.append("  ");
			s.append(degree.getReqirements().isSatisfied(this) ? "✓ - " : "X - ");
			s.append(degree).append('\n');
		}
		s.append('\n').append(UNASSIGNED_NAME).append(":\n");
		s.append(toStringIndented(unassignedCourses)).append('\n');
		s.append(SATISFIED_NAME).append(":\n");
		s.append(toStringIndented(satisfiedCourses)).append('\n');
		for (Semester semester : semesters) {
			s.append(semester.toPrint()).append('\n');
		}
		return s.toString();
	}
	
	private String toStringIndented(Iterable<?> objects) {
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
