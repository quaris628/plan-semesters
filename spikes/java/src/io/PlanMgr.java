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
	private static CmdMenu chooseDegrees = new CmdMenu.CmdMenuBuilder("Manage My Degree Selections")
					.withOnEnter(PlanMgr::printSelectedDegrees)
					.withOption("Add a Degree", PlanMgr::addDegree)
					.withOption("Remove a Degree", PlanMgr::removeDegree)
					.build();
	
	private static CmdMenu rootMenu = new CmdMenu.CmdMenuBuilder("Plan My Courses")
			.withOption("Choose Degrees", chooseDegrees)
			.withOption("Plan Semesters", PlanMgr::askCourseToPlan)
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
		CmdMenu.CmdMenuBuilder menuBuilder = new CmdMenu.CmdMenuBuilder("Select Semester to Move " + course.toString() + " to");
		
		Semester satisfied = Plan.INSTANCE.getSemesters().getSatisfied();
		menuBuilder.withOption(satisfied.getName(), () -> { Plan.INSTANCE.take(course, satisfied); });
		Semester unplanned = Plan.INSTANCE.getSemesters().getUnplanned();
		menuBuilder.withOption(unplanned.getName(), () -> { Plan.INSTANCE.take(course, unplanned); });
		for (Semester semester : Plan.INSTANCE.getSemesters()) {
			// TODO filter semester options based on validity of that course being taken
			menuBuilder.withOption(semester.getName(), () -> { Plan.INSTANCE.take(course, semester); });
		}
		
		menuBuilder.withNoRepeats().build().run();
	}
	
	// Prompt user to choose a course to plan a semester for
	private static void askCourseToPlan() {
		CmdMenu.CmdMenuBuilder menuBuilder = new CmdMenu.CmdMenuBuilder("Select a Course to assign to a Semester:")
				.withOnEnter(() -> { System.out.println(Plan.INSTANCE.toString()); });
		
		Course[] courses = Plan.INSTANCE.getAllCourses();
		Arrays.sort(courses);
		for (Course course : courses) {
			menuBuilder.withOption(course.toString(), () -> { askSemesterOfCourse(course); });
		}
		
		menuBuilder.build().run();
	}
	
	
	// --------------------------------
	//  Degree Mgt Methods
	// --------------------------------
	
	private static void printSelectedDegrees() {
		System.out.println("\nSelected Degrees:");
		for (Degree degree : Plan.INSTANCE.getDegrees()) {
			System.out.println("  " + degree);
		}
	}
	
	private static void addDegree() {
		CmdMenu.CmdMenuBuilder addDegreeBuilder = new CmdMenu.CmdMenuBuilder("Select a Degree to Add:");
		for (Degree degree : Catalog.getAllDegrees()) {
			if (!Plan.INSTANCE.hasDegree(degree)) {
				addDegreeBuilder.withOption(degree.toString(), () -> {
					Plan.INSTANCE.addDegree(degree);
					System.out.println("Added " + degree.getName());
				} ); // is 'degree' variable scope right to make this work?
			}
		}
		addDegreeBuilder.withNoRepeats().build().run();
	}
	
	private static void removeDegree() {
		CmdMenu.CmdMenuBuilder removeDegreeBuilder = new CmdMenu.CmdMenuBuilder("Select a Degree to Remove:");
		
		for (Degree degree : Plan.INSTANCE.getDegrees()) {
			removeDegreeBuilder.withOption(degree.toString(), () -> {
					Plan.INSTANCE.removeDegree(degree);
					System.out.println("Removed " + degree.getName());
				} );
		}
		
		removeDegreeBuilder.withNoRepeats().build().run();
	}
	
}
