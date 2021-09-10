package restaurante;

public class Pedidos {

	private int idTicket;
	private int idPlato;

	public Pedidos(int idTicket, int idPlato) {

		this.idTicket = idTicket;
		this.idPlato = idPlato;
	}

	public int getIdTicket() {
		return idTicket;
	}

	public void setIdTicket(int idTicket) {
		this.idTicket = idTicket;
	}

	public int getIdPlato() {
		return idPlato;
	}

	public void setIdPlato(int idPlato) {
		this.idPlato = idPlato;
	}

}
