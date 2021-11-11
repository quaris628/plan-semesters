import java.util.LinkedList;

/**
 * A list of courses
 * Can calculate the total credits
 * @author Quaris
 */
public class CoursesList extends LinkedList<Course> {
    
    /**
     * Calculates the sum of the credits of all courses in this CoursesList
     * O(n)
     * @return total credits
     */
    public int getTotalCredits() {
        int totalCredits = 0;
        for (Course course : super) {
            totalCredits = course.getCredits();
        }
        return totalCredits;
    }
    
}
