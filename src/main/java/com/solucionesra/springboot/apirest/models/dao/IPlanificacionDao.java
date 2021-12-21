package com.solucionesra.springboot.apirest.models.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.solucionesra.springboot.apirest.models.entity.Planificacion;

public interface IPlanificacionDao extends CrudRepository<Planificacion, Long> {
	
	@Query("select r, re.estado, pla.fecha_subida, pla.ruta, pla.id, re.comentarios "
			+ "from RamoCarrera r\r\n"
			+ "left join Planificacion pla on pla.ramo.id = r.id\r\n"
			+ "left join Revision re on re.planificacion.id = pla.id\r\n"
			+ "where r.usuario.id = ?1")
	public List<Object[]> findEstadoPlanificacionRevision(Long id_usuario);
	
	@Transactional
	@Procedure(procedureName = "getsyllabus")
	String getRutaPlanificaciones(@Param("_id") int id);

}
