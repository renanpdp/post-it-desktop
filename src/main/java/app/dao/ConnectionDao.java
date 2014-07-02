package app.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import app.util.ResourcesUtil;

public class ConnectionDao {
	
	private static final String DB_DRIVER = ResourcesUtil.getValue(ResourcesUtil.DATABASE_PROPERTIES, "mysql.driver");
	private static final String DB_URL = ResourcesUtil.getValue(ResourcesUtil.DATABASE_PROPERTIES, "mysql.url");
	private static final String DB_NAME = ResourcesUtil.getValue(ResourcesUtil.DATABASE_PROPERTIES, "mysql.database");
	private static final String DB_USER = ResourcesUtil.getValue(ResourcesUtil.DATABASE_PROPERTIES, "mysql.user");
	private static final String DB_PASSWORD = ResourcesUtil.getValue(ResourcesUtil.DATABASE_PROPERTIES, "mysql.password");

	/**
	 * Method that returns a connection to the database.
	 * 
	 * @return {@link Connection}
	 */
	public static Connection getConnection() {

		Connection conn = null;

		try {
			Class.forName(DB_DRIVER);
			conn = DriverManager.getConnection(DB_URL + DB_NAME, DB_USER, DB_PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return conn;

	}
	
}
