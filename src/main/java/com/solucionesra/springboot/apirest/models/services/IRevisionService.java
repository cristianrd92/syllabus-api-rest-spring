package com.solucionesra.springboot.apirest.models.services;

import java.util.List;

import com.solucionesra.springboot.apirest.models.entity.Revision;

public interface IRevisionService {

	public List<Revision> findAll();
	
	public Revision findById(Long id);
	
	public Revision save(Revision revision);
	
	public void delete(Long id);
	
}
