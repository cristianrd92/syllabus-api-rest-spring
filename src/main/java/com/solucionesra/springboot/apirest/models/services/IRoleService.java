package com.solucionesra.springboot.apirest.models.services;

import java.util.List;

import com.solucionesra.springboot.apirest.models.entity.Permiso;

public interface IRoleService {

	public List<Permiso> findAll();
	
	public Permiso findById(Long id);
	
	public Permiso save(Permiso perfil);
	
	public void delete(Long id);
	
}
