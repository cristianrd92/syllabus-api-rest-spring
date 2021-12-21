package com.solucionesra.springboot.apirest.models.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.solucionesra.springboot.apirest.models.entity.Sede;

public interface ISedeDao extends CrudRepository<Sede, Long> {
	
	@Transactional
	@Modifying
	@Query("update Sede s set s.vigente=false where s.id=?1")
	public void desactivar(Long id);
	
	@Transactional
	@Modifying
	@Query("update Sede s set s.vigente=true where s.id=?1")
	public void activar(Long id);
	
}
