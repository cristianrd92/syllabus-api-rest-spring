package com.solucionesra.springboot.apirest.models.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.solucionesra.springboot.apirest.models.entity.Periodo;

public interface IPeriodoDao extends CrudRepository<Periodo, Long> {
	
	@Transactional
	@Modifying
	@Query("update Periodo p set p.vigente=false where p.id=?1")
	public void desactivar(Long id);
	
	@Transactional
	@Modifying
	@Query("update Periodo p set p.vigente=true where p.id=?1")
	public void activar(Long id);
	
}
