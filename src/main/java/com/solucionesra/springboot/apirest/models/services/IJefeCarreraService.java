package com.solucionesra.springboot.apirest.models.services;

import java.util.List;

import com.solucionesra.springboot.apirest.models.entity.Carrera;
import com.solucionesra.springboot.apirest.models.entity.JefeCarrera;
import com.solucionesra.springboot.apirest.models.entity.Usuario;

public interface IJefeCarreraService {

	public JefeCarrera findById(Long id);
	
	public List<Usuario> getDocentesSinJefe();
	
	public List<Carrera> getCarreraSinJefe();
	
	public void delete(Long id);
	
	public List<JefeCarrera> findAll();
	
	public JefeCarrera save(JefeCarrera jefeCarrera);
	
}
