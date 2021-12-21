package com.solucionesra.springboot.apirest.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.solucionesra.springboot.apirest.models.entity.Carrera;
import com.solucionesra.springboot.apirest.models.entity.JefeCarrera;
import com.solucionesra.springboot.apirest.models.entity.Usuario;

public interface IJefeCarreraDao extends CrudRepository<JefeCarrera, Long> {
	
	@Query("select u from Usuario u where u.id not in (select j.usuario.id from JefeCarrera j where j.estado=true)"
			+ " and u.vigente=true")
	public List<Usuario> getDocentesSinJefe();
	
	@Query("select c from Carrera c where c.id not in (select j.carrera.id from JefeCarrera j where j.estado=true) and c.vigente=true")
	public List<Carrera> getCarrerasSinJefe();
	
}
