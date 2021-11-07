package general;

import java.util.Arrays;
import java.util.Scanner;
import java.util.function.Consumer;

import catalog.Catalog;
import course.Course;
import degree.Degree;
import semesters.Semester;

/**
 * right now this is just for sandboxing
 * TODO write command line stuff that lets me use the system?
 * 
 * @author Quaris
 *
 */
public class Main {
	
	private static final Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		Plan plan = new Plan();
		
		// TODO much later
		CmdMenu degreeMgr = new CmdMenu.CmdMenuBuilder("Manage Degrees")
				.withOption("TODO", () -> {})
				.build();
		
		Runnable printSelectedDegrees = () -> {
			System.out.println("\nSelected Degrees:");
			for (Degree degree : plan.getDegrees()) {
				System.out.println("  " + degree);
			}
		};
		
		Runnable addDegree = () -> {
			CmdMenu.CmdMenuBuilder addDegreeBuilder = new CmdMenu.CmdMenuBuilder("Select a Degree to Add:");
			for (Degree degree : Catalog.getAllDegrees()) {
				if (!plan.hasDegree(degree)) {
					addDegreeBuilder.withOption(degree.toString(), () -> {
						plan.addDegree(degree);
						System.out.println("Added " + degree.getName());
					} ); // is 'degree' variable scope right to make this work?
				}
			}
			addDegreeBuilder.withNoRepeats().build().run();
		};
		
		Runnable removeDegree = () -> {
			CmdMenu.CmdMenuBuilder removeDegreeBuilder = new CmdMenu.CmdMenuBuilder("Select a Degree to Remove:");
			
			for (Degree degree : plan.getDegrees()) {
				removeDegreeBuilder.withOption(degree.toString(), () -> {
						plan.removeDegree(degree);
						System.out.println("Removed " + degree.getName());
					} ); // is 'degree' variable scope right to make this work? -- Answer: YES!!!
			}
			
			removeDegreeBuilder.withNoRepeats().build().run();
		};
		
		// Degree Choice menu, options to add or remove a degree
		CmdMenu chooseDegrees = new CmdMenu.CmdMenuBuilder("Manage My Degree Selections")
				.withOnEnter(printSelectedDegrees)
				.withOption("Add a Degree", addDegree)
				.withOption("Remove a Degree", removeDegree)
				.build();
		
		// Prompt user to choose a semester during which to plan the course they chose
		Consumer<Course> setSemesterOf = (course) -> {
			CmdMenu.CmdMenuBuilder menuBuilder = new CmdMenu.CmdMenuBuilder("Select Semester to Move " + course.toString() + "to");
			
			Semester satisfied = plan.getSemesters().getSatisfied();
			menuBuilder.withOption(satisfied.getName(), () -> { plan.take(course, satisfied); });
			Semester unplanned = plan.getSemesters().getUnplanned();
			menuBuilder.withOption(unplanned.getName(), () -> { plan.take(course, unplanned); });
			for (Semester semester : plan.getSemesters()) {
				menuBuilder.withOption(semester.getName(), () -> { plan.take(course, semester); });
			}
			
			menuBuilder.withNoRepeats().build().run();
		};
		
		// Prompt user to choose a course to plan a semester for
		Runnable planSemesters = () -> {
			CmdMenu.CmdMenuBuilder menuBuilder = new CmdMenu.CmdMenuBuilder("Plan Semester to Take:")
					.withOnEnter(() -> { System.out.println(plan.toString()); });
			
			Course[] courses = plan.getAllCourses();
			Arrays.sort(courses);
			for (Course course : courses) {
				menuBuilder.withOption(course.toString(), () -> { setSemesterOf.accept(course); });
			}
			
			menuBuilder.build().run();
					
		};
		
		// menu for using the planner
		CmdMenu planner = new CmdMenu.CmdMenuBuilder("Plan My Courses")
				.withOption("Choose Degrees", chooseDegrees)
				.withOption("Plan Semesters", planSemesters)
				.withOption("Edit Semester Settings", () -> {})
				.build();
		
		// main menu
		CmdMenu main = new CmdMenu.CmdMenuBuilder("Welcome to the Courses Planner")
				.withExitPhrase("Exit")
				.withOption("Courses Planner", planner)
				.withOption("Manage Degrees", degreeMgr)
				.build();
		
		main.run();
		
	}
}
