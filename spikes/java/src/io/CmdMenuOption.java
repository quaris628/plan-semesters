/**
 * 
 */
package io;

import utils.Args;

/**
 * 
 * @author Quaris
 *
 */
public class CmdMenuOption implements Runnable {

	private String title;
	private Runnable onSelectAction;

	public CmdMenuOption(String title, Runnable onSelectAction) {
		Args.checkNull(onSelectAction, "onSelectAction");
		this.title = title;
		this.onSelectAction = onSelectAction;
	}
	
	public void run() {
		onSelectAction.run();
	}

	@Override
	public String toString() {
		return title;
	}
}
