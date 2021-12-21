package com.solucionesra.springboot.apirest.models.services;

import java.util.List;

import com.solucionesra.springboot.apirest.models.entity.DetalleMallaCarrera;

public interface IDetalleMallaCurricularService {

	public List<DetalleMallaCarrera> findAll();
	
	public DetalleMallaCarrera findById(Long id);
	
	public DetalleMallaCarrera save(DetalleMallaCarrera cliente);
	
	public void delete(Long id);
	
}
