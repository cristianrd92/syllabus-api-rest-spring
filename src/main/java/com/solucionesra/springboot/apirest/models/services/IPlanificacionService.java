package com.solucionesra.springboot.apirest.models.services;

import java.util.List;

import com.solucionesra.springboot.apirest.models.entity.Planificacion;

public interface IPlanificacionService {

	public List<Planificacion> findAll();
	
	public Planificacion findById(Long id);
	
	public Planificacion save(Planificacion planificacion);
	
	public void delete(Long id);

	List<Object[]> findEstadoPlanificacionRevision(Long usuario_id);
	
	String getRutaPlanificaciones(Long id);
}
