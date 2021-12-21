package com.solucionesra.springboot.apirest.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name="ciudad")
public class Ciudad implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "no puede ser vacio")
	@Size(min=4, max=50, message = "el tamaño debe estar entre 4 y 50")
	@Column(nullable=false, unique=true)
	private String nombre_ciudad;
	
	@Column(nullable=true)
	private boolean vigente=true;
	/**
	@Column(name="create_at")
	@Temporal(TemporalType.DATE)
	private Date createdAt;
	@PrePersist
	public void prePersiste() {
		createdAt = new Date();
	}
	 */
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre_ciudad() {
		return nombre_ciudad;
	}

	public void setNombre_ciudad(String nombre_ciudad) {
		this.nombre_ciudad = nombre_ciudad;
	}

	public boolean isVigente() {
		return vigente;
	}

	public void setVigente(boolean vigente) {
		this.vigente = vigente;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
}
