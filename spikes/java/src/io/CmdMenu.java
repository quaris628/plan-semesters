/**
 * 
 */
package io;

import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * @author Quaris
 *
 */
public class CmdMenu implements Runnable {

	public static final String DEFAULT_EXIT_MENU_NAME = "Back";
	public static final String DEFAULT_PROMPT = "";
	public static final String INVALID_INPUT_MESSAGE =
			"Enter a number corresponding to an element in the list above";
	
	private Runnable onEnter;
	private String title;
	private String exitPhrase;
	private String[] optionTitles;
	private Runnable[] options;
	private String prompt;
	private Scanner sc;
	private boolean noRepeats;
	
	
	private CmdMenu(CmdMenuBuilder b) {
		this.onEnter = b.onEnter;
		this.title = b.title;
		this.exitPhrase = b.exitPhrase;
		this.optionTitles = b.optionTitles.toArray(new String[b.optionTitles.size()]);
		this.options = b.options.toArray(new Runnable[b.options.size()]);
		this.prompt = b.prompt;
		this.sc = new Scanner(System.in);
		this.noRepeats = b.noRepeats;
	}
	
	@Override
	public void run() {
		do {
			for (int i = 0; i < 50; i++) {
				System.out.println();
			}
			onEnter.run();
			printTitles();
			System.out.print(prompt);
			int choice = askUserChoice();
			if (choice == 0) {
				break;
			}
			options[choice - 1].run();
		} while(!noRepeats);
	}
	
	private void printTitles() {
		System.out.println(title);
		System.out.println(" 0 - " + exitPhrase);
		for (int i = 0; i < optionTitles.length; i++) {
			System.out.println(" " + String.valueOf(i + 1) + " - " + optionTitles[i]);
		}
	}
	
	private int askUserChoice() {
		int choice = -1;
		boolean choiceValid = false;
		while (!choiceValid) {
			try {
				choice = sc.nextInt();
				choiceValid = 0 <= choice && choice <= optionTitles.length;
			} catch (InputMismatchException e) {
				choiceValid = false;
			}
			sc.nextLine();
			if (!choiceValid) {
				System.out.println(INVALID_INPUT_MESSAGE);
			}
		}
		return choice;
	}
	
	
	// Builder Pattern
	public static class CmdMenuBuilder {
	    
		private Runnable onEnter;
		private String title;
		private String exitPhrase;
	    private LinkedList<String> optionTitles;
	    private LinkedList<Runnable> options;
	    private String prompt;
	    private boolean noRepeats;
	    
	    public CmdMenuBuilder(String title) {
	        this.onEnter = () -> {};
	    	this.title = title;
	        this.exitPhrase = DEFAULT_EXIT_MENU_NAME;
	        this.optionTitles = new LinkedList<String>();
	        this.options = new LinkedList<Runnable>();
	        this.prompt = "";
	        this.noRepeats = false;
	    }
	    
	    public CmdMenuBuilder withOption(String title, Runnable option) {
	        optionTitles.addLast(title);
	        options.addLast(option);
	        return this;
	    }
	    
	    /**
	     * Runs just before each time the menu options are displayed
	     * @param action
	     * @return
	     */
	    public CmdMenuBuilder withOnEnter(Runnable onEnter) {
	    	this.onEnter = onEnter;
	    	return this;
	    }
	    
	    public CmdMenuBuilder withPrompt(String prompt) {
	    	this.prompt = prompt;
	    	return this;
	    }
	    
	    public CmdMenuBuilder withExitPhrase(String exitPhrase) {
	    	this.exitPhrase = exitPhrase;
	    	return this;
	    }
	    
	    /**
	     * If this option enabled, menu only runs once and does not re-run after
	     *     one option is chosen
	     * @return
	     */
	    public CmdMenuBuilder withoutRepeats() {
	    	this.noRepeats = true;
	    	return this;
	    }
	    
	    public CmdMenu build() {
	        return new CmdMenu(this);
	    }
	    
	}

}
