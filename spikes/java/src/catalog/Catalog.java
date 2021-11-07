/**
 * 
 */
package catalog;

import java.util.LinkedList;

import course.CPreReq;
import course.CReqAnd;
import course.Course;
import degree.DReqAnd;
import degree.DReqCourse;
import degree.Degree;
import degree.DegreeReq;

/**
 * TODO, big TODO: replace this placeholder catalog with a dynamic catalog
 *     whose departments, degrees, and courses can be managed by the user
 * @author Quaris
 *
 */
public class Catalog {

	public static final Department[] ALL_DEPARTMENTS = new Department[] {
			CS.INSTANCE,
			SE.INSTANCE
			};
	private static Degree[] allDegrees = null; // cache for getAllDegrees()
	
	private Catalog() {
		
	}
	
	public static Degree[] getAllDegrees() {
		if (allDegrees == null) {
			LinkedList<Degree> allDegrees = new LinkedList<Degree>();
			for (Department dept : ALL_DEPARTMENTS) {
				for (Degree degree : dept.getDegrees()) {
					allDegrees.add(degree);
				}
			}
			Catalog.allDegrees = (Degree[])allDegrees.toArray(new Degree[allDegrees.size()]);
		}
		return allDegrees;
	}
	
	/**
	 * Gets degree by a name, searching all departments
	 * If no degree found, returns null
	 * @param name
	 * @return degree
	 */
	public static Degree getDegreeByName(String name) {
		for (Department dept : ALL_DEPARTMENTS) {
			Degree degree = dept.getDegreeByName(name);
			if (degree != null) {
				return degree;
			}
		}
		return null;
	}
	
	// individual depts are singleton classes that extend abstract Department superclass
	public static class CS extends Department {
		
		// Courses
		
		public static final Course CS1430 = new Course("CS1430", "CS", "Intro to Computer Programming", 3);
		public static final Course CS2430 = new Course("CS2430", "CS", "Object Oriented Programming", 3,
				new CPreReq(CS1430));
		public static final Course CS2630 = new Course("CS2630", "CS", "Data Structures", 3,
				new CPreReq(CS2430));
		
		// Degrees
		
		public static final Degree CSBS = new Degree("Computer Science B.S.",
				new DReqAnd(new DegreeReq[] {
				new DReqCourse(CS1430),
				new DReqCourse(CS2430),
				new DReqCourse(CS2630)
				}));
		
		public static final CS INSTANCE = new CS();
		
		private CS() {
			super("Computer Science",
				new Course[] {
						CS.CS1430,
						CS.CS2430,
						CS.CS2630
				}, new Degree[] {
						CS.CSBS
				});
			System.out.println(CS.CSBS);
		}
	}
	
	public static class SE extends Department {
		
		// Courses
		
		public static final Course SE2730 = new Course("SE2730", "SE", "Intro to Software Engineering", 3,
				new CPreReq(CS.CS1430));
		public static final Course SE3430 = new Course("SE3430", "SE", "Object-Oriented Analysis and Design", 3,
				new CReqAnd(new CPreReq(CS.CS2630), new CPreReq(SE2730)));
		
		// Degrees
		
		public static final Degree SEBS = new Degree("Software Engineering B.S.",
				new DReqAnd(new DegreeReq[] {
				new DReqCourse(CS.CS1430),
				new DReqCourse(CS.CS2430),
				new DReqCourse(CS.CS2630),
				new DReqCourse(SE2730),
				new DReqCourse(SE3430)
				}));
		
		public static final SE INSTANCE = new SE();
		
		private SE() {
			super("Software Engineering",
				new Course[] {
						SE2730,
						SE3430
				}, new Degree[] {
						SEBS
				});
		}
		
	}

}
