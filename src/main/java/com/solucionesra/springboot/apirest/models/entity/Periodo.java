package com.solucionesra.springboot.apirest.models.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "periodo")
public class Periodo implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String nombre_periodo;
	
	@Column(nullable=true)
	private boolean vigente=true;
	
	@OneToMany(mappedBy="periodo")
	@JsonBackReference
	Set<RamoCarrera> ramoCarrera;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre_periodo() {
		return nombre_periodo;
	}

	public void setNombre_periodo(String nombre_periodo) {
		this.nombre_periodo = nombre_periodo;
	}

	public boolean isVigente() {
		return vigente;
	}

	public void setVigente(boolean vigente) {
		this.vigente = vigente;
	}

	public Set<RamoCarrera> getRamoCarrera() {
		return ramoCarrera;
	}

	public void setRamoCarrera(Set<RamoCarrera> ramoCarrera) {
		this.ramoCarrera = ramoCarrera;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
