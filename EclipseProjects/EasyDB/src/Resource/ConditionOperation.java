package Resource;

/**
 * Enumeration type for menus info
 * @author DongKyu
 *
 */
public enum ConditionOperation {
	EQUAL					(0, StringR.EQUAL, "="),
	LARGER_THAN				(1, StringR.LARGER_THAN, ">"),
	SMALLER_THAN			(2, StringR.SMALLER_THAN, "<"),
	EQUAL_OR_LARGER_THAN	(3, StringR.EQUAL_OR_LARGER_THAN, ">="),
	EQUAL_OR_SMALLER_THAN	(4, StringR.EQUAL_OR_SMALLER_THAN, "<=");

	private int value;
	private String text;
	private String operation;
	
	private ConditionOperation() {}
	
	private ConditionOperation(int v, String t, String o) {
		value = v;
		text = t;
		operation = o;
	}
	
	public int getInt() {
		return value;
	}
	
	public String getString() {
		return text;
	}
	
	public String getOperation() {
		return operation;
	}
}