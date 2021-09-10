package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import restaurante.Ticket;

public class TicketDAO {

	public static void insertar(Ticket ticket, Connection connection) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("INSERT INTO TICKET (FECHA, NUMERO_MESA) VALUES(?,?)");
		stmt.setDate(1, (Date) ticket.getFecha());
		stmt.setDouble(2, ticket.getNumMesa());
		stmt.executeUpdate();
	}

	public static void borrar(int id, Connection connection) throws SQLException {
		PreparedStatement prepStmt = connection.prepareStatement("DELETE FROM TICKET WHERE ID = ?");
		prepStmt.setInt(1, id);
		prepStmt.executeUpdate();
	}

	public static Ticket findById(int id, Connection connection) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM TICKET WHERE ID = ?");
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();
		Ticket ticket = null;
		if (rs.next()) {
			ticket = new Ticket(rs.getInt("idTiket"), rs.getDate("fecha"), rs.getInt("numero_mesa"));
		}
		return ticket;
	}

	public static List<Ticket> findBetween(java.util.Date fecha1, java.util.Date fecha2, Connection connection)
			throws SQLException {
		List<Ticket> listaTicket = new ArrayList<Ticket>();
		PreparedStatement stmt = connection
				.prepareStatement("SELECT FECHA FROM TICKET WHERE FECHA BETWEEN %?% AND %?%");
		ResultSet rs = stmt.executeQuery();
		Ticket ticket = null;
		while (rs.next()) {
			ticket = new Ticket(rs.getInt("idTicket"), rs.getDate("fecha"), rs.getInt("numero_mesa"));
			listaTicket.add(ticket);
		}
		return listaTicket;
	}

}
