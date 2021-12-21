package com.solucionesra.springboot.apirest.models.services;

import java.util.List;

import com.solucionesra.springboot.apirest.models.entity.Periodo;

public interface IPeriodoService {

	public List<Periodo> findAll();
	
	public Periodo findById(Long id);
	
	public Periodo save(Periodo periodo);
	
	public void delete(Long id);
	
	public void activar(Long id);
	
	public void desactivar(Long id);
}
