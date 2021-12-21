package com.solucionesra.springboot.apirest.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="malla_curricular")
public class MallaCurricular implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "no puede ser vacio")
	@Size(min=4, max=50, message = "el tamaño debe estar entre 4 y 50")
	@Column(nullable=false, unique=true)
	private String descripcion_malla;
	
	@Column(nullable=true)
	private boolean vigente=true;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="carrera_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Carrera carrera;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion_malla() {
		return descripcion_malla;
	}

	public void setDescripcion_malla(String descripcion_malla) {
		this.descripcion_malla = descripcion_malla;
	}

	public boolean isVigente() {
		return vigente;
	}

	public void setVigente(boolean vigente) {
		this.vigente = vigente;
	}

	public Carrera getCarrera() {
		return carrera;
	}

	public void setCarrera(Carrera carrera) {
		this.carrera = carrera;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
}
