package io;

import general.Plan;

/**
 * Command Line stuff that lets me use the system
 * 
 * @author Quaris
 *
 */
public class Main {
	
	public static final Plan PLAN = new Plan();
	
	public static void main(String[] args) {
		
		CmdMenu mainMenu = new CmdMenu.CmdMenuBuilder("Welcome to Plan-My-Semesters")
				.withExitPhrase("Exit")
				.withOption("My Semesters Planner", PlanMgr::run)
				.withOption("Manage Degrees and their Requirements", DegreesMgr::run)
				.build();
		
		mainMenu.run();
	}
}
