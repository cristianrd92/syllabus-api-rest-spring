package com.solucionesra.springboot.apirest.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.solucionesra.springboot.apirest.models.entity.Sistema;

public interface ISistemaDao extends CrudRepository<Sistema, Long> {
	
	
}
