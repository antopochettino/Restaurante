package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import restaurante.Categoria;

public class CategoriaDAO {

	public static void insertar(Categoria categoria, Connection connection) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("INSERT INTO CATEGORIAS (NOMBRE) VALUES(?)");
		stmt.setString(1, categoria.getNombre());
		stmt.executeUpdate();
	}

	public static List<Categoria> findAll(Connection connection) throws SQLException {
		List<Categoria> listaCategoria = new ArrayList<Categoria>();
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM CATEGORIAS");
		ResultSet rs = stmt.executeQuery();
		Categoria categoria = null;
		while (rs.next()) {
			categoria = new Categoria(rs.getInt("id"), rs.getString("nombre"));
			listaCategoria.add(categoria);
		}
		return listaCategoria;
	}

	public static void modificar(Categoria categoria, Connection connection) throws SQLException {
		PreparedStatement prepStmt = connection.prepareStatement("UPDATE CATEGORIAS SET NOMBRE = ? WHERE ID = ?");
		prepStmt.setString(1, categoria.getNombre());
		prepStmt.setInt(2, categoria.getId());
		prepStmt.executeUpdate();
	}

	public static void borrar(int idCategoria, Connection connection) throws SQLException {
		PreparedStatement prepStmt = connection.prepareStatement("DELETE FROM CATEGORIAS WHERE ID = ?");
		prepStmt.setInt(1, idCategoria);
		prepStmt.executeUpdate();
	}

	public static Categoria findById(int idCategoria, Connection connection) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM CATEGORIAS WHERE ID = ?");
		stmt.setInt(1, idCategoria);
		ResultSet rs = stmt.executeQuery();
		Categoria categoria = null;
		if (rs.next()) {
			categoria = new Categoria(rs.getInt("id"), rs.getString("nombre"));
		}
		return categoria;
	}
}
