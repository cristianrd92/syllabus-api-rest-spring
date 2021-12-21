package com.solucionesra.springboot.apirest.models.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.solucionesra.springboot.apirest.models.entity.Facultad;

public interface IFacultadDao extends CrudRepository<Facultad, Long> {
	
	@Transactional
	@Modifying
	@Query("update Facultad f set f.vigente=false where f.id=?1")
	public void desactivar(Long id);
	
	@Transactional
	@Modifying
	@Query("update Facultad f set f.vigente=true where f.id=?1")
	public void activar(Long id);
	
}
