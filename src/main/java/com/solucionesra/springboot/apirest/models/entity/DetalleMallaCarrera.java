package com.solucionesra.springboot.apirest.models.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class DetalleMallaCarrera {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="malla_curricular_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private MallaCurricular malla_curricular;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ramo_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Ramo ramo;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="semestre_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Semestre semestre;
	
	private int posicion;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MallaCurricular getMalla_curricular() {
		return malla_curricular;
	}

	public void setMalla_curricular(MallaCurricular malla_curricular) {
		this.malla_curricular = malla_curricular;
	}

	public Ramo getRamo() {
		return ramo;
	}

	public void setRamo(Ramo ramo) {
		this.ramo = ramo;
	}

	public Semestre getSemestre() {
		return semestre;
	}

	public void setSemestre(Semestre semestre) {
		this.semestre = semestre;
	}

	public int getPosicion() {
		return posicion;
	}

	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}

}
