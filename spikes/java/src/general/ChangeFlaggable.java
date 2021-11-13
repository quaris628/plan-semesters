package general;

/**
 * An object that wishes to convey whether its
 *     internal state has changed or not
 * Intended to be extended from
 * 
 * The change flag starts false.
 * When the subclass flags a change, the flag is set to true,
 *     using protected method flagChange().
 * The status of the flag can be queried with hasChanged().
 * The flag can be set back to false at any time, with resetChangeFlag().
 * @author Quaris
 */
public class ChangeFlaggable {

	private boolean changed;
	
	protected ChangeFlaggable() {
		changed = false;
	}

	/**
	 * Test if this object's state has changed or not
	 * @return true if a change occurred, false if no changes occurred
	 */
	public boolean hasChanged() {
		return changed;
	}
	
	/**
	 * Reset change flag to unchanged
	 */
	public void resetChangeFlag() {
		changed = false;
	}
	
	/**
	 * Flag that a change in the state of the object has occurred 
	 */
	protected void flagChange() {
		changed = true;
	}
}
