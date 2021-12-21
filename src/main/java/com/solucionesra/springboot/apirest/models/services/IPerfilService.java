package com.solucionesra.springboot.apirest.models.services;

import java.util.List;

import com.solucionesra.springboot.apirest.models.entity.Perfil;

public interface IPerfilService {

	public List<Perfil> findAll();
	
	public Perfil findById(Long id);
	
	public Perfil save(Perfil perfil);
	
	public void delete(Long id);
	
	public void activar(Long id);
	
	public void desactivar(Long id);
	
}
