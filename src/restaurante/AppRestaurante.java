package restaurante;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import DAO.AdminBD;
import DAO.CategoriaDAO;
import DAO.PedidosDAO;
import DAO.PlatoDAO;
import DAO.TicketDAO;

public class AppRestaurante {

	public static void main(String[] args) throws ParseException {
		Connection connection;
		Scanner sc = new Scanner(System.in);
		try {
			connection = AdminBD.obtenerConexion("com.mysql.cj.jdbc.Driver");
			System.out.println("RESTAURANTE");
			System.out.println();
			int opcion = menuOpciones(sc);

			while (opcion != 0) {

				switch (opcion) {
				case 1:
					submenuCategorias(sc, connection);
					break;
				case 2:
					submenuPlatos(sc, connection);
					break;
				case 3:
					submenuTickets(sc, connection);
					break;
				}
				opcion = menuOpciones(sc);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private static void submenuTickets(Scanner sc, Connection connection) throws SQLException, ParseException {
		int opcion = menuTicket(sc, connection);
		while (opcion != 0) {
			switch (opcion) {
			case 1:
				nuevoTicket(sc, connection);
				break;
			case 2:
				anularTicket(sc, connection);
				break;
			case 3:
				verTickets(sc, connection);
				break;
			}
			opcion = menuTicket(sc, connection);
		}
	}

	private static void verTickets(Scanner sc, Connection connection) throws SQLException, ParseException {
		System.out.println("Ingrese primer fecha con formato dd/mm/yyyy: ");
		String f1 = sc.next();
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date fecha1 = df.parse(f1);
		System.out.println("Ingrese segunda fecha con formato dd/mm/yyyy: ");
		String f2 = sc.next();
		Date fecha2 = df.parse(f2);
		List<Ticket> listaTicket = TicketDAO.findBetween(fecha1, fecha2, connection);
		System.out.println();
		for (Ticket c : listaTicket) {
			System.out.println(c.getIdTicket() + " - " + c.getNumMesa() + "-" + c.getFecha());
		}
		System.out.println();
	}

	private static void anularTicket(Scanner sc, Connection connection) throws SQLException {
		Ticket ticket = buscarTicket(sc, connection);
		if (ticket != null) {
			PlatoDAO.borrar(ticket.getIdTicket(), connection);
		} else {
			System.err.println("No se encontró el pedido");
		}

		System.out.println("Se anulo el pedido corrrectamente");
		System.out.println();

	}

	private static Ticket buscarTicket(Scanner sc, Connection connection) throws SQLException {
		System.out.println("Ingrese número de ticket: ");
		int id = sc.nextInt();
		return TicketDAO.findById(id, connection);
	}

	private static void nuevoTicket(Scanner sc, Connection connection) throws SQLException, ParseException {
		System.out.println("Ingrese número de mesa:");
		int numMesa = sc.nextInt();
		System.out.println("Ingrese la fecha con formato dd/mm/yyyy:");
		String fecha = "01/02/1995";
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date dia = df.parse(fecha);
		TicketDAO.insertar(new Ticket(dia, numMesa), connection);
		double total = 0;
		List<Plato> platos = new ArrayList<Plato>();
		System.out.println("1 - Para ingresar el numero del ticket - 0 para finalizar el ticket:");
		int opcion = sc.nextInt();
		System.out.println("Ingrese el numero del ticket:");
		int numTicket = sc.nextInt();
		while (opcion != 0) {
			System.out.println("Ingrese id del plato pedido:");
			int idPlato = sc.nextInt();
			PedidosDAO.insertar(new Pedidos(numTicket, idPlato), connection);
			double precio = PlatoDAO.buscarPrecio(idPlato, connection);
			total += total + precio;
			String nombre = PlatoDAO.buscarNombre(idPlato, connection);
			Plato plato = null;
			plato = new Plato(nombre, precio);
			platos.add(plato);

			System.out.println("1 - Para ingresar el numero del ticket - 0 para finalizar el ticket:");
			opcion = sc.nextInt();
			System.out.println("Ingrese el numero del ticket:");
			numTicket = sc.nextInt();
		}
		imprimirTicket(numTicket, numMesa, dia, total, platos);

	}

	private static void imprimirTicket(int numTicket, int numMesa, Date dia, double total, List<Plato> platos) {
		System.out.println(numTicket + "    " + dia);
		System.out.println(numMesa);
		System.out.println("Detalle:");
		platos.forEach(p -> {
			System.out.println(p.getNombre() + "  " + p.getPrecio());
		});
		System.out.println("Total: " + total);
	}

	private static int menuTicket(Scanner sc, Connection connection) {
		System.out.println("Menu Ticket:");
		System.out.println("1. Nuevo Ticket");
		System.out.println("2. Ver Tickets entre fechas");
		System.out.println("4. Anular pedido");
		System.out.println("0. Salir");
		System.out.println();
		System.out.println("Ingrese opcion: ");
		return sc.nextInt();
	}

	private static void submenuCategorias(Scanner sc, Connection connection) throws SQLException {
		int opcion = menuCategoria(sc, connection);
		while (opcion != 0) {
			switch (opcion) {
			case 1:
				nuevaCategoria(sc, connection);
				break;
			case 2:
				verCategorias(connection);
				break;
			case 3:
				modificarCategorias(sc, connection);
				break;
			case 4:
				eliminarCategoria(sc, connection);
				break;
			}
			opcion = menuCategoria(sc, connection);
		}
	}

	private static int menuCategoria(Scanner sc, Connection connection) {
		System.out.println("Menu Categorias:");
		System.out.println("1. Nueva Categoria");
		System.out.println("2. Ver Categorias");
		System.out.println("3. Modificar categoria");
		System.out.println("4. Eliminar categoria");
		System.out.println("0. Salir");
		System.out.println();
		System.out.println("Ingrese opcion: ");
		return sc.nextInt();
	}

	private static Categoria buscarCategoria(Scanner sc, Connection connection) throws SQLException {
		System.out.println("Ingrese id categoria del plato: ");
		int id = sc.nextInt();
		return CategoriaDAO.findById(id, connection);
	}

	private static void eliminarCategoria(Scanner sc, Connection connection) throws SQLException {
		Categoria categoria = buscarCategoria(sc, connection);
		if (categoria != null) {
			CategoriaDAO.borrar(categoria.getId(), connection);
		} else {
			System.err.println("No se encontró la categoria");
		}

		System.out.println("Se elimino la categoría corrrectamente");
		System.out.println();
	}

	private static void modificarCategorias(Scanner sc, Connection connection) throws SQLException {
		Categoria categoria = buscarCategoria(sc, connection);
		if (categoria == null) {
			System.out.println("Categoria no encontrada");
		} else {
			System.out.println("Ingrese el nuevo nombre de la categoria: ");
			sc.nextLine();
			String categoriaNombre = sc.nextLine();
			categoria.setNombre(categoriaNombre);
			CategoriaDAO.modificar(categoria, connection);

			System.out.println("Modificacion realizada");
			System.out.println();
		}
	}

	private static void verCategorias(Connection connection) throws SQLException {
		System.out.println("Ver categorias:");
		List<Categoria> listaCategoria = CategoriaDAO.findAll(connection);
		System.out.println();
		for (Categoria c : listaCategoria) {
			System.out.println(c.getId() + " - " + c.getNombre());
		}
		System.out.println();
	}

	private static void submenuPlatos(Scanner sc, Connection connection) throws SQLException {
		int opcion = menuPlato(sc, connection);
		while (opcion != 0) {
			switch (opcion) {
			case 1:
				nuevoPlato(sc, connection);
				break;
			case 2:
				eliminarPlato(sc, connection);
				break;
			case 3:
				modificarPlato(sc, connection);
				break;
			case 4:
				verPlatos(sc, connection);
				break;
			case 5:
				verPlatosCategoria(sc, connection);
				break;
			}
			opcion = menuPlato(sc, connection);

		}
	}

	private static void verPlatosCategoria(Scanner sc, Connection connection) throws SQLException {
		System.out.println("Ver platos con sus categorias:");
		PlatoDAO.platoJoinCategoria(connection);
	}

	private static void verPlatos(Scanner sc, Connection connection) throws SQLException {
		System.out.println("Ver platos:");
		List<Plato> listaPlato = PlatoDAO.findAll(connection);
		System.out.println();
		for (Plato plato : listaPlato) {
			System.out.println(plato.getId() + " - " + plato.getNombre() + " - " + "$" + plato.getPrecio() + " - "
					+ plato.getIdCategoria());
		}
		System.out.println();

	}

	private static void modificarPlato(Scanner sc, Connection connection) throws SQLException {
		Plato plato = buscarPlato(sc, connection);
		if (plato == null) {
			System.out.println("Plato no encontrado");
		} else {
			System.out.println("Ingrese la opcion que desea  modificar:");
			System.out.println("1- Nombre");
			System.out.println("2- Precio");
			int opcion = sc.nextInt();
			switch (opcion) {
			case 1:
				System.out.println("Ingrese el nuevo nombre del plato: ");
				sc.nextLine();
				String platoNombre = sc.nextLine();
				plato.setNombre(platoNombre);
				PlatoDAO.modificarNombre(plato, connection);
				break;
			case 2:
				System.out.println("Ingrese el nuevo precio del plato: ");
				sc.nextLine();
				double platoPrecio = sc.nextDouble();
				plato.setPrecio(platoPrecio);
				PlatoDAO.modificarPrecio(plato, connection);
				break;
			}
			System.out.println("Modificacion realizada");
			System.out.println();
		}

	}

	private static int menuPlato(Scanner sc, Connection connection) {
		System.out.println("Menu Platos:");
		System.out.println("1. Nuevo plato");
		System.out.println("2. Eliminar plato");
		System.out.println("3. Modificar plato");
		System.out.println("4. Ver platos");
		System.out.println("0. Salir");
		System.out.println();
		System.out.println("Ingrese opcion: ");
		return sc.nextInt();
	}

	private static void eliminarPlato(Scanner sc, Connection connection) throws SQLException {
		Plato plato = buscarPlato(sc, connection);
		if (plato != null) {
			PlatoDAO.borrar(plato.getId(), connection);
		} else {
			System.err.println("No se encontró el plato");
		}

		System.out.println("Se elimino el plato corrrectamente");
		System.out.println();

	}

	private static Plato buscarPlato(Scanner sc, Connection connection) throws SQLException {
		System.out.println("Ingrese id del plato: ");
		int id = sc.nextInt();
		return PlatoDAO.findById(id, connection);
	}

	private static void nuevoPlato(Scanner sc, Connection connection) throws SQLException {
		System.out.println("Ingrese el nombre del plato: ");
		String nombrePlato = sc.next();
		System.out.println("Ingrese el precio del plato: ");
		double precioPlato = sc.nextInt();
		System.out.println("Ingrese el numero de categoria a la que pertenece el plato: ");
		int platoCategoria = sc.nextInt();
		PlatoDAO.insertar(new Plato(nombrePlato, precioPlato, platoCategoria), connection);
		System.out.println("El plato se añadió correctamente");
		System.out.println();
	}

	private static void nuevaCategoria(Scanner sc, Connection connection) throws SQLException {
		System.out.println("Ingrese el nombre de la categoria: ");
		String nombreCategoria = sc.next();
		CategoriaDAO.insertar(new Categoria(nombreCategoria), connection);
		System.out.println("La categoria se añadió correctamente");
		System.out.println();

	}

	private static int menuOpciones(Scanner sc) {
		System.out.println("Menu:");
		System.out.println("1. Categorias");
		System.out.println("2. Platos");
		System.out.println("3. Tickets");
		System.out.println("0. Salir");
		System.out.println();
		System.out.println("Ingrese opcion: ");
		return sc.nextInt();
	}

}
