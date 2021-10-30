/**
 * 
 */
package general;

import semesters.Season;

/**
 * Complete 26 Oct
 * @author Quaris
 *
 */
public class Student {

	private Season startingSeason;
	private int startingCredits;

	public Student() {
		this.setStartingSeason(Season.FALL);
		this.setStartingCredits(0);
	}

	public int getStartingCredits() {
		return startingCredits;
	}

	public void setStartingCredits(int startingCredits) {
		this.startingCredits = startingCredits;
	}

	public Season getStartingSeason() {
		return startingSeason;
	}

	public void setStartingSeason(Season startingSeason) {
		this.startingSeason = startingSeason;
	}

}
