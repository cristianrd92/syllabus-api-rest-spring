package com.solucionesra.springboot.apirest.models.services;

import java.util.List;

import com.solucionesra.springboot.apirest.models.entity.Sistema;

public interface ISistemaService {

	public List<Sistema> findAll();
	
	public Sistema findById(Long id);
	
	public Sistema save(Sistema sistema);
	
	public void delete(Long id);
	
}
