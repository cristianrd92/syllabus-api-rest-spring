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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Trazabilidad implements Serializable {

	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	Long id;
	
	@Column(name = "fecha")
	@Temporal(TemporalType.DATE)
	private Date fecha;
	
	@Column
	private String accion;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="usuario_id")
	private Usuario usuario;
	
	@PrePersist
	public void prePersiste() {
		fecha = new Date();
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	
}
