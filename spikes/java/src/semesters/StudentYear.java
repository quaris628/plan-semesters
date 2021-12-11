/**
 * 
 */
package semesters;

import utils.Args;

/**
 * Complete
 * @author Quaris
 *
 */
public enum StudentYear {
	FRESHMAN,
	SOPHOMORE,
	JUNIOR,
	SENIOR;
	
	public static final int CREDITS_PER_YEAR_STATUS = 30;
	
	public StudentYear getNext() {
		if (this == SENIOR) { return null; }
		return StudentYear.values()[this.ordinal() + 1];
	}
	
	public StudentYear getPrev() {
		if (this == FRESHMAN) { return null; }
		return StudentYear.values()[this.ordinal() - 1];
	}
	
	public static StudentYear getStudentYear(int credits) {
		Args.checkNonNegative(credits, "credits");
		return StudentYear.values()[Math.min(StudentYear.values().length,
				credits / CREDITS_PER_YEAR_STATUS)];
	}
	
	@Override
	public String toString() {
		// Capitalize first letter, remaining are lowercase
		return this.name().substring(0, 1) + this.name().substring(1).toLowerCase();
	}
	
}
