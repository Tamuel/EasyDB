package Resource;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import GUI.Frames.MainFrame.CautionFrame;

public class DBProvider {
	/** For Singleton design pattern */
	private static DBProvider singleton = null;
	/** For save tableNames */
	private static ArrayList<String> tableNames;
	/** For save current table attribute names */
	private static ArrayList<String> attributesNames;
	/** For get query result */
	private static ArrayList<String> queryStrings;
	
	/** For JDBC */
	private String url = "jdbc:oracle:thin:@localhost:1521:oraknu";
	private String user = "kdhong";
	private String pass = "kdhong";
	private String table_name = "emp";
	private String attr1 = "id";
	private String attr1_type = "number";
	private String attr2 = "ssn";
	private String attr2_type = "number";
	private String constraint_name = "constraint_name";
	private Connection conn = null;
	private String sql = null;
	private String query = null;
	private int result;
	
	private int currentColumnNumber = 0;
	
	private DBProvider() {
		tableNames = new ArrayList<String>();
		attributesNames = new ArrayList<String>();
		queryStrings = new ArrayList<String>();
		
		// connection
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("드라이버 검색 성공!");
		}catch(ClassNotFoundException e){
			System.err.println("error = " + e.getMessage());
			System.exit(1);
		}
		try{
			conn = DriverManager.getConnection(url, user, pass);
		}catch(SQLException e){
			System.err.println("sql error = " + e.getMessage());
			System.exit(1);
		}
	}
	
	public void getTableNamesQuery() {
		try{
			conn.setAutoCommit(false);
			Statement stmt = conn.createStatement();
			
			query = "SELECT table_name FROM " +
					"user_tables";
			ResultSet rs = stmt.executeQuery(query);
			tableNames.clear();
			while (rs.next())
			{
				String name = rs.getString("table_name");
				tableNames.add(name);
				System.out.println("TABLE NAME : " + name);
			}
			
			rs.close();
			conn.commit();
		} catch(Exception e){
			System.err.println("sql error = " + e.getMessage());
		}
	}
	
	public void dropTableQuery(String tableName) {
		try{
			conn.setAutoCommit(false);
			Statement stmt = conn.createStatement();			
			
			result = stmt.executeUpdate("DROP TABLE " + tableName);

			conn.commit();
		} catch(Exception e){
			System.err.println("sql error = " + e.getMessage());
		}
	}
	
	public void getTableAttrubutesQuery(String tableName) {
		try{
			Statement statement = conn.createStatement();
			ResultSet results = statement.executeQuery("SELECT * FROM " + tableName);
			
			ResultSetMetaData metadata = results.getMetaData();
			int columnCount = metadata.getColumnCount();
			attributesNames.clear();
			for (int i = 1; i<= columnCount; i++) {
				String columnName = metadata.getColumnName(i);
				System.out.println(columnName);
				attributesNames.add(columnName);
			}
		} catch(Exception e){
			System.err.println("sql error = " + e.getMessage());
		}
	}
	
	public void createQuery(String query) {
		try{
			conn.setAutoCommit(false);
			Statement stmt = conn.createStatement();
			
			result = stmt.executeUpdate(query);
			
			conn.commit();
		} catch(Exception e) {
			System.err.println("sql error = " + e.getMessage());
		}
	}

	public void insertQuery(String query) {
		try{
			conn.setAutoCommit(false);
			Statement stmt = conn.createStatement();			
			
			result = stmt.executeUpdate(query);

			conn.commit();
			new CautionFrame(
					StringR.CAUTION,
					StringR.INSERT_SUCCESS,
					DimenR.CAUTION_FRAME_WIDTH,
					DimenR.CAUTION_FRAME_HEIGHT
					);
		} catch(Exception e){
			System.err.println("sql error = " + e.getMessage());
			new CautionFrame(
					StringR.CAUTION,
					StringR.INSERT_FAIL + "\n" + e.getMessage(),
					DimenR.CAUTION_FRAME_WIDTH,
					DimenR.CAUTION_FRAME_HEIGHT
					);
		}

	}
	
	public void deleteQuery(String query) {
		try{
			conn.setAutoCommit(false);
			Statement stmt = conn.createStatement();
			
			result = stmt.executeUpdate(query);
			
			conn.commit();
			new CautionFrame(
					StringR.CAUTION,
					StringR.DELETE_SUCCESS,
					DimenR.CAUTION_FRAME_WIDTH,
					DimenR.CAUTION_FRAME_HEIGHT
					);
		} catch(Exception e) {
			System.err.println("sql error = " + e.getMessage());			
			new CautionFrame(
					StringR.CAUTION,
					StringR.DELETE_FAIL + "\n" + e.getMessage(),
					DimenR.CAUTION_FRAME_WIDTH,
					DimenR.CAUTION_FRAME_HEIGHT
					);
		}

	}
	
	public void updateQuery(String query) {
		try{
			conn.setAutoCommit(false);
			Statement stmt = conn.createStatement();
			
			result = stmt.executeUpdate(query);
			
			conn.commit();
			new CautionFrame(
					StringR.CAUTION,
					StringR.UPDATE_SUCCESS,
					DimenR.CAUTION_FRAME_WIDTH,
					DimenR.CAUTION_FRAME_HEIGHT
					);
		} catch(Exception e) {
			System.err.println("sql error = " + e.getMessage());
			new CautionFrame(
					StringR.CAUTION,
					StringR.UPDATE_FAIL + "\n" + e.getMessage(),
					DimenR.CAUTION_FRAME_WIDTH,
					DimenR.CAUTION_FRAME_HEIGHT
					);
		}

	}
	
	public void selectQuery(String query, String attributes) {
		try {
			queryStrings.clear();
			conn.setAutoCommit(false);
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery(query);
			currentColumnNumber = 0;
			
			if(attributes.equals("*")) {
				currentColumnNumber = getAttributesNames().size();
				queryStrings.addAll(getAttributesNames());
				for(String temp : queryStrings)
					System.out.println("ATTR : " + temp);
					
			} else {
				String attr[] = attributes.split(", ");
				currentColumnNumber = attr.length;
				for(String temp : attr) {
					queryStrings.add(temp);
					System.out.println("ATTR : " + temp);
				}
			}
			
			while (rs.next())
			{
				for(int i = 1; i <= currentColumnNumber; i++){
					String temp = rs.getNString(i);
					queryStrings.add(temp);
					System.out.print(temp + " ");
				}
				System.out.print("\n");
			}
			
			rs.close();
			conn.commit();
			new CautionFrame(
					StringR.CAUTION,
					StringR.SEEK_SUCCESS,
					DimenR.CAUTION_FRAME_WIDTH,
					DimenR.CAUTION_FRAME_HEIGHT
					);
			
		} catch(Exception e) {
			e.printStackTrace();
			System.err.println("sql error = " + e.getMessage());
			new CautionFrame(
					StringR.CAUTION,
					StringR.SEEK_FAIL + "\n" + e.getMessage(),
					DimenR.CAUTION_FRAME_WIDTH,
					DimenR.CAUTION_FRAME_HEIGHT
					);
		}

	}


	public int getCurrentColumnNumber() {
		return currentColumnNumber;
	}

	public void setCurrentColumnNumber(int currentColumnNumber) {
		this.currentColumnNumber = currentColumnNumber;
	}

	public static ArrayList<String> getQueryStrings() {
		return queryStrings;
	}

	public static void setQueryStrings(ArrayList<String> queryStrings) {
		DBProvider.queryStrings = queryStrings;
	}

	public static ArrayList<String> getAttributesNames() {
		return attributesNames;
	}

	public static void setAttributesNames(ArrayList<String> attributesNames) {
		DBProvider.attributesNames = attributesNames;
	}

	public static ArrayList<String> getTableNames() {
		return tableNames;
	}

	public static void setTableNames(ArrayList<String> tableNames) {
		DBProvider.tableNames = tableNames;
	}

	/** For Singleton design.
	 * This function provide get instance of singleton */
	public static DBProvider getInstance()
	{ 
		if(singleton == null)
		{
			singleton  = new DBProvider();
		}
		return singleton;
	}
}
