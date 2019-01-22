package Resource;

public class DataProvider {
	/** For Singleton design pattern */
	private static DataProvider singleton = null;
	
	private DataProvider() {
	}

	/** For Singleton design.
	 * This function provide get instance of singleton */
	public static DataProvider getInstance()
	{ 
		if(singleton == null)
		{
			singleton  = new DataProvider();
		}
		return singleton;
	}
}
