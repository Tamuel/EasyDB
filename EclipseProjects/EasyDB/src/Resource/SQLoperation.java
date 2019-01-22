package Resource;

/**
 * Enumeration type for menus info
 * @author DongKyu
 *
 */
public enum SQLoperation {
	SELECT		(0, StringR.SELECT),
	FROM		(1, StringR.FROM),
	WHERE		(2, StringR.WHERE),
	GROUP_BY	(3, StringR.GROUP_BY),
	HAVING		(4, StringR.HAVING),
	ORDER_BY	(5, StringR.ORDER_BY),
	INSERT		(6, StringR.INSERT),
	DELETE		(7, StringR.DELETE),
	UPDATE		(8, StringR.UPDATE);

	private int value;
	private String text;
	
	private SQLoperation() {}
	
	private SQLoperation(int v, String t) {
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