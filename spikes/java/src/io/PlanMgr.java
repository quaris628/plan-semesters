/**
 * 
 */
package io;

import java.util.Arrays;

import catalog.Catalog;
import course.Course;
import degree.Degree;
import general.Plan;
import semesters.Semester;
import semesters.SemesterList;

/**
 * I/O related to managing the user's plan of semesters
 * Fully static, essentially a singleton
 * In-Progress 7 Nov
 * @author Quaris
 *
 */
public class PlanMgr {

	private PlanMgr() {	}

	// --------------------------------
	//  Menus
	// --------------------------------
	
	// Degree Choice menu, options to add or remove a degree
	private static CmdMenu chooseDegrees =
			new CmdMenu.CmdMenuBuilder("Manage My Degree Selections")
			.withOnEnter(PlanMgr::printSelectedDegrees)
			.withOption("Add a Degree", PlanMgr::addDegree)
			.withOption("Remove a Degree", PlanMgr::removeDegree)
			.build();
	
	private static CmdMenu rootMenu =
			new CmdMenu.CmdMenuBuilder("Edit Semester Plan")
			.withOnEnter(PlanMgr::printPlan)
			.withOption("Choose Degrees", chooseDegrees)
			.withOption("Auto-Assign Unplanned Courses to Semesters", PlanMgr::autoAssignUnplannedCourses)
			.withOption("Manually Assign a Course to a Semester", PlanMgr::askCourseToPlan)
			.withOption("Edit Semester Settings", () -> {})
			.build();

	public static void run() {
		rootMenu.run();
	}
	
	
	// --------------------------------
	//  Planning Methods
	// --------------------------------
	
	// Prompt user to choose a semester during which to plan the course they chose
	private static void askSemesterOfCourse(Course course) {
		CmdMenu.CmdMenuBuilder menuBuilder = new CmdMenu.CmdMenuBuilder(
				course.toString() + " is currently in " +
				Plan.INSTANCE.getSemesterOf(course).getName()
				+ "\nMove " + course.toString() + " to...")
				.withOnEnter(PlanMgr::printPlan);
		
		Semester unplanned = Plan.INSTANCE.getSemesters().getUnplanned();
		menuBuilder.withOption(unplanned.getName(),
				() -> { Plan.INSTANCE.take(course, unplanned); });
		Semester satisfied = Plan.INSTANCE.getSemesters().getSatisfied();
		menuBuilder.withOption(satisfied.getName(),
				() -> { Plan.INSTANCE.take(course, satisfied); });
		for (Semester semester : Plan.INSTANCE.getSemesters()) {
			if (Plan.INSTANCE.canTake(course, semester)) {
				menuBuilder.withOption(semester.getName(),
						() -> { Plan.INSTANCE.take(course, semester); });
			}
			
		}
		
		menuBuilder.withoutRepeats().build().run();
	}
	
	// Prompt user to choose a course to plan a semester for
	private static void askCourseToPlan() {
		CmdMenu.CmdMenuBuilder menuBuilder = new CmdMenu.CmdMenuBuilder(
				"Select a Course to assign to a different Semester:")
				.withOnEnter(PlanMgr::printPlan);
		
		for (Course course : Plan.INSTANCE.getAllCourses()) {
			menuBuilder.withOption(course.toString() + " ("
					+ Plan.INSTANCE.getSemesterOf(course).getName() + ")",
					() -> { askSemesterOfCourse(course); });
		}
		
		menuBuilder.withoutRepeats().build().run();
	}
	
	private static void autoAssignUnplannedCourses() {
		Course[] unplannedCourses = Plan.INSTANCE.getSemesters().getUnplanned().getCourses();
		autoAssign(Arrays.asList(unplannedCourses));
	}
	
	// TODO move this to a method in Plan
	private static void autoAssign(Iterable<Course> courses) {
		SemesterList allSemesters = Plan.INSTANCE.getSemesters();
		boolean lastIterationHasChange = false;
		boolean isFirstIteration = true;
		while (!lastIterationHasChange) {
			for (Course course : courses) {
				for (Semester semester : allSemesters) {
					if (Plan.INSTANCE.canTake(course, semester)) {
						Plan.INSTANCE.take(course, semester);
						lastIterationHasChange = true;
						break;
					}
					
				}
			}
			if (isFirstIteration && !lastIterationHasChange) {
				isFirstIteration = false;
				break;
			}
		}
		// TODO make auto-assignment take into account 18-credit max each semester
	}
	
	
	// --------------------------------
	//  Degree Mgt Methods
	// --------------------------------
	
	private static void printSelectedDegrees() {
		System.out.println("\nSelected Degrees:");
		for (Degree degree : Plan.INSTANCE.getDegrees()) {
			System.out.println("  " + degree);
		}
		System.out.println();
	}
	
	private static void addDegree() {
		CmdMenu.CmdMenuBuilder addDegreeBuilder =
				new CmdMenu.CmdMenuBuilder("Select a Degree to Add:");
		for (Degree degree : Catalog.getAllDegrees()) {
			if (!Plan.INSTANCE.hasDegree(degree)) {
				addDegreeBuilder.withOption(degree.toString(), () -> {
					Plan.INSTANCE.addDegree(degree);
					System.out.println("Added " + degree.getName());
				} );
			}
		}
		addDegreeBuilder.withoutRepeats().build().run();
	}
	
	private static void removeDegree() {
		CmdMenu.CmdMenuBuilder removeDegreeBuilder =
				new CmdMenu.CmdMenuBuilder("Select a Degree to Remove:");
		
		for (Degree degree : Plan.INSTANCE.getDegrees()) {
			removeDegreeBuilder.withOption(degree.toString(), () -> {
					Plan.INSTANCE.removeDegree(degree);
					System.out.println("Removed " + degree.getName());
				} );
		}
		
		removeDegreeBuilder.withoutRepeats().build().run();
	}

	// --------------------------------
	//  Misc
	// --------------------------------
	
	private static void printPlan() {
		System.out.println("--------------------------------");
		System.out.print(Plan.INSTANCE);
		System.out.println("--------------------------------\n");
	}
	
}
