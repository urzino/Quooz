package quooz.server.database;

import java.sql.SQLException;

import javax.sql.DataSource;

import java.sql.Connection;

import org.apache.commons.dbcp.BasicDataSource;

import quooz.shared.DatabaseProblemException;

/**
 * The Class DatabaseConnection
 * manages the Database connections 
 * using the connection pooling.
 */
public class DatabaseConnection {

	/** The pool of connections. */
	private static DataSource dataSource = null;

	/**
	 * Instantiates a new database connection.
	 */
	public DatabaseConnection() {

	}

	/**
	 * Gets the connection from a connection pool.
	 *
	 * @return the connection
	 * @throws DatabaseProblemException 
	 */
	public static synchronized Connection getConnection() throws DatabaseProblemException {

		
		Connection connection = null;
		if (dataSource == null) {
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/dbquooz?autoReconnect=true&useSSL=false";
			String user = "root";
			String password = "root";
			dataSource = setupDataSource(driver, url, user, password);

		}
		try {
			connection = dataSource.getConnection();
		} catch (SQLException e) {			
			e.printStackTrace();
			throw new DatabaseProblemException();
		}

		return connection;
	}

	/**
	 * Setup data source.
	 *
	 * @param driver the driver
	 * @param url the url
	 * @param user the user
	 * @param password the password
	 * @return the data source
	 */
	private static DataSource setupDataSource(String driver, String url, String user, String password) {
		BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setDriverClassName(driver);
		basicDataSource.setUsername(user);
		basicDataSource.setPassword(password);
		basicDataSource.setUrl(url);
		basicDataSource.setMaxActive(10);
		return basicDataSource;
	}

	/**
	 * Shutdown data source.
	 *
	 * @param dataSource the data source
	 */
	public static void shutdownDataSource(DataSource dataSource) {
		BasicDataSource basicDataSource = (BasicDataSource) dataSource;
		try {
			basicDataSource.close();
		} catch (SQLException e) {
			System.out.println("E' stato riscontrato un problema nella creazione del BasicDataSource");
		}
	}

}
