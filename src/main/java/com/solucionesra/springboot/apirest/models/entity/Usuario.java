package com.solucionesra.springboot.apirest.models.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "no puede ser vacio")
	@Size(min = 4, max = 50, message = "el tamaño debe estar entre 4 y 50")
	@Column(nullable = false)
	private String nombres;

	@NotEmpty(message = "no puede ser vacio")
	@Size(min = 4, max = 50, message = "el tamaño debe estar entre 4 y 50")
	@Column(nullable = false)
	private String apellidos;
	
	@Column(nullable= true)
	private String nombre_corto;

	@Column(nullable = true)
	private boolean first=true;
	
	@Column(unique = true, length = 50)
	private String rut_usuario;

	@Column(unique = true, length = 20)
	private String username;

	@Column(length = 60)
	@JsonIgnore
	private String password;

	@Column(length = 100, unique=true)
	@NotEmpty(message = "no puede ser vacio")
	@Email(message = "no tiene un formato correcto")
	private String email;
	
	private Boolean vigente = true;

	
	@OneToMany(mappedBy="usuario")
	@JsonBackReference
	Set<RamoCarrera> ramoCarrera;
	
	@Transient
	private Carrera carrera;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinTable(name = "usuario_perfil",  joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "perfil_id"), uniqueConstraints = {
			@UniqueConstraint(columnNames = { "usuario_id", "perfil_id" }) })
	private List<Perfil> perfiles;

	public List<Perfil> getPerfiles() {
		return perfiles;
	}

	public void setPerfiles(List<Perfil> perfiles) {
		this.perfiles = perfiles;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getRut_usuario() {
		return rut_usuario;
	}

	public void setRut_usuario(String rut_usuario) {
		this.rut_usuario = rut_usuario;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getVigente() {
		return vigente;
	}

	public void setVigente(Boolean vigente) {
		this.vigente = vigente;
	}

	public Set<RamoCarrera> getRamoCarrera() {
		return ramoCarrera;
	}

	public void setRamoCarrera(Set<RamoCarrera> ramoCarrera) {
		this.ramoCarrera = ramoCarrera;
	}

	public boolean isFirst() {
		return first;
	}

	public void setFirst(boolean first) {
		this.first = first;
	}

	public Carrera getCarrera() {
		return carrera;
	}

	public void setCarrera(Carrera carrera) {
		this.carrera = carrera;
	}

	public String getNombre_corto() {
		return nombre_corto;
	}

	public void setNombre_corto(String nombre_corto) {
		this.nombre_corto = nombre_corto;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
