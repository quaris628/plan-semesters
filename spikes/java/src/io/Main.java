package io;

/**
 * Command Line stuff that lets me use the system
 * 
 * @author Quaris
 *
 */
public class Main {
	
	public static void main(String[] args) {
		
		CmdMenu mainMenu = new CmdMenu.CmdMenuBuilder("Welcome to Plan-My-Semesters")
				.withExitPhrase("Exit")
				.withOption("Courses Planner", PlanMgr::run)
				.withOption("Manage Degrees", DegreesMgr::run)
				.build();
		
		mainMenu.run();
	}
}
