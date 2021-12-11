/**
 * 
 */
package semesters;

/**
 * @author Quaris
 *
 */
public enum Season {
	WINTER,
	SPRING,
	SUMMER,
	FALL;
	
	public Season getNext() {
		if (this == FALL) { return WINTER; }
		return Season.values()[this.ordinal() + 1];
	}
	
	public Season getPrev() {
		if (this == WINTER) { return FALL; }
		return Season.values()[this.ordinal() - 1];
	}
}
