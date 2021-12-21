package com.solucionesra.springboot.apirest.models.services;

import java.util.List;

import com.solucionesra.springboot.apirest.models.entity.Ciudad;

public interface ICiudadService {

	public List<Ciudad> findAll();
	
	public Ciudad findById(Long id);
	
	public Ciudad save(Ciudad ciudad);
	
	void insertCiudad(Ciudad ciudad);
	
	public void delete(Long id);
	
	public void activar(Long id);
	
	public void desactivar(Long id);
	
}
