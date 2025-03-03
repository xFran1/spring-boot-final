package net.javaguides.spingannotations;

public class UsuarioDTO {
    private Integer id;
    private String nombre;

    // Constructor
    public UsuarioDTO(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // Getters
    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

	@Override
	public String toString() {
		return "UsuarioDTO [id=" + id + ", nombre=" + nombre + "]";
	}
    
}