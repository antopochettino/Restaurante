package restaurante;

public class Categoria {

	private int id;
	private String nombre;

	public Categoria(String nombreCategoria) {
		this.nombre = nombreCategoria;
	}

	public Categoria(int id, String nombreCategoria) {
		this.id = id;
		this.nombre = nombreCategoria;
	}

	public Categoria() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
