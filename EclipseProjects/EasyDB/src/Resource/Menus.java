package Resource;

/**
 * Enumeration type for menus info
 * @author DongKyu
 *
 */
public enum Menus {
	TABLES		(0, StringR.TABLES),
	DIAGRAM		(1, StringR.DIAGRAM);

	private int value;
	private String text;
	
	private Menus() {}
	
	private Menus(int v, String t) {
		value = v;
		text = t;
	}
	
	public int getInt() {
		return value;
	}
	
	public String getString() {
		return text;
	}
}