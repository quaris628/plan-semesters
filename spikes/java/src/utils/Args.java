/**
 * 
 */
package utils;

/**
 * @author Quaris
 *
 */
public class Args {
	
	private Args() { }

	public static void checkNull(Object arg, String name) {
		if (arg == null) {
			throw new IllegalArgumentException(name + " cannot be null");
		}
	}
	
	public static void checkNullArr(Object[] arg, String name) {
		Args.checkNull(arg, name);
		for (int i = 0; i < arg.length; i++) {
			Args.checkNull(arg[0], name + "[" + String.valueOf(i) + "]");
		}
	}
	
	public static void checkNonNegative(int arg, String name) {
		if (arg < 0) {
			throw new IllegalArgumentException(name + " cannot be negative");
		}
	}
	
}
