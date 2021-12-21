package com.solucionesra.springboot.apirest.models.services;

import java.util.List;

import com.solucionesra.springboot.apirest.models.entity.Facultad;

public interface IFacultadService {

	public List<Facultad> findAll();
	
	public Facultad findById(Long id);
	
	public Facultad save(Facultad facultad);
	
	public void delete(Long id);
	
	public void activar(Long id);
	
	public void desactivar(Long id);
}
