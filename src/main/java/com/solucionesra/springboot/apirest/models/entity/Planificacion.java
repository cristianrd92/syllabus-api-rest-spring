package com.solucionesra.springboot.apirest.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "planificacion")
public class Planificacion implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String ruta;

	@Column(name = "fecha_subida")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha_subida;

	@PrePersist
	public void prePersiste() {
		fecha_subida = new Date();
	}
	
	@PreUpdate
	public void preUpdate() {
		fecha_subida = new Date();
	}


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ramo_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
	private RamoCarrera ramo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
	private Usuario usuario;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public Date getFecha_subida() {
		return fecha_subida;
	}

	public void setFecha_subida(Date fecha_subida) {
		this.fecha_subida = fecha_subida;
	}

	public RamoCarrera getRamo() {
		return ramo;
	}

	public void setRamo(RamoCarrera ramo) {
		this.ramo = ramo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
