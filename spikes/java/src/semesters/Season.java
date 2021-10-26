/**
 * 
 */
package semesters;

/**
 * @author Quaris
 *
 */
public enum Season {
	SPRING,
	SUMMER,
	FALL,
	WINTER;
	
	public Season getNext() {
		if (this == WINTER) { return SPRING; }
		return Season.values()[this.ordinal() + 1];
	}
	
	public Season getPrev() {
		if (this == SPRING) { return WINTER; }
		return Season.values()[this.ordinal() - 1];
	}
}
