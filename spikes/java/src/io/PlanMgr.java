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
			.withOption("Edit Degrees", chooseDegrees)
			.withOption("Assign a Course to a Semester", PlanMgr::askCourseToPlan)
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
		
		Course[] courses = Plan.INSTANCE.getAllCourses();
		Arrays.sort(courses);
		for (Course course : courses) {
			menuBuilder.withOption(course.toString() + " ("
					+ Plan.INSTANCE.getSemesterOf(course).getName() + ")",
					() -> { askSemesterOfCourse(course); });
		}
		
		menuBuilder.withoutRepeats().build().run();
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
				} ); // is 'degree' variable scope right to make this work?
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
