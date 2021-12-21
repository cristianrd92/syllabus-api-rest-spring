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
@Table(name = "ramo")
public class Ramo implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String nombre_ramo;

	@Column(nullable=true)
	private boolean vigente=true;
	
	@Column(nullable=true)
	private String nombre_corto;

	private String codigo_ramo;

	@OneToMany(mappedBy="ramo")
	@JsonBackReference
	Set<RamoCarrera> ramoCarrera;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre_ramo() {
		return nombre_ramo;
	}

	public void setNombre_ramo(String nombre_ramo) {
		this.nombre_ramo = nombre_ramo;
	}

	public boolean isVigente() {
		return vigente;
	}

	public void setVigente(boolean vigente) {
		this.vigente = vigente;
	}

	public String getCodigo_ramo() {
		return codigo_ramo;
	}

	public String getNombre_corto() {
		return nombre_corto;
	}

	public void setNombre_corto(String nombre_corto) {
		this.nombre_corto = nombre_corto;
	}

	public void setCodigo_ramo(String codigo_ramo) {
		this.codigo_ramo = codigo_ramo;
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
