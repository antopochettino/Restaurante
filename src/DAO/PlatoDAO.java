package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import restaurante.Categoria;
import restaurante.Plato;

public class PlatoDAO {

	public static void insertar(Plato plato, Connection connection) throws SQLException {
		PreparedStatement stmt = connection
				.prepareStatement("INSERT INTO PLATOS (NOMBRE, PRECIO, ID_CATEGORIA) VALUES(?,?,?)");
		stmt.setString(1, plato.getNombre());
		stmt.setDouble(2, plato.getPrecio());
		stmt.setInt(3, plato.getIdCategoria());
		stmt.executeUpdate();
	}

	public static Plato findById(int id, Connection connection) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM PLATOS WHERE ID = ?");
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();
		Plato plato = null;
		if (rs.next()) {
			plato = new Plato(rs.getInt("id"), rs.getString("nombre"), rs.getDouble("precio"),
					rs.getInt("id_categoria"));
		}
		return plato;
	}

	public static void borrar(int id, Connection connection) throws SQLException {
		PreparedStatement prepStmt = connection.prepareStatement("DELETE FROM PLATOS WHERE ID = ?");
		prepStmt.setInt(1, id);
		prepStmt.executeUpdate();

	}

	public static void modificarNombre(Plato plato, Connection connection) throws SQLException {
		PreparedStatement prepStmt = connection.prepareStatement("UPDATE PLATOS SET NOMBRE = ? WHERE ID = ?");
		prepStmt.setString(1, plato.getNombre());
		prepStmt.setInt(2, plato.getId());
		prepStmt.executeUpdate();

	}

	public static void modificarPrecio(Plato plato, Connection connection) throws SQLException {
		PreparedStatement prepStmt = connection.prepareStatement("UPDATE PLATOS SET PRECIO = ? WHERE ID = ?");
		prepStmt.setDouble(1, plato.getPrecio());
		prepStmt.setInt(2, plato.getId());
		prepStmt.executeUpdate();

	}

	public static List<Plato> findAll(Connection connection) throws SQLException {
		List<Plato> listaPlato = new ArrayList<Plato>();
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM PLATOS");
		ResultSet rs = stmt.executeQuery();
		Plato plato = null;
		while (rs.next()) {
			plato = new Plato(rs.getInt("id"), rs.getString("nombre"), rs.getDouble("precio"),
					rs.getInt("id_categoria"));
			listaPlato.add(plato);
		}
		return listaPlato;
	}

	public static double buscarPrecio(int id, Connection connection) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM PLATOS WHERE ID = ?");
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();
		double precio = 0;
		if (rs.next()) {
			precio = rs.getDouble("precio");
		}
		return precio;
	}

	public static String buscarNombre(int id, Connection connection) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM PLATOS WHERE ID = ?");
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();
		String nombre = "";
		if (rs.next()) {
			nombre = rs.getString("nombre");
		}
		return nombre;
	}

	public static void platoJoinCategoria(Connection connection) throws SQLException {
		Map<Plato, Categoria> m = new HashMap<Plato, Categoria>();
		PreparedStatement stmt = connection
				.prepareStatement("SELECT * FROM PLATOS JOIN CATEGORIAS WHERE PLATOS.ID = CATEGORIAS.ID");
		ResultSet rs = stmt.executeQuery();
		Plato plato = null;
		Categoria categoria = null;
		while (rs.next()) {
			plato = new Plato(rs.getString("nombre"));
			categoria = new Categoria(rs.getString("nombre"));
			m.put(plato, categoria);
		}

		for (Entry<Plato, Categoria> entry : m.entrySet()) {
			System.out.println(entry.getKey() + " --> " + entry.getValue());
		}
	}
}
