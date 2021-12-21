package com.solucionesra.springboot.apirest.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.solucionesra.springboot.apirest.models.entity.Carrera;
import com.solucionesra.springboot.apirest.models.entity.Usuario;
import com.solucionesra.springboot.apirest.models.entity.UsuarioComision;

public interface IUsuarioComisionDao extends CrudRepository<UsuarioComision, Long>{

	@Query("select u from UsuarioComision u where u.carrera.id=?1")
	public List<UsuarioComision> findUsuarioByCarreraId(Long id);
	
	@Query("Select u from Usuario u where u.id \r\n"
			+ "not in \r\n"
			+ "(select u_c.usuario.id \r\n"
			+ " from UsuarioComision u_c where u_c.carrera.id=?1)")
	public List<Usuario> findUsuarioNotInComision(Long id);
	
	@Query("select c from Carrera c where c.id in (select u.carrera.id from UsuarioComision u where u.usuario.id=?1)")
	public List<Carrera> findCarrerasByUsuarioComisionId(Long id_usuario);

	@Query("select p, r from Planificacion p left join Revision r on r.planificacion.id = p.id "
			+ "where p.ramo.id in "
			+ "(select ra.id from RamoCarrera ra where ra.carrera.id = ?1) "
			+ "and r.estado = 'En revisión' "
			+ "or r.estado is null")
	public List<Object[]> obtenerSyllabus(Long id_carrera);
	
}

