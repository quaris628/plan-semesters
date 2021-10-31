/**
 * 
 */
package semesters;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import exceptions.CoursesNotClearedException;
import exceptions.OneSeasonEnabledException;

/**
 * Complete 30 Oct
 * @author Quaris
 *
 */
public class SemesterList implements Iterable<Semester> {
	
	public static final boolean DEFAULT_ENABLED_SPRING = true;
	public static final boolean DEFAULT_ENABLED_SUMMER = false;
	public static final boolean DEFAULT_ENABLED_FALL = true;
	public static final boolean DEFAULT_ENABLED_WINTER = false;
	
	private int startingCredits;
	private boolean[] enabledSeasons;
	private Semester unplanned;
	private Semester satisfied;
	private LinkedList<Semester> semesters;
	
	public SemesterList(int startingCredits, Season startingSeason) {
		this.startingCredits = startingCredits;
		enabledSeasons = new boolean[] {
				DEFAULT_ENABLED_SPRING,
				DEFAULT_ENABLED_SUMMER,
				DEFAULT_ENABLED_FALL,
				DEFAULT_ENABLED_WINTER };
		// MAX and -1 are so .compareTo results work well for prereq checks
		unplanned = new Semester(null, Integer.MAX_VALUE, null, null);
		satisfied = new Semester(this, -1, null, null);
		semesters = new LinkedList<Semester>();
		semesters.add(new Semester(this, 0, startingSeason, StudentYear.getStudentYear(startingCredits)));
	}
	
	// --------------------------------
	//  set/get initial conditions
	// --------------------------------
	
	public void setStartingCredits(int startingCredits) {
		this.startingCredits = startingCredits;
		refreshYears();
	}
	
	/**
	 * update year status based on cumulative credits
	 * @return total credits over all semesters
	 */
	public int refreshYears() {
		int cumulativeCredits = startingCredits;
		for (Semester semester : semesters) {
			semester.setStudentYear(StudentYear.getStudentYear(cumulativeCredits));
			cumulativeCredits += semester.getTotalCredits();
		}
		return cumulativeCredits;
	}
	
	public int getStartingCredits() {
		return startingCredits;
	}

	public void setStartingSeason(Season season) {
		Season seasonToSet = season;
		// refresh season of all semesters
		for (Semester semester : semesters) {
			semester.setSeason(seasonToSet);
			seasonToSet = this.getNextEnabledAfter(seasonToSet);
		}
	}
	
	public Season getStartingSeason() {
		return semesters.getFirst().getSeason();
	}


	// --------------------------------
	//  manage Seasons
	// --------------------------------

	public void enable(Season seasonToEnable) {
		if (enabledSeasons[seasonToEnable.ordinal()]) {
			return;
		}
		
		// calculate indices to insert semesters with new seasons at
		
		// calculate how many seasons are enabled (not counting the new addition)
		int numEnabledSeasons = 0;
		for (int i = 0; i < enabledSeasons.length; i++) {
			if (enabledSeasons[i]) {
				numEnabledSeasons++;
			}
		}
		
		// calculate index of first insertion point
		int firstInsertIndex = 0;
		Season seasonToInsertAfter = this.getNextEnabledBefore(seasonToEnable);
		while (semesters.get(firstInsertIndex).getSeason()
				!= seasonToInsertAfter) {
			firstInsertIndex++;
		}
		
		// This part is needlessly O(n^2), since linkedlist .add(index, item) is O(n) 
		// TODO increase efficiency to O(n) with a manual linked list (iterate once over list)
		for (int i = firstInsertIndex; i < semesters.size(); i+= numEnabledSeasons + 1) {
			semesters.add(i, new Semester(this, i, seasonToEnable, null));
		}
		refreshYears();
		
		enabledSeasons[seasonToEnable.ordinal()] = true;
	}
	
	public void disable(Season seasonToDisable)
			throws CoursesNotClearedException, OneSeasonEnabledException {
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
		
		// if any semesters during this season have any courses, throw CoursesNotClearedException
		LinkedList<Semester> semestersToRemove = new LinkedList<Semester>();
		for (Semester semester : semesters) {
			if (semester.getSeason() == seasonToDisable) {
				if (semester.getCourses().length > 0) {
					throw new CoursesNotClearedException(seasonToDisable, semester);
				}
				semestersToRemove.add(semester);
			}
		}
		
		// Remove all semesters with the season from the linked list
		semesters.removeAll(semestersToRemove);
		
		// refresh cumulative credits (=> refresh student years)
		refreshYears();
		
		enabledSeasons[seasonToDisable.ordinal()] = false;
	}
	
	public boolean isEnabled(Season season) {
		return enabledSeasons[season.ordinal()];
	}
	
	public int getNumEnabledSeasons() {
		int numEnabled = 0;
		for (int i = 0; i < enabledSeasons.length; i++) {
			if (enabledSeasons[i]) {
				numEnabled++;
			}
		}
		return numEnabled;
	}
	
	public Season getNextEnabledAfter(Season season) {
		Season toReturn = season.getNext();
		while (!enabledSeasons[toReturn.ordinal()] ) {
			toReturn = toReturn.getNext();
		}
		return toReturn;
	}
	
	public Season getNextEnabledBefore(Season season) {
		Season toReturn = season.getPrev();
		while (!enabledSeasons[toReturn.ordinal()] ) {
			toReturn = toReturn.getPrev();
		}
		return toReturn;
	}

	// --------------------------------
	//  get Semesters
	// --------------------------------

	public Semester getUnplanned() {
		return unplanned;
	}

	public Semester getSatisfied() {
		return satisfied;
	}

	public Semester getSemester(int i) {
		return semesters.get(i);
	}
	
	// Iterable interface
	@Override
	public Iterator<Semester> iterator() {
		return semesters.iterator();
	}
	
	public int getNumSemesters() {
		return semesters.size();
	}
	
	public Semester getSemesterAfter(Semester semester) throws NoSuchElementException {
		Iterator<Semester> iter = semesters.iterator();
		int cumulativeCredits = 0;
		while (iter.hasNext()) {
			Semester s = iter.next();
			cumulativeCredits += s.getTotalCredits();
			if (s.equals(semester)) {
				if (iter.hasNext()) {
					return iter.next();
				} else {
					// create new semester
					int n = s.getN();
					Season season = s.getSeason().getNext();
					while (!enabledSeasons[season.ordinal()] ) {
						season = season.getNext();
						n++;
					}
					StudentYear year = StudentYear.getStudentYear(cumulativeCredits);
					Semester newSemester = new Semester(this, n, season, year);
					semesters.addLast(newSemester);
					return newSemester;
				}
				
			}
		}
		throw new NoSuchElementException(semester.toString() + " was not found");
	}
	
	// --------------------------------
	
	@Override
	public String toString() {
		return "SemesterList[" + String.valueOf(semesters.size()) + "]"; 
	}
	
}
