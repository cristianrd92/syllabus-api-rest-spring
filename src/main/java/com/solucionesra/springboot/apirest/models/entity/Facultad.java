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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="facultad")
public class Facultad implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(nullable=false)
	private String nombre_facultad;
	
	@Column(nullable=true)
	private boolean vigente=true;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sede_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Sede sede;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre_facultad;
	}
	public void setNombre(String nombre_facultad) {
		this.nombre_facultad = nombre_facultad;
	}
	
	public String getNombre_facultad() {
		return nombre_facultad;
	}
	public void setNombre_facultad(String nombre_facultad) {
		this.nombre_facultad = nombre_facultad;
	}
	public boolean isVigente() {
		return vigente;
	}
	public void setVigente(boolean vigente) {
		this.vigente = vigente;
	}
	public Sede getSede() {
		return sede;
	}
	public void setSede(Sede sede) {
		this.sede = sede;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
}
