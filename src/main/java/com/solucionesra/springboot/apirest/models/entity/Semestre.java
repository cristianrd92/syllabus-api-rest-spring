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
@Table(name="semestre")
public class Semestre implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "no puede ser vacio")
	@Size(min=4, max=50, message = "el tamaño debe estar entre 4 y 50")
	@Column(nullable=false, unique=true)
	private String descripcion_semestre;
	
	@Column(unique = true)
	private int posicion;
	
	@Column(nullable=true)
	private boolean vigente=true;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion_semestre() {
		return descripcion_semestre;
	}

	public void setDescripcion_semestre(String descripcion_semestre) {
		this.descripcion_semestre = descripcion_semestre;
	}

	public int getPosicion() {
		return posicion;
	}

	public void setPosicion(int posicion) {
		this.posicion = posicion;
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
