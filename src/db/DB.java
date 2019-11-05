package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import model.exceptions.DbException;

public class DB {
	
	public static Connection conn = null;
	
	public static Connection getConnection() {
		if(conn == null) {
			try {
				Properties props = loadProperties();
				String url = props.getProperty("dburl");
				conn = DriverManager.getConnection(url, props);
			}
			catch(SQLException e) {
				throw new DbException(e.getMessage());
			}	
		}
		return conn;
	}
	
	public static void closeConnection() {
		if(conn != null) {
			try {
				conn.close();
				conn = null;	
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static Properties loadProperties() {
		try(FileInputStream fs = new FileInputStream("C:\\Users\\leonardo.petta\\Desktop\\db.properties")){
			Properties props = new Properties();
			props.load(fs);
			return props;
		}
		catch(IOException e) {
			throw new DbException(e.getMessage());
		}
	}
	
	public static void fechaStatement(Statement st) {
		if(st != null) {
			try {
				st.close();
				st = null;
			}
			catch(SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	
	public static void fechaResultSet(ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
				rs = null;
			}
			catch(SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
}
