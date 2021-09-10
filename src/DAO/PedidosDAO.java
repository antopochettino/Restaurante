package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import restaurante.Pedidos;

public class PedidosDAO {

	public static void insertar(Pedidos pedido, Connection connection) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("INSERT INTO PEDIDOS (ID_TICKET, ID_PLATO) VALUES(?,?)");
		stmt.setInt(1, pedido.getIdTicket());
		stmt.setInt(2, pedido.getIdPlato());
		stmt.executeUpdate();
	}

}
