/**
 * 
 */
package semesters;

/**
 * @author Quaris
 *
 */
public class YearStatus {
	
	private StudentYear year;
	private int semestersIn;
	
	public YearStatus(StudentYear year) {
		this.year = year;
		semestersIn = 0;
	}
	
    public YearStatus(StudentYear year, int semestersIn) {
		
    	this.year = year;
		this.semestersIn = semestersIn % 4;
		for (int i = 0; i < semestersIn / 4; i ++) {
			this.year = this.year.getNext();
		}
	}
    
    public YearStatus getStatusNextSemester() {
    	return new YearStatus(year, semestersIn + 1);
    }

    public StudentYear GetStudentYear() {
    	return year;
    }
    
    @Override
    public boolean equals(Object o) {
    	// TODO - ignore semestersIn, but year must be the same
    	return false;
    }
    
    @Override
    public String toString() {
    	// TODO
    	return null;
    }
}
