package com.solucionesra.springboot.apirest.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.solucionesra.springboot.apirest.models.entity.Permiso;

public interface IPermisoDao extends CrudRepository<Permiso, Long> {
	
	@Query("select p from Permiso p ORDER BY p.id asc")
	public List<Permiso> findAllord();
	
}
