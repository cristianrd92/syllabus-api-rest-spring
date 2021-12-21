package com.solucionesra.springboot.apirest.models.services;

import java.util.List;

import com.solucionesra.springboot.apirest.models.entity.Semestre;

public interface ISemestreService {

	public List<Semestre> findAll();
	
	public Semestre findById(Long id);
	
	public Semestre save(Semestre semestre);
	
	public void delete(Long id);
	
	public void activar(Long id);
	
	public void desactivar(Long id);
}
