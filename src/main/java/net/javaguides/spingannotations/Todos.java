package net.javaguides.spingannotations;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "todos")
public class Todos {
	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id")
	    private Long id;

	    @Column(name = "fecha_creacion", columnDefinition = "TIMESTAMP(0)")	    
	    private LocalDateTime fechaCreacion;

	    @Column(name = "asunto")
	    private String asunto;

	    @Column(name = "mensaje")
	    private String mensaje;

	  
	    @Column(name = "completado", nullable = false)
	    private Boolean completado;

	    @Column(name = "id_cliente")
	    private Integer idCliente;

    public Todos() {
    }

    public Todos(String asunto, String mensaje) {
        this.fechaCreacion = LocalDateTime.now();
        this.asunto = asunto;
        this.mensaje = mensaje;
        this.completado = false;
    }

    public Long getId() {
        return id;
    }

    
    
  

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    
    
    public void setId(Long id) {
		this.id = id;
	}

	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}


	public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

  
    public Boolean getCompletado() {
		return completado;
	}

	public void setCompletado(Boolean completado) {
		this.completado = completado;
	}

}
