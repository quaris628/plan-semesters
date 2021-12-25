/**
 * 
 */
package control;

import catalog.Catalog;
import course.Course;
import degree.Degree;
import io.CmdMenu;
import semesters.Semester;
import utils.Args;

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
			.withOnEnter(() -> { Main.PLAN.applySettings(); printPlan(); })
			.withOption("Change Degrees", chooseDegrees)
			.withOption("Manually Assign a Course to a Semester",
					PlanMgr::askCourseToPlan)
			.withOption("Auto-Assign Unplanned Courses", () -> {
					Main.PLAN.autoAssign(Main.PLAN.getUnassigned()); })
			.withOption("Auto-Assign All Courses", () -> {
					Main.PLAN.autoAssign(Main.PLAN.getCourses()); })
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
		Args.checkNull(course, "course");
		CmdMenu.CmdMenuBuilder menuBuilder = new CmdMenu.CmdMenuBuilder(
				course.toString() + " is currently in "
				+ Main.PLAN.getAssignmentNameOf(course) + "\nMove to...")
				.withOnEnter(PlanMgr::printPlan)
				.withOption("Unassign", () -> { Main.PLAN.unassign(course); })
				.withOption("Satisfied", () -> { Main.PLAN.assignSatisfied(course); });
		
		for (Semester semester : Main.PLAN.getSemesters()) {
			if (Main.PLAN.canAssign(course, semester)) {
				menuBuilder.withOption(semester.getName(),
						() -> { Main.PLAN.assign(course, semester); });
			}
		}
		menuBuilder.withoutRepeats().build().run();
	}
	
	// Prompt user to choose a course to plan a semester for
	private static void askCourseToPlan() {
		CmdMenu.CmdMenuBuilder menuBuilder = new CmdMenu.CmdMenuBuilder(
				"Select a Course to assign to a different Semester:")
				.withOnEnter(PlanMgr::printPlan);
		
		for (Course course : Main.PLAN.getCourses()) {
			menuBuilder.withOption(course.toString() + " ("
					+ Main.PLAN.getAssignmentNameOf(course) + ")",
					() -> { askSemesterOfCourse(course); });
		}
		menuBuilder.withoutRepeats().build().run();
	}
	
	
	// --------------------------------
	//  Degree Mgt Methods
	// --------------------------------
	
	private static void printSelectedDegrees() {
		System.out.println("\nSelected Degrees:");
		for (Degree degree : Main.PLAN.getSettings().getDegrees()) {
			System.out.println("  " + degree);
		}
		System.out.println();
	}
	
	private static void addDegree() {
		CmdMenu.CmdMenuBuilder addDegreeBuilder =
				new CmdMenu.CmdMenuBuilder("Select a Degree to Add:");
		for (Degree degree : Catalog.getAllDegrees()) {
			if (!Main.PLAN.getSettings().containsDegree(degree)) {
				addDegreeBuilder.withOption(degree.toString(), () -> {
						Main.PLAN.getSettings().addDegree(degree);
						System.out.println("Added " + degree.getName());
					});
			}
		}
		addDegreeBuilder.withoutRepeats().build().run();
	}
	
	private static void removeDegree() {
		CmdMenu.CmdMenuBuilder removeDegreeBuilder =
				new CmdMenu.CmdMenuBuilder("Select a Degree to Remove:");
		
		for (Degree degree : Main.PLAN.getSettings().getDegrees()) {
			removeDegreeBuilder.withOption(degree.toString(), () -> {
					Main.PLAN.getSettings().removeDegree(degree);
					System.out.println("Removed " + degree.getName());
				});
		}
		
		removeDegreeBuilder.withoutRepeats().build().run();
	}

	// --------------------------------
	//  Misc
	// --------------------------------
	
	private static void printPlan() {
		System.out.println("\n--------------------------------\n");
		System.out.println(Main.PLAN.toPrint());
		System.out.println("\n--------------------------------\n");
	}
	
}
