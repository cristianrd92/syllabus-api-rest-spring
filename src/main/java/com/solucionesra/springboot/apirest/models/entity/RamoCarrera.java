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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class RamoCarrera implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Column
	private int creditos;
	
	@Column(nullable = true)
	private int anio;
	
	@Column(nullable=true)
	private boolean vigente=true;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="carrera_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Carrera carrera;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ramo_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Ramo ramo;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="periodo_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Periodo periodo;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="usuario_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Usuario usuario;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public int getCreditos() {
		return creditos;
	}

	public void setCreditos(int creditos) {
		this.creditos = creditos;
	}

	public Carrera getCarrera() {
		return carrera;
	}

	public void setCarrera(Carrera carrera) {
		this.carrera = carrera;
	}

	public Ramo getRamo() {
		return ramo;
	}

	public void setRamo(Ramo ramo) {
		this.ramo = ramo;
	}

	public Periodo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	public boolean isVigente() {
		return vigente;
	}

	public void setVigente(boolean vigente) {
		this.vigente = vigente;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	

}
