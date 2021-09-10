package restaurante;

import java.util.Date;

public class Ticket {

	private int idTicket;
	private Date fecha;
	private int numMesa;

	public Ticket() {

	}

	public Ticket(Date fecha, int numMesa) {
		this.fecha = fecha;
		this.numMesa = numMesa;

	}

	public Ticket(int idTicket, Date fecha, int numMesa) {
		this.idTicket = idTicket;
		this.fecha = fecha;
		this.numMesa = numMesa;

	}

	public int getIdTicket() {
		return idTicket;
	}

	public void setIdTicket(int idTicket) {
		this.idTicket = idTicket;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public int getNumMesa() {
		return numMesa;
	}

	public void setNumMesa(int numMesa) {
		this.numMesa = numMesa;
	}

}
