package com.solucionesra.springboot.apirest.models.services;

import java.util.List;

import com.solucionesra.springboot.apirest.models.entity.DetalleMallaCarrera;
import com.solucionesra.springboot.apirest.models.entity.MallaCurricular;

public interface IMallaCurricularService {

	public List<MallaCurricular> findAll();
	
	public List<MallaCurricular> buscarMallaByCarreraId(Long id);
	
	public List<DetalleMallaCarrera> buscarDetalleMallaByMalla(Long id);
	
	public MallaCurricular findById(Long id);
	
	public List<Object[]> buscarMalla(Long id);
	
	public MallaCurricular save(MallaCurricular cliente);
	
	public void delete(Long id);

	public void activar(Long id);
	
	public void desactivar(Long id);
}
