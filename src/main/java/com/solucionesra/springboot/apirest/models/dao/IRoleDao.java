package com.solucionesra.springboot.apirest.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.solucionesra.springboot.apirest.models.entity.Permiso;

public interface IRoleDao extends CrudRepository<Permiso, Long> {
	
	
}
