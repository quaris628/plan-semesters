/**
 * 
 */
package semesters;

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
	
	public StudentYear getNext() {
		if (this == SENIOR) { return null; }
		return StudentYear.values()[this.ordinal() + 1];
	}
	
	public StudentYear getPrev() {
		if (this == FRESHMAN) { return null; }
		return StudentYear.values()[this.ordinal() - 1];
	}
	
	@Override
	public String toString() {
		// Capitalize first letter, remaining are lowercase
		return this.name().substring(0, 1) + this.name().substring(1).toLowerCase();
	}
	
}
