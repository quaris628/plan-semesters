/**
 * 
 */
package course;

import java.util.Collection;
import java.util.LinkedList;

/**
 * @author Quaris
 *
 */
public class CourseList extends LinkedList<Course> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3606738445144911277L;
	
	public CourseList() {
		
	}
	public CourseList(Collection<? extends Course> c) {
		super(c);
	}

}
