package com.solucionesra.springboot.apirest.models.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.solucionesra.springboot.apirest.models.entity.Ciudad;

public interface ICiudadDao extends CrudRepository<Ciudad, Long> {
	
	@Transactional
	@Modifying
	@Query("update Ciudad c set c.vigente=false where c.id=?1")
	public void desactivar(Long id);
	
	@Transactional
	@Modifying
	@Query("update Ciudad c set c.vigente=true where c.id=?1")
	public void activar(Long id);
	
	@Query(value = "call insertciudad(:name)", nativeQuery = true)
    void saveProcedure(
            @Param("name")String name
    );
}
