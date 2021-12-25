/**
 * 
 */
package control;

import io.CmdMenu;

/**
 * I/O related to user's managing the library of degrees and their requirements
 * Fully static, essentially a singleton
 * 
 * @author Quaris
 *
 */
public class DegreesMgr {

	private DegreesMgr() { }
	
	private static CmdMenu rootMenu = new CmdMenu.CmdMenuBuilder("Manage Degrees")
			.withOption("TODO", () -> {})
			.build();
	
	public static void run() {
		rootMenu.run();
	}

}
