package dataBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.swing.JOptionPane;

import gui.utils.GeraLogUtils;
import gui.utils.IdentificadorSoUtils;
import model.exceptions.DbException;

public class DbUtils {
	
	public static Connection conn = null;
	
	public static Connection getConnection(){
		if(conn == null) {
				Properties props = loadProperties();
				String url = props.getProperty("dburl");
				try {
					conn = DriverManager.getConnection(url, props);
				} catch (SQLException e) {
					GeraLogUtils.gravarLogQuery("ERRO "+e.getMessage());
					JOptionPane.showMessageDialog(null, e.getMessage()+
							"\n\nAperte enter para fechar a mensagem e encerrar seu programa!",
							"Problemas com a conexão", JOptionPane.ERROR_MESSAGE);
					System.exit(0);
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
		String caminho = System.getProperty("user.home")+File.separatorChar+"Documents"+File.separatorChar+"JEF_DATA"+ File.separatorChar+"db.properties";
		if (IdentificadorSoUtils.sistema().equals("linux")){
				caminho = System.getProperty("user.home")+File.separatorChar+"Documentos"+File.separatorChar+"JEF_DATA"+ File.separatorChar+"db.properties";
		}
		try(FileInputStream fs = new FileInputStream(caminho)){
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
