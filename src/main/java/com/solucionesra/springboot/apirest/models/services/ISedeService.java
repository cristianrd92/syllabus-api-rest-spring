package com.solucionesra.springboot.apirest.models.services;

import java.util.List;

import com.solucionesra.springboot.apirest.models.entity.Sede;

public interface ISedeService {

	public List<Sede> findAll();
	
	public Sede findById(Long id);
	
	public Sede save(Sede sede);
	
	public void delete(Long id);

	public void activar(Long id);
	
	public void desactivar(Long id);
}
