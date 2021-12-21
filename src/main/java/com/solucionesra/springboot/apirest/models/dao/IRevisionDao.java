package com.solucionesra.springboot.apirest.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.solucionesra.springboot.apirest.models.entity.Revision;

public interface IRevisionDao extends CrudRepository<Revision, Long> {
	
	
	
}
