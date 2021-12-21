package com.solucionesra.springboot.apirest.models.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.solucionesra.springboot.apirest.models.entity.Perfil;

public interface IPerfilDao extends CrudRepository<Perfil, Long> {
	
	@Transactional
	@Modifying
	@Query("update Perfil p set p.vigente=false where p.id=?1")
	public void desactivar(Long id);
	
	@Transactional
	@Modifying
	@Query("update Perfil p set p.vigente=true where p.id=?1")
	public void activar(Long id);	
}
