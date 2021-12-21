package com.solucionesra.springboot.apirest.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "parametros")
public class Sistema implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "no puede ser vacio")
	@Size(min = 4, max = 50, message = "el tamaño debe estar entre 4 y 50")
	@Column(nullable = false, unique = true)
	private String host_correo;

	private String nombre_sistema;
	
	private String url_sistema;
	
	private int puerto_correo;

	@NotEmpty(message = "no puede ser vacio")
	@Size(min = 4, max = 100, message = "el tamaño debe estar entre 4 y 50")
	@Column(nullable = false, unique = true)
	private String usuario_correo;

	@NotEmpty(message = "no puede ser vacio")
	@Size(min = 4, max = 300, message = "el tamaño debe estar entre 4 y 50")
	@Column(nullable = false, unique = true)
	private String password_correo;

	@NotEmpty(message = "no puede ser vacio")
	@Size(min = 4, max = 200, message = "el tamaño debe estar entre 4 y 50")
	@Column(nullable = false, unique = true)
	private String url_imagen;

	@NotEmpty(message = "no puede ser vacio")
	@Size(min = 4, max = 500, message = "el tamaño debe estar entre 4 y 50")
	@Column(nullable = false, unique = true)
	private String mensaje_subida_syllabus_docente;

	@NotEmpty(message = "no puede ser vacio")
	@Size(min = 4, max = 500, message = "el tamaño debe estar entre 4 y 50")
	@Column(nullable = false, unique = true)
	private String mensaje_revision_syllabus_comision;

	@NotEmpty(message = "no puede ser vacio")
	@Size(min = 4, max = 500, message = "el tamaño debe estar entre 4 y 50")
	@Column(nullable = false, unique = true)
	private String mensaje_revision_syllabus_docente;

	@NotEmpty(message = "no puede ser vacio")
	@Size(min = 4, max = 500, message = "el tamaño debe estar entre 4 y 50")
	@Column(nullable = false, unique = true)
	private String mensaje_bienvenida_sistema;

	@NotEmpty(message = "no puede ser vacio")
	@Size(min = 4, max = 500, message = "el tamaño debe estar entre 4 y 50")
	@Column(nullable = false, unique = true)
	private String mensaje_reinicio_clave;

	@NotEmpty(message = "no puede ser vacio")
	@Size(min = 4, max = 500, message = "el tamaño debe estar entre 4 y 50")
	@Column(nullable = false, unique = true)
	private String mensaje_recuperacion_clave;

	@Column(name = "fecha_tope_syllabus")
	@Temporal(TemporalType.DATE)
	private Date fecha_tope_syllabus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHost_correo() {
		return host_correo;
	}

	public void setHost_correo(String host_correo) {
		this.host_correo = host_correo;
	}

	public int getPuerto_correo() {
		return puerto_correo;
	}

	public void setPuerto_correo(int puerto_correo) {
		this.puerto_correo = puerto_correo;
	}

	public String getUsuario_correo() {
		return usuario_correo;
	}

	public void setUsuario_correo(String usuario_correo) {
		this.usuario_correo = usuario_correo;
	}

	public String getPassword_correo() {
		return password_correo;
	}

	public void setPassword_correo(String password_correo) {
		this.password_correo = password_correo;
	}

	public String getUrl_imagen() {
		return url_imagen;
	}

	public void setUrl_imagen(String url_imagen) {
		this.url_imagen = url_imagen;
	}

	public String getMensaje_subida_syllabus_docente() {
		return mensaje_subida_syllabus_docente;
	}

	public void setMensaje_subida_syllabus_docente(String mensaje_subida_syllabus_docente) {
		this.mensaje_subida_syllabus_docente = mensaje_subida_syllabus_docente;
	}

	public String getMensaje_revision_syllabus_comision() {
		return mensaje_revision_syllabus_comision;
	}

	public void setMensaje_revision_syllabus_comision(String mensaje_revision_syllabus_comision) {
		this.mensaje_revision_syllabus_comision = mensaje_revision_syllabus_comision;
	}

	public String getMensaje_revision_syllabus_docente() {
		return mensaje_revision_syllabus_docente;
	}

	public void setMensaje_revision_syllabus_docente(String mensaje_revision_syllabus_docente) {
		this.mensaje_revision_syllabus_docente = mensaje_revision_syllabus_docente;
	}

	public String getMensaje_bienvenida_sistema() {
		return mensaje_bienvenida_sistema;
	}

	public void setMensaje_bienvenida_sistema(String mensaje_bienvenida_sistema) {
		this.mensaje_bienvenida_sistema = mensaje_bienvenida_sistema;
	}

	public String getMensaje_reinicio_clave() {
		return mensaje_reinicio_clave;
	}

	public void setMensaje_reinicio_clave(String mensaje_reinicio_clave) {
		this.mensaje_reinicio_clave = mensaje_reinicio_clave;
	}

	public String getMensaje_recuperacion_clave() {
		return mensaje_recuperacion_clave;
	}

	public void setMensaje_recuperacion_clave(String mensaje_recuperacion_clave) {
		this.mensaje_recuperacion_clave = mensaje_recuperacion_clave;
	}

	public Date getFecha() {
		return fecha_tope_syllabus;
	}

	public void setFecha(Date fecha) {
		this.fecha_tope_syllabus = fecha;
	}

	public String getNombre_sistema() {
		return nombre_sistema;
	}

	public void setNombre_sistema(String nombre_sistema) {
		this.nombre_sistema = nombre_sistema;
	}

	public String getUrl_sistema() {
		return url_sistema;
	}

	public void setUrl_sistema(String url_sistema) {
		this.url_sistema = url_sistema;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
