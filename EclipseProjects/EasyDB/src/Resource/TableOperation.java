package Resource;

/**
 * Enumeration type for menus info
 * @author DongKyu
 *
 */
public enum TableOperation {
	SEEK_DATA	(0, StringR.SEEK_DATA),
	ADD_DATA	(1, StringR.ADD_DATA),
	DELETE_DATA	(2, StringR.DELETE_DATA),
	UPDATE_DATA	(3, StringR.UPDATE_DATA);

	private int value;
	private String text;
	
	private TableOperation() {}
	
	private TableOperation(int v, String t) {
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