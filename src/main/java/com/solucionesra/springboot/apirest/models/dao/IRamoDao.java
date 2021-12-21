package com.solucionesra.springboot.apirest.models.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.solucionesra.springboot.apirest.models.entity.Ramo;

public interface IRamoDao extends CrudRepository<Ramo, Long> {
	
	@Query(value = "call insertRamo(:cod, :name, :short)", nativeQuery = true)
    void saveProcedure(
            @Param("cod")String cod,
            @Param("name")String name,
            @Param("short")String corto
    );
	
	@Transactional
	@Modifying
	@Query("update Ramo r set r.vigente=false where r.id=?1")
	public void desactivar(Long id);
	
	@Transactional
	@Modifying
	@Query("update Ramo r set r.vigente=true where r.id=?1")
	public void activar(Long id);
	
}
