/**
 * 
 */
package semesters;

/**
 * @author Quaris
 *
 */
public enum StudentYear {
	FRESHMAN,
	SOPHOMORE,
	JUNIOR,
	SENIOR;
	
	public StudentYear getNext() {
		if (this == SENIOR) { return null; }
		return StudentYear.values()[this.ordinal() + 1];
	}
	
	public StudentYear getPrev() {
		if (this == FRESHMAN) { return null; }
		return StudentYear.values()[this.ordinal() - 1];
	}
}
