/**
 * 
 */
package semesters;

import java.util.LinkedList;

import course.Course;
import exceptions.CoursesNotClearedException;
import exceptions.OneSeasonEnabledException;

/**
 * Complete 26 Oct
 * @author Quaris
 *
 */
public class SemesterList implements Comparable<SemesterList> {
	
	public static final boolean DEFAULT_ENABLED_SPRING = true;
	public static final boolean DEFAULT_ENABLED_SUMMER = false;
	public static final boolean DEFAULT_ENABLED_FALL = true;
	public static final boolean DEFAULT_ENABLED_WINTER = false;
	private static boolean[] enabledSeasons = new boolean[] {
			DEFAULT_ENABLED_SPRING,
			DEFAULT_ENABLED_SUMMER,
			DEFAULT_ENABLED_FALL,
			DEFAULT_ENABLED_WINTER };
	
	// MAX_VALUE is so prereq checks work elegantly when a course's semester is unplanned
	public static final SemesterList UNPLANNED = new SemesterList(Integer.MAX_VALUE);
	public static final SemesterList SATISFIED = new SemesterList(-1);
	private static SemesterList first = new SemesterList(0);
	private static SemesterList last = first;
	private static int numSemesters = 1;
	
	// this class is a manual linked list
	private int n;
	private SemesterList next;
	private SemesterList prev;
	
	private Season season;
	private LinkedList<Course> courses;
	private int credits;
	private int prevCumulativeCredits;
	
	public SemesterList(int n) {
		this.n = n;
		this.next = null;
		this.prev = null;
		this.season = null;
		this.courses = new LinkedList<Course>();
		this.credits = 0;
		this.prevCumulativeCredits = 0;
	}

	public static SemesterList getFirst() {
		return first;
	}
	
	public static SemesterList getLast() {
		return last;
	}
	
	/**
	 * Set number of credits achieved prior to the first semester
	 * (This does not include the credits of courses in SATISFIED -- these are included internally)
	 * @param credits
	 */
	public static void setStartingCredits(int credits) {
		first.prevCumulativeCredits = credits = SATISFIED.getCredits();
	}
	
	public static int getNumSemesters() {
		return numSemesters;
	}
	
	/**
	 * Gets next semester, forward in time
	 * If no semester after this semester exists, creates a new semester 
	 * @return next semester
	 */
	public SemesterList getNext() {
		if (next == null) {
			// create a new semester
			next = new SemesterList(n + 1);
			next.prev = this;
			next.season = this.season.getNext();
			while (!SemesterList.isEnabled(next.season)) {
				next.season = next.season.getNext();
			}
			next.prevCumulativeCredits = this.prevCumulativeCredits + this.credits;
			last = next;
			numSemesters++;
		}
		return next;
	}
	
	/**
	 * Gets previous semester, backward in time
	 * If no semester before this semester exists, return null 
	 * @return previous semester
	 */
	public SemesterList getPrev() {
		return prev;
	}
	
	public boolean hasNext() {
		return next != null;
	}
	
	public boolean hasPrev() {
		return prev != null;
	}
	
	// TODO should probably write tests for this method, cuz there's so many things I could miss
	public static void enable(Season seasonToEnable) {
		enabledSeasons[seasonToEnable.ordinal()] = true;
		
		// get previous enabled season (there could be a gap where one semester is disabled)
		Season prevEnabledSeason = seasonToEnable.getPrev();
		while (!SemesterList.isEnabled(prevEnabledSeason)) {
			prevEnabledSeason = prevEnabledSeason.getPrev();
		}
		
		// insert new seasons between all semesters in the linked list
		for (SemesterList semester = first; semester.hasNext(); semester = semester.getNext()) {
			if (semester.season == prevEnabledSeason) {
				// create new semester
				SemesterList newSemester = new SemesterList(semester.n + 1);
				// insert new semester into linked list
				newSemester.next = semester.getNext();
				newSemester.prev = semester;
				semester.next.prev = newSemester;
				semester.next = newSemester;
				numSemesters++;
				
				newSemester.season = seasonToEnable;
				// update cumulative credits when loop finished
			}
		}
		
		// force refresh of cumulative credits
		setStartingCredits(first.prevCumulativeCredits);
	}
	
	public static void disable(Season seasonToDisable)
			throws CoursesNotClearedException, OneSeasonEnabledException {
		
		// if season already disabled, return and do nothing
		if (!enabledSeasons[seasonToDisable.ordinal()]) {
			return;
		}
		
		// if disabling the last enabled season, throw NoSeasonsEnabledException
		boolean isLastEnabledCourse = true;
		for (int i = 0; i < enabledSeasons.length; i++) {
			if (i != seasonToDisable.ordinal() && enabledSeasons[i]) {
				isLastEnabledCourse = false;
				break;
			}
		}
		if (isLastEnabledCourse) {
			throw new OneSeasonEnabledException("Cannot disable the last enabled season");
		}
		
		// if any semesters during this season have any courses, throw exception
		for (SemesterList semester = first; semester.hasNext(); semester = semester.getNext()) {
			if (semester.getSeason() == seasonToDisable && semester.getCourses().length > 0) {
				throw new CoursesNotClearedException("Season must not contain any planned courses in order to be disabled");
			}
		}
		
		// Remove all semesters with the season from the linked list
		for (SemesterList semester = first; semester.hasNext(); semester = semester.getNext()) {
			if (semester.season == seasonToDisable) {
				// remove semester from linked list
				SemesterList prevSemester = semester.prev;
				SemesterList nextSemester = semester.next;
				prevSemester.next = nextSemester;
				nextSemester.prev = prevSemester;
			}
		}
		// force refresh of cumulative credits
		setStartingCredits(first.prevCumulativeCredits);
		
		enabledSeasons[seasonToDisable.ordinal()] = false;
	}
	
	public static boolean isEnabled(Season season) {
		return enabledSeasons[season.ordinal()];
	}
	
	public static Season getNextEnabled(Season season) {
		Season toReturn = season.getNext();
		while (!enabledSeasons[toReturn.ordinal()] ) {
			toReturn = toReturn.getNext();
		}
		return toReturn;
	}
	
	public static Season getPrevEnabled(Season season) {
		Season toReturn = season.getPrev();
		while (!enabledSeasons[toReturn.ordinal()] ) {
			toReturn = toReturn.getPrev();
		}
		return toReturn;
	}
	
	public void setSeason(Season season) {
		this.season = season;
		// set season of prev
		
		// set season of next, recursively
		next.setSeason(SemesterList.getNextEnabled(season));
	}

	public Season getSeason() {
		return season;
	}
	
	public StudentYear getStudentYear() {
		// TODO total credits so far
		return StudentYear.getStudentYear(getCredits());
	}
	
	// course management
	// update year status across all subsequent semesters
	public void addCourse(Course course) {
		courses.add(course);
		credits += course.getCredits();
		// TODO
	}
	
	// update year status across all subsequent semesters
	public void removeCourse(Course course) {
		courses.remove(course);
		credits -= course.getCredits();
		// TODO
	}
	
	public Course[] getCourses() {
		// deep copy
		return courses.toArray(new Course[courses.size()]);
	}
	
	public int getCredits() {
		return credits;
	}
	
	/**
	 * @return for s1.compareTo(s2),
	 * 		    1 if s1 > s2
	 * 			0 if s1 == s2
	 * 		   -1 if s1 < s2
	 */
	@Override
	public int compareTo(SemesterList s) {
		return this.n - s.n;
	}
	
	@Override
	public String toString() {
		return season.toString() + String.valueOf(n);
	}
	
}
