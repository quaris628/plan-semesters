/**
 * 
 */
package io;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * A Menu for programs with Command Prompt i/o
 * Displays options to the user, then lets the user choose an option by typing its number
 * Intended to be highly reusable across multiple projects
 * 
 * @author Quaris
 */
public class CmdMenu implements Runnable {

	public static final String DEFAULT_EXIT_MENU_NAME = "Back";
	public static final String DEFAULT_PROMPT = "";
	public static final String INVALID_INPUT_MESSAGE =
			"Enter a number corresponding to an element in the list above";
	// for clearing the command output before each menu run
	public static final int NUM_NEWLINES_WHEN_CLEARING = 50;
	
	private Scanner sc;
	private PrintStream out;
	
	private Runnable onStart;
	private String title;
	private String exitPhrase;
	private CmdMenuOption[] menuOptions;
	private String prompt;
	private boolean noRepeats;
	private boolean noClearing;
	
	
	private CmdMenu(CmdMenuBuilder b) {
		this.sc = new Scanner(b.in);
		this.out = b.out;
		
		this.onStart = b.onStart;
		this.title = b.title;
		this.exitPhrase = b.exitPhrase;
		this.menuOptions = b.menuOptions.toArray(new CmdMenuOption[b.menuOptions.size()]);
		this.prompt = b.prompt;
		this.noRepeats = b.noRepeats;
		this.noClearing = b.noClearing;
	}
	
	/**
	 * Runs this menu
	 * 
	 * This is the more detailed procedure of running the menu:
	 * 1. Prints the options available to the user
	 * 2. Asks the user to select an option, continually prompting until
	 *     a valid option is chosen
	 * 3. Runs the action associated with the selected option
	 *     (except if the user selected to exit the menu)
	 * 4. Repeats from step 1, unless user selected to exit the menu.
	 * 
	 * Other configurable options may override the above behavior.
	 * See the CmdMenuBuilder options' descriptions for more details.
	 */
	@Override
	public void run() {
		int choice;
		do {
			if (!noClearing) {
				clearOutput();
			}
			onStart.run();
			printOptions();
			choice = askUserChoice();
			if (choice != 0) {
				menuOptions[choice - 1].run();
			}
			
		} while(choice != 0 && !noRepeats);
	}
	
	private void clearOutput() {
		for (int i = 0; i < NUM_NEWLINES_WHEN_CLEARING ; i++) {
			out.println();
		}
	}
	
	private void printOptions() {
		out.println(title);
		out.println(" 0 - " + exitPhrase);
		for (int i = 0; i < menuOptions.length; i++) {
			out.println(" " + String.valueOf(i + 1) + " - " + menuOptions[i].toString());
		}
	}
	
	private int askUserChoice() {
		int choice = -1;
		boolean choiceValid = false;
		while (!choiceValid) {
			try {
				out.print(prompt);
				choice = sc.nextInt();
				choiceValid = 0 <= choice && choice <= menuOptions.length;
			} catch (InputMismatchException e) {
				choiceValid = false;
			}
			sc.nextLine(); // move scanner cursor over the line break
			if (!choiceValid) {
				out.println(INVALID_INPUT_MESSAGE);
			}
		}
		return choice;
	}
	
	
	// Builder Pattern
	public static class CmdMenuBuilder {
	    
		private InputStream in;
		private PrintStream out;
		
		private Runnable onStart;
		private String title;
		private String exitPhrase;
		private LinkedList<CmdMenuOption> menuOptions;
	    private String prompt;
	    private boolean noRepeats;
	    private boolean noClearing;
	    
	    /**
	     * Creates a new CmdMenu builder (Builder Design Pattern)
	     * Requires only a title, though adding options to select is strongly recommended.
	     * @param title to display at the head of the list of options
	     */
	    public CmdMenuBuilder(String title) {
	        this.in = System.in;
	        this.out = System.out;
	    	
	    	this.onStart = () -> {};
	    	this.title = title;
	        this.exitPhrase = DEFAULT_EXIT_MENU_NAME;
	        this.menuOptions = new LinkedList<CmdMenuOption>();
	        this.prompt = "";
	        this.noRepeats = false;
	        this.noClearing = false;
	    }
	    
	    /**
	     * Add an option to the menu
	     * An option needs a title and an action that will be performed if
	     *     the option is selected.
	     * @param title of the option
	     * @param action to perform if option is selected
	     * @return this
	     */
	    public CmdMenuBuilder withOption(String title, Runnable action) {
	        menuOptions.addLast(new CmdMenuOption(title, action));
	        return this;
	    }
	    
	    /**
	     * Add an option to the menu
	     * An option needs a title and an action that will be performed if
	     *     the option is selected.
	     * @param option to add to the menu
	     * @return this
	     */
	    public CmdMenuBuilder withOption(CmdMenuOption option) {
	    	menuOptions.addLast(option);
	    	return this;
	    }
	    
	    /**
	     * Add an action to be performed before the menu options are displayed,
	     *     such as printing other text
	     * @param onStart action to be run on entering the menu
	     * @return this
	     */
	    public CmdMenuBuilder withOnEnter(Runnable onStart) {
	    	this.onStart = onStart;
	    	return this;
	    }
	    
	    /**
	     * Set a custom prompt to display for the user's input
	     * Displayed below the list of options in the menu
	     * Default ""
	     * @param prompt to display for the user input, below the menu options list
	     * @return this
	     */
	    public CmdMenuBuilder withPrompt(String prompt) {
	    	this.prompt = prompt;
	    	return this;
	    }
	    
	    /**
	     * Phrase to be used for the option to not select any option and
	     *     exit this menu
	     * Default "Back"
	     * @param exitPhrase
	     * @return this
	     */
	    public CmdMenuBuilder withExitPhrase(String exitPhrase) {
	    	this.exitPhrase = exitPhrase;
	    	return this;
	    }
	    
	    /**
	     * If this option is enabled, the menu only runs once and does not
	     *     re-run after one option is chosen
	     * @return this
	     */
	    public CmdMenuBuilder withoutRepeats() {
	    	this.noRepeats = true;
	    	return this;
	    }
	    
	    /**
	     * If this option is enabled, the output is not cleared (by printing many new lines)
	     *     each time the menu is ran
	     * @return this
	     */
	    public CmdMenuBuilder withoutClearing() {
	    	this.noClearing = true;
	    	return this;
	    }
	    
	    /**
	     * Sets custom input stream for the menu to use
	     * Default System.in
	     * @param input stream
	     * @return this
	     */
	    public CmdMenuBuilder withInput(InputStream input) {
	    	this.in = input;
	    	return this;
	    }
	    
	    /**
	     * Sets custom output print stream for the menu to use
	     * Default System.out
	     * @param output print stream
	     * @return this
	     */
	    public CmdMenuBuilder withOutput(PrintStream output) {
	    	this.out = output;
	    	return this;
	    }
	    
	    /**
	     * Creates a new CmdMenu instance, using the parameters given to this CmdMenuBuilder
	     * @return new CmdMenu instance
	     */
	    public CmdMenu build() {
	        return new CmdMenu(this);
	    }
	    
	}

}
