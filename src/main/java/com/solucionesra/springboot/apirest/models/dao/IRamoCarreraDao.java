package com.solucionesra.springboot.apirest.models.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.solucionesra.springboot.apirest.models.entity.RamoCarrera;

public interface IRamoCarreraDao extends CrudRepository<RamoCarrera, Long> {

	List<RamoCarrera> findAllByUsuarioId(Long usuarioId);

	@Transactional
	@Modifying
	@Query("update RamoCarrera rc set rc.vigente=false where rc.id=?1")
	public void desactivar(Long id);
	
	@Transactional
	@Modifying
	@Query("update RamoCarrera rc set rc.vigente=true where rc.id=?1")
	public void activar(Long id);
	
}
