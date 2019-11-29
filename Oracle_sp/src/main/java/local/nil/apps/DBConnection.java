package local.nil.apps;

import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class DBConnection {

	private static Logger logger = Logger.getLogger(DBConnection.class);

	public DBConnection() {
		BasicConfigurator.configure();
	}

	public Connection getOracleConnection() {
		/* Declare and initialize a sql Connection variable. */
		Connection connection = null;
		try {

			/* Register jdbc driver class. */
			Class.forName("oracle.jdbc.driver.OracleDriver");

			/* Create connection url. */
			String connUrl = "jdbc:oracle:thin:@localhost:1521:orcl";

			/* user name. */
			String userName = "nailesh";

			/* password. */
			String password = "nailesh";

			/* Get the Connection object. */
			connection = DriverManager.getConnection(connUrl, userName, password);

		} catch (Exception ex) {
			logger.error("Db connection error..." + ex);
		}
		return connection;
	}

}