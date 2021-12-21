package com.solucionesra.springboot.apirest.models.services;

import java.util.List;

import com.solucionesra.springboot.apirest.models.entity.Ramo;

public interface IRamoService {

	public List<Ramo> findAll();
	
	public Ramo findById(Long id);
	
	public Ramo save(Ramo ramo);
	
	public void delete(Long id);
	
	void insertRamo(Ramo ramo);

	public void activar(Long id);
	
	public void desactivar(Long id);
}
