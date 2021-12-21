package com.solucionesra.springboot.apirest.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.solucionesra.springboot.apirest.models.entity.DetalleMallaCarrera;


public interface IDetalleMallaCurricularDao extends CrudRepository<DetalleMallaCarrera, Long> {
	
}
