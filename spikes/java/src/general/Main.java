package general;

import java.util.Scanner;

import catalog.Catalog;
import degree.Degree;

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
						System.out.println(degree.getName() + " Added");
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
						System.out.println(degree.getName() + " Removed");
					} ); // is 'degree' variable scope right to make this work?
			}
			removeDegreeBuilder.withNoRepeats().build().run();
		};
		
		CmdMenu chooseDegrees = new CmdMenu.CmdMenuBuilder("Manage My Degree Selections")
				.withOnEnter(printSelectedDegrees)
				.withOption("Add a Degree", addDegree)
				.withOption("Remove a Degree", removeDegree)
				.build();
		
		CmdMenu planner = new CmdMenu.CmdMenuBuilder("Plan My Courses")
				.withOption("Choose Degrees", chooseDegrees)
				.withOption("Plan Courses", () -> {})
				.withOption("Edit Semester Settings", () -> {})
				.build();
		
		CmdMenu main = new CmdMenu.CmdMenuBuilder("Welcome to the Courses Planner")
				.withExitPhrase("Exit")
				.withOption("Courses Planner", planner)
				.withOption("Manage Degrees", degreeMgr)
				.build();
		
		main.run();
		
	}
}
