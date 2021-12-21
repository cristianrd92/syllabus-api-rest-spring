package com.solucionesra.springboot.apirest.models.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.solucionesra.springboot.apirest.models.entity.MallaCurricular;
import com.solucionesra.springboot.apirest.models.entity.DetalleMallaCarrera;


public interface IMallaCurricularDao extends CrudRepository<MallaCurricular, Long> {
	
	@Query("select de from DetalleMallaCarrera de where de.malla_curricular.id=?1")
	public List<DetalleMallaCarrera>buscarDetalleMallaByMalla(Long id);
	
	@Query("select ma from MallaCurricular ma where ma.carrera.id=?1")
	public List<MallaCurricular> buscarMallaByCarreraId(Long id);
	
	@Transactional
	@Modifying
	@Query("update MallaCurricular m set m.vigente=false where m.id=?1")
	public void desactivar(Long id);
	
	@Transactional
	@Modifying
	@Query("update MallaCurricular m set m.vigente=true where m.id=?1")
	public void activar(Long id);
	
	@Query(value = "select * from test(:cod)", nativeQuery = true)
    public List<Object[]> buscarMalla(
            @Param("cod")Integer cod
    );
}
