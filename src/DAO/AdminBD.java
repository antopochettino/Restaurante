package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AdminBD {

	public static Connection obtenerConexion(String driver) throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/restaurante?serverTimezone=UTC", "root", "");
	}

	public static Connection obtenerConexion(String driver, String url, String usr, String pass)
			throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		return DriverManager.getConnection(url, usr, pass);
	}
}