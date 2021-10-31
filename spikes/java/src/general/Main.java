package general;

import java.util.InputMismatchException;
import java.util.Scanner;

import catalog.Catalog;
import course.CPreReq;
import course.CReqAnd;
import course.Course;
import degree.*;

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
		
		while (true) {
			int mainChoice = runMenu("Welcome to the Courses Planner", new String[] {
					"Plan My Courses",
					"Edit Degrees",
					"Exit"});
			if (mainChoice == 0) { // Plan My Courses
				while (true) {
					int planChoice = runMenu("Plan My Courses", new String[] {
							"Choose Degrees",
							"Plan Course Enrollment",
							"Edit Semester Settings",
							"Back"});
					if (planChoice == 0) { // Choose Degrees
						while (true) {
							System.out.println("Currently Selected Degrees:");
							for (Degree degree : plan.getDegrees()) {
								System.out.println("\t" + degree);
							}
							int degreeManageChoice = runMenu("Manage My Degree Selections", new String[] {
									"Add a Degree",
									"Remove a Degree",
									"Back"});
							if (degreeManageChoice == 0) {
								System.out.println("Available Degrees:");
								for (Degree degree : Catalog.getAllDegrees()) {
									if (!plan.hasDegree(degree)) {
										System.out.println("\t" + degree);
									}
								}
								System.out.println("Enter the name of the degree:");
								String degreeName = sc.nextLine();
								Degree degreeToAdd = Catalog.getDegreeByName(degreeName);
								if (degreeToAdd == null) {
									System.out.println("No degree with the name '" + degreeName + "' was found");
								} else {
									plan.addDegree(degreeToAdd);
									System.out.println(degreeToAdd.getName() + " Added");
								}
							} else if (degreeManageChoice == 1) {
								System.out.println("Enter the name of the degree:");
								String degreeName = sc.nextLine();
								Degree degreeToRemove = Catalog.getDegreeByName(degreeName);
								if (degreeToRemove == null) {
									System.out.println("No degree with the name '" + degreeName + "' was found");
								} else {
									plan.removeDegree(degreeToRemove);
									System.out.println(degreeToRemove.getName() + " Added");
								}
							} else {
								break;
							}
						}
					} else if (planChoice == 1) { // Plan Course Enrollment
						
					} else if (planChoice == 2) { // Edit Semester Settings
						
					} else { // Exit
						break;
					}
				}
			} else if (mainChoice == 1) { // Edit Degrees
				System.out.println("Coming later");
			} else { // Exit
				break;
			}
		}
	}
	
	private static int runMenu(String message, String[] options) {
		System.out.println();
		System.out.println(message);
		for (int i = 0; i < options.length; i++) {
			System.out.println(" " + String.valueOf(i) + " - " + options[i]);
		}
		int choice = -1;
		boolean choiceValid = false;
		while (!choiceValid) {
			try {
				choice = sc.nextInt();
				choiceValid = 0 <= choice && choice < options.length;
			} catch (InputMismatchException e) {
				choiceValid = false;
			}
			sc.nextLine();
			if (!choiceValid) {
				System.out.println("Enter a number corresponding to an element in the list above");
			}
		}
		return choice;
	}

}
