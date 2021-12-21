package com.solucionesra.springboot.apirest.models.services;

import java.util.List;

import com.solucionesra.springboot.apirest.models.entity.RamoCarrera;

public interface IRamoCarreraService {

	public List<RamoCarrera> findAll();
	
	public List<RamoCarrera> findAllByUsuarioId(Long usuarioId);
	
	public RamoCarrera findById(Long id);
	
	public RamoCarrera save(RamoCarrera ramoCarrera);
	
	public void delete(Long id);
	
	public void activar(Long id);
	
	public void desactivar(Long id);
}
